package com.ao.portfolio.dto;

public class QuantCsvImportSummary {

    private int riskMetricRowsImported;
    private int backtestResultRowsImported;
    private int strategySignalRowsImported;
    private int portfolioEquityRowsImported;
    private int totalRowsImported;

    public QuantCsvImportSummary() {
    }

    public QuantCsvImportSummary(
            int riskMetricRowsImported,
            int backtestResultRowsImported,
            int strategySignalRowsImported,
            int portfolioEquityRowsImported
    ) {
        this.riskMetricRowsImported = riskMetricRowsImported;
        this.backtestResultRowsImported = backtestResultRowsImported;
        this.strategySignalRowsImported = strategySignalRowsImported;
        this.portfolioEquityRowsImported = portfolioEquityRowsImported;
        this.totalRowsImported = riskMetricRowsImported
                + backtestResultRowsImported
                + strategySignalRowsImported
                + portfolioEquityRowsImported;
    }

    public int getRiskMetricRowsImported() {
        return riskMetricRowsImported;
    }

    public void setRiskMetricRowsImported(int riskMetricRowsImported) {
        this.riskMetricRowsImported = riskMetricRowsImported;
        recalculateTotalRowsImported();
    }

    public int getBacktestResultRowsImported() {
        return backtestResultRowsImported;
    }

    public void setBacktestResultRowsImported(int backtestResultRowsImported) {
        this.backtestResultRowsImported = backtestResultRowsImported;
        recalculateTotalRowsImported();
    }

    public int getStrategySignalRowsImported() {
        return strategySignalRowsImported;
    }

    public void setStrategySignalRowsImported(int strategySignalRowsImported) {
        this.strategySignalRowsImported = strategySignalRowsImported;
        recalculateTotalRowsImported();
    }

    public int getPortfolioEquityRowsImported() {
        return portfolioEquityRowsImported;
    }

    public void setPortfolioEquityRowsImported(int portfolioEquityRowsImported) {
        this.portfolioEquityRowsImported = portfolioEquityRowsImported;
        recalculateTotalRowsImported();
    }

    public int getTotalRowsImported() {
        return totalRowsImported;
    }

    public void setTotalRowsImported(int totalRowsImported) {
        this.totalRowsImported = totalRowsImported;
    }

    private void recalculateTotalRowsImported() {
        this.totalRowsImported = riskMetricRowsImported
                + backtestResultRowsImported
                + strategySignalRowsImported
                + portfolioEquityRowsImported;
    }
}