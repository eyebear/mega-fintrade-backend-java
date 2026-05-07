package com.ao.portfolio.dto;

import java.time.LocalDateTime;

public class BatchRunSummary {

    private String status;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;

    private String riskMetricJobStatus;
    private String backtestResultJobStatus;
    private String strategySignalJobStatus;
    private String portfolioEquityJobStatus;

    public BatchRunSummary() {
    }

    public BatchRunSummary(
            String status,
            LocalDateTime startedAt,
            LocalDateTime finishedAt,
            String riskMetricJobStatus,
            String backtestResultJobStatus,
            String strategySignalJobStatus,
            String portfolioEquityJobStatus
    ) {
        this.status = status;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.riskMetricJobStatus = riskMetricJobStatus;
        this.backtestResultJobStatus = backtestResultJobStatus;
        this.strategySignalJobStatus = strategySignalJobStatus;
        this.portfolioEquityJobStatus = portfolioEquityJobStatus;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public String getRiskMetricJobStatus() {
        return riskMetricJobStatus;
    }

    public String getBacktestResultJobStatus() {
        return backtestResultJobStatus;
    }

    public String getStrategySignalJobStatus() {
        return strategySignalJobStatus;
    }

    public String getPortfolioEquityJobStatus() {
        return portfolioEquityJobStatus;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }

    public void setRiskMetricJobStatus(String riskMetricJobStatus) {
        this.riskMetricJobStatus = riskMetricJobStatus;
    }

    public void setBacktestResultJobStatus(String backtestResultJobStatus) {
        this.backtestResultJobStatus = backtestResultJobStatus;
    }

    public void setStrategySignalJobStatus(String strategySignalJobStatus) {
        this.strategySignalJobStatus = strategySignalJobStatus;
    }

    public void setPortfolioEquityJobStatus(String portfolioEquityJobStatus) {
        this.portfolioEquityJobStatus = portfolioEquityJobStatus;
    }
}