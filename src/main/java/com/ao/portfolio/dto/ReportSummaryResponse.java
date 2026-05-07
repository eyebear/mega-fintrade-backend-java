package com.ao.portfolio.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReportSummaryResponse {

    private long riskMetricRowCount;
    private long backtestResultRowCount;
    private long strategySignalRowCount;
    private long portfolioEquityPointRowCount;

    private LocalDate latestEquityDate;
    private BigDecimal latestPortfolioWealth;
    private BigDecimal latestBenchmarkWealth;

    private BigDecimal portfolioCumulativeReturn;
    private BigDecimal benchmarkCumulativeReturn;
    private BigDecimal portfolioSharpeRatio;
    private BigDecimal portfolioMaxDrawdown;

    public ReportSummaryResponse() {
    }

    public ReportSummaryResponse(
            long riskMetricRowCount,
            long backtestResultRowCount,
            long strategySignalRowCount,
            long portfolioEquityPointRowCount,
            LocalDate latestEquityDate,
            BigDecimal latestPortfolioWealth,
            BigDecimal latestBenchmarkWealth,
            BigDecimal portfolioCumulativeReturn,
            BigDecimal benchmarkCumulativeReturn,
            BigDecimal portfolioSharpeRatio,
            BigDecimal portfolioMaxDrawdown
    ) {
        this.riskMetricRowCount = riskMetricRowCount;
        this.backtestResultRowCount = backtestResultRowCount;
        this.strategySignalRowCount = strategySignalRowCount;
        this.portfolioEquityPointRowCount = portfolioEquityPointRowCount;
        this.latestEquityDate = latestEquityDate;
        this.latestPortfolioWealth = latestPortfolioWealth;
        this.latestBenchmarkWealth = latestBenchmarkWealth;
        this.portfolioCumulativeReturn = portfolioCumulativeReturn;
        this.benchmarkCumulativeReturn = benchmarkCumulativeReturn;
        this.portfolioSharpeRatio = portfolioSharpeRatio;
        this.portfolioMaxDrawdown = portfolioMaxDrawdown;
    }

    public long getRiskMetricRowCount() {
        return riskMetricRowCount;
    }

    public void setRiskMetricRowCount(long riskMetricRowCount) {
        this.riskMetricRowCount = riskMetricRowCount;
    }

    public long getBacktestResultRowCount() {
        return backtestResultRowCount;
    }

    public void setBacktestResultRowCount(long backtestResultRowCount) {
        this.backtestResultRowCount = backtestResultRowCount;
    }

    public long getStrategySignalRowCount() {
        return strategySignalRowCount;
    }

    public void setStrategySignalRowCount(long strategySignalRowCount) {
        this.strategySignalRowCount = strategySignalRowCount;
    }

    public long getPortfolioEquityPointRowCount() {
        return portfolioEquityPointRowCount;
    }

    public void setPortfolioEquityPointRowCount(long portfolioEquityPointRowCount) {
        this.portfolioEquityPointRowCount = portfolioEquityPointRowCount;
    }

    public LocalDate getLatestEquityDate() {
        return latestEquityDate;
    }

    public void setLatestEquityDate(LocalDate latestEquityDate) {
        this.latestEquityDate = latestEquityDate;
    }

    public BigDecimal getLatestPortfolioWealth() {
        return latestPortfolioWealth;
    }

    public void setLatestPortfolioWealth(BigDecimal latestPortfolioWealth) {
        this.latestPortfolioWealth = latestPortfolioWealth;
    }

    public BigDecimal getLatestBenchmarkWealth() {
        return latestBenchmarkWealth;
    }

    public void setLatestBenchmarkWealth(BigDecimal latestBenchmarkWealth) {
        this.latestBenchmarkWealth = latestBenchmarkWealth;
    }

    public BigDecimal getPortfolioCumulativeReturn() {
        return portfolioCumulativeReturn;
    }

    public void setPortfolioCumulativeReturn(BigDecimal portfolioCumulativeReturn) {
        this.portfolioCumulativeReturn = portfolioCumulativeReturn;
    }

    public BigDecimal getBenchmarkCumulativeReturn() {
        return benchmarkCumulativeReturn;
    }

    public void setBenchmarkCumulativeReturn(BigDecimal benchmarkCumulativeReturn) {
        this.benchmarkCumulativeReturn = benchmarkCumulativeReturn;
    }

    public BigDecimal getPortfolioSharpeRatio() {
        return portfolioSharpeRatio;
    }

    public void setPortfolioSharpeRatio(BigDecimal portfolioSharpeRatio) {
        this.portfolioSharpeRatio = portfolioSharpeRatio;
    }

    public BigDecimal getPortfolioMaxDrawdown() {
        return portfolioMaxDrawdown;
    }

    public void setPortfolioMaxDrawdown(BigDecimal portfolioMaxDrawdown) {
        this.portfolioMaxDrawdown = portfolioMaxDrawdown;
    }
}