package com.ao.portfolio.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ao.portfolio.entity.ImportRejection;
import com.ao.portfolio.entity.RiskMetric;
import com.ao.portfolio.repository.ImportRejectionRepository;
import com.ao.portfolio.repository.RiskMetricRepository;

@SpringBootTest
class RiskMetricCsvImportServiceTest {

    @Autowired
    private RiskMetricCsvImportService riskMetricCsvImportService;

    @Autowired
    private RiskMetricRepository riskMetricRepository;

    @Autowired
    private ImportRejectionRepository importRejectionRepository;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        importRejectionRepository.deleteAll();
        riskMetricRepository.deleteAll();
    }

    @Test
    void shouldImportValidRiskMetricCsvFile() throws Exception {
        Path csvFile = tempDir.resolve("risk_metrics.csv");

        Files.write(
                csvFile,
                List.of(
                        "scope,symbol,metric,value",
                        "symbol,AAPL,volatility,0.0335",
                        "portfolio,ALL,cagr,0.8500"
                )
        );

        int importedRows = riskMetricCsvImportService.importFile(csvFile);

        List<RiskMetric> riskMetrics = riskMetricRepository.findAll();

        assertThat(importedRows).isEqualTo(2);
        assertThat(riskMetrics).hasSize(2);

        assertThat(riskMetrics)
                .extracting(RiskMetric::getSymbol)
                .contains("AAPL", "ALL");

        assertThat(importRejectionRepository.findAll()).isEmpty();
    }

    @Test
    void shouldRejectRiskMetricCsvWithInvalidColumnCount() throws Exception {
        Path csvFile = tempDir.resolve("risk_metrics.csv");

        Files.write(
                csvFile,
                List.of(
                        "scope,symbol,metric,value",
                        "BROKEN,ROW"
                )
        );

        assertThatThrownBy(() -> riskMetricCsvImportService.importFile(csvFile))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid column count");

        List<ImportRejection> rejections = importRejectionRepository.findAll();

        assertThat(rejections).hasSize(1);
        assertThat(rejections.get(0).getFileName()).isEqualTo("risk_metrics.csv");
        assertThat(rejections.get(0).getLineNumber()).isEqualTo(2);
        assertThat(rejections.get(0).getRawRecord()).isEqualTo("BROKEN,ROW");
        assertThat(rejections.get(0).getReason()).contains("Invalid column count");
    }

    @Test
    void shouldRejectRiskMetricCsvWithInvalidHeader() throws Exception {
        Path csvFile = tempDir.resolve("risk_metrics.csv");

        Files.write(
                csvFile,
                List.of(
                        "bad,header",
                        "symbol,AAPL,volatility,0.0335"
                )
        );

        assertThatThrownBy(() -> riskMetricCsvImportService.importFile(csvFile))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid CSV header");

        List<ImportRejection> rejections = importRejectionRepository.findAll();

        assertThat(rejections).hasSize(1);
        assertThat(rejections.get(0).getFileName()).isEqualTo("risk_metrics.csv");
        assertThat(rejections.get(0).getLineNumber()).isEqualTo(1);
        assertThat(rejections.get(0).getReason()).contains("Invalid CSV header");
    }
}