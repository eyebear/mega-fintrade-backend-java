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

import com.ao.portfolio.entity.BacktestResult;
import com.ao.portfolio.entity.ImportRejection;
import com.ao.portfolio.repository.BacktestResultRepository;
import com.ao.portfolio.repository.ImportRejectionRepository;

@SpringBootTest
class BacktestResultCsvImportServiceTest {

    @Autowired
    private BacktestResultCsvImportService backtestResultCsvImportService;

    @Autowired
    private BacktestResultRepository backtestResultRepository;

    @Autowired
    private ImportRejectionRepository importRejectionRepository;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        importRejectionRepository.deleteAll();
        backtestResultRepository.deleteAll();
    }

    @Test
    void shouldImportValidBacktestResultCsvFile() throws Exception {
        Path csvFile = tempDir.resolve("backtest_results.csv");

        Files.write(
                csvFile,
                List.of(
                        "date,portfolio_return,benchmark_return,turnover,portfolio_wealth,benchmark_wealth",
                        "2026-01-02,0.0100,0.0080,0.1200,101000.00,100800.00",
                        "2026-01-03,-0.0050,-0.0040,0.1000,100495.00,100396.80"
                )
        );

        int importedRows = backtestResultCsvImportService.importFile(csvFile);

        List<BacktestResult> results = backtestResultRepository.findAll();

        assertThat(importedRows).isEqualTo(2);
        assertThat(results).hasSize(2);
        assertThat(importRejectionRepository.findAll()).isEmpty();
    }

    @Test
    void shouldRejectBacktestResultCsvWithInvalidDate() throws Exception {
        Path csvFile = tempDir.resolve("backtest_results.csv");

        Files.write(
                csvFile,
                List.of(
                        "date,portfolio_return,benchmark_return,turnover,portfolio_wealth,benchmark_wealth",
                        "BAD_DATE,0.0100,0.0080,0.1200,101000.00,100800.00"
                )
        );

        assertThatThrownBy(() -> backtestResultCsvImportService.importFile(csvFile))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid date field");

        List<ImportRejection> rejections = importRejectionRepository.findAll();

        assertThat(rejections).hasSize(1);
        assertThat(rejections.get(0).getFileName()).isEqualTo("backtest_results.csv");
        assertThat(rejections.get(0).getLineNumber()).isEqualTo(2);
        assertThat(rejections.get(0).getReason()).contains("Invalid date field");
    }

    @Test
    void shouldRejectBacktestResultCsvWithInvalidDecimal() throws Exception {
        Path csvFile = tempDir.resolve("backtest_results.csv");

        Files.write(
                csvFile,
                List.of(
                        "date,portfolio_return,benchmark_return,turnover,portfolio_wealth,benchmark_wealth",
                        "2026-01-02,NOT_A_NUMBER,0.0080,0.1200,101000.00,100800.00"
                )
        );

        assertThatThrownBy(() -> backtestResultCsvImportService.importFile(csvFile))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid decimal field");

        List<ImportRejection> rejections = importRejectionRepository.findAll();

        assertThat(rejections).hasSize(1);
        assertThat(rejections.get(0).getFileName()).isEqualTo("backtest_results.csv");
        assertThat(rejections.get(0).getLineNumber()).isEqualTo(2);
        assertThat(rejections.get(0).getReason()).contains("Invalid decimal field");
    }
}