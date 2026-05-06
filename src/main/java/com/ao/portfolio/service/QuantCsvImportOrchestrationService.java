package com.ao.portfolio.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ao.portfolio.dto.QuantCsvImportSummary;

@Service
public class QuantCsvImportOrchestrationService {

    private final RiskMetricCsvImportService riskMetricCsvImportService;
    private final BacktestResultCsvImportService backtestResultCsvImportService;
    private final StrategySignalCsvImportService strategySignalCsvImportService;
    private final PortfolioEquityCsvImportService portfolioEquityCsvImportService;

    public QuantCsvImportOrchestrationService(
            RiskMetricCsvImportService riskMetricCsvImportService,
            BacktestResultCsvImportService backtestResultCsvImportService,
            StrategySignalCsvImportService strategySignalCsvImportService,
            PortfolioEquityCsvImportService portfolioEquityCsvImportService
    ) {
        this.riskMetricCsvImportService = riskMetricCsvImportService;
        this.backtestResultCsvImportService = backtestResultCsvImportService;
        this.strategySignalCsvImportService = strategySignalCsvImportService;
        this.portfolioEquityCsvImportService = portfolioEquityCsvImportService;
    }

    @Transactional
    public QuantCsvImportSummary importAllDefaultFiles() {
        int riskMetricRowsImported = riskMetricCsvImportService.importDefaultFile();
        int backtestResultRowsImported = backtestResultCsvImportService.importDefaultFile();
        int strategySignalRowsImported = strategySignalCsvImportService.importDefaultFile();
        int portfolioEquityRowsImported = portfolioEquityCsvImportService.importDefaultFile();

        return new QuantCsvImportSummary(
                riskMetricRowsImported,
                backtestResultRowsImported,
                strategySignalRowsImported,
                portfolioEquityRowsImported
        );
    }
}