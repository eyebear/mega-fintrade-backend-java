package com.ao.portfolio.batch;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ao.portfolio.dto.BatchRunSummary;
import com.ao.portfolio.repository.BacktestResultRepository;
import com.ao.portfolio.repository.ImportAuditRepository;
import com.ao.portfolio.repository.ImportRejectionRepository;
import com.ao.portfolio.repository.PortfolioEquityPointRepository;
import com.ao.portfolio.repository.RiskMetricRepository;
import com.ao.portfolio.repository.StrategySignalRepository;
import com.ao.portfolio.service.QuantBatchRunService;

@SpringBootTest
class QuantBatchRunServiceTest {

    @Autowired
    private QuantBatchRunService quantBatchRunService;

    @Autowired
    private RiskMetricRepository riskMetricRepository;

    @Autowired
    private BacktestResultRepository backtestResultRepository;

    @Autowired
    private StrategySignalRepository strategySignalRepository;

    @Autowired
    private PortfolioEquityPointRepository portfolioEquityPointRepository;

    @Autowired
    private ImportAuditRepository importAuditRepository;

    @Autowired
    private ImportRejectionRepository importRejectionRepository;

    @BeforeEach
    void setUp() throws Exception {
        importAuditRepository.deleteAll();
        importRejectionRepository.deleteAll();

        strategySignalRepository.deleteAll();
        portfolioEquityPointRepository.deleteAll();
        backtestResultRepository.deleteAll();
        riskMetricRepository.deleteAll();

        copySampleCsvFilesToDataInput();
    }

    @Test
    void shouldRunAllQuantImportBatchJobsAndReturnSummary() {
        BatchRunSummary summary = quantBatchRunService.runAllQuantImportJobs();

        assertThat(summary).isNotNull();
        assertThat(summary.getStatus()).isNotBlank();
        assertThat(summary.getStartedAt()).isNotNull();
        assertThat(summary.getFinishedAt()).isNotNull();

        assertThat(summary.getRiskMetricJobStatus()).isNotBlank();
        assertThat(summary.getBacktestResultJobStatus()).isNotBlank();
        assertThat(summary.getStrategySignalJobStatus()).isNotBlank();
        assertThat(summary.getPortfolioEquityJobStatus()).isNotBlank();
    }

    private void copySampleCsvFilesToDataInput() throws Exception {
        Path dataInputDir = Path.of("data", "input");
        Files.createDirectories(dataInputDir);

        copyClasspathResourceToFile(
                "sample-csv/risk_metrics.csv",
                dataInputDir.resolve("risk_metrics.csv")
        );

        copyClasspathResourceToFile(
                "sample-csv/backtest_results.csv",
                dataInputDir.resolve("backtest_results.csv")
        );

        copyClasspathResourceToFile(
                "sample-csv/strategy_signals.csv",
                dataInputDir.resolve("strategy_signals.csv")
        );

        copyClasspathResourceToFile(
                "sample-csv/portfolio_equity_curve.csv",
                dataInputDir.resolve("portfolio_equity_curve.csv")
        );
    }

    private void copyClasspathResourceToFile(String resourcePath, Path targetPath) throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Test resource not found: " + resourcePath);
            }

            Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}