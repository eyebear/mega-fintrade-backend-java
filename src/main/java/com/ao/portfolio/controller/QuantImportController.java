package com.ao.portfolio.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ao.portfolio.service.BacktestResultCsvImportService;
import com.ao.portfolio.service.PortfolioEquityCsvImportService;
import com.ao.portfolio.service.QuantCsvImportOrchestrationService;
import com.ao.portfolio.service.RiskMetricCsvImportService;
import com.ao.portfolio.service.StrategySignalCsvImportService;

@RestController
@RequestMapping("/api/import")
public class QuantImportController {

    private final RiskMetricCsvImportService riskMetricCsvImportService;
    private final BacktestResultCsvImportService backtestResultCsvImportService;
    private final StrategySignalCsvImportService strategySignalCsvImportService;
    private final PortfolioEquityCsvImportService portfolioEquityCsvImportService;
    private final QuantCsvImportOrchestrationService quantCsvImportOrchestrationService;

    public QuantImportController(
            RiskMetricCsvImportService riskMetricCsvImportService,
            BacktestResultCsvImportService backtestResultCsvImportService,
            StrategySignalCsvImportService strategySignalCsvImportService,
            PortfolioEquityCsvImportService portfolioEquityCsvImportService,
            QuantCsvImportOrchestrationService quantCsvImportOrchestrationService
    ) {
        this.riskMetricCsvImportService = riskMetricCsvImportService;
        this.backtestResultCsvImportService = backtestResultCsvImportService;
        this.strategySignalCsvImportService = strategySignalCsvImportService;
        this.portfolioEquityCsvImportService = portfolioEquityCsvImportService;
        this.quantCsvImportOrchestrationService = quantCsvImportOrchestrationService;
    }
}