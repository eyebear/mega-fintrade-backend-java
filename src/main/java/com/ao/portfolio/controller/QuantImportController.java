package com.ao.portfolio.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ao.portfolio.dto.QuantCsvImportSummary;
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

    @PostMapping("/risk-metrics")
    public Map<String, Object> importRiskMetrics() {
        int rowsImported = riskMetricCsvImportService.importDefaultFile();

        return Map.of(
                "file", "risk_metrics.csv",
                "rowsImported", rowsImported,
                "message", "Risk metrics imported successfully"
        );
    }

    @PostMapping("/backtest-results")
    public Map<String, Object> importBacktestResults() {
        int rowsImported = backtestResultCsvImportService.importDefaultFile();

        return Map.of(
                "file", "backtest_results.csv",
                "rowsImported", rowsImported,
                "message", "Backtest results imported successfully"
        );
    }

    @PostMapping("/strategy-signals")
    public Map<String, Object> importStrategySignals() {
        int rowsImported = strategySignalCsvImportService.importDefaultFile();

        return Map.of(
                "file", "strategy_signals.csv",
                "rowsImported", rowsImported,
                "message", "Strategy signals imported successfully"
        );
    }

    @PostMapping("/portfolio-equity")
    public Map<String, Object> importPortfolioEquity() {
        int rowsImported = portfolioEquityCsvImportService.importDefaultFile();

        return Map.of(
                "file", "portfolio_equity_curve.csv",
                "rowsImported", rowsImported,
                "message", "Portfolio equity curve imported successfully"
        );
    }

    @PostMapping("/all")
    public QuantCsvImportSummary importAllQuantCsvFiles() {
        return quantCsvImportOrchestrationService.importAllDefaultFiles();
    }
}