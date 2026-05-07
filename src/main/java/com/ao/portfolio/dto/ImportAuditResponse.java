package com.ao.portfolio.dto;

import java.time.LocalDateTime;

import com.ao.portfolio.entity.ImportAudit;

public class ImportAuditResponse {

    private Long id;
    private String jobName;
    private String status;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private int riskMetricRows;
    private int backtestResultRows;
    private int strategySignalRows;
    private int portfolioEquityRows;
    private String errorMessage;

    public ImportAuditResponse() {
    }

    public ImportAuditResponse(ImportAudit audit) {
        this.id = audit.getId();
        this.jobName = audit.getJobName();
        this.status = audit.getStatus();
        this.startedAt = audit.getStartedAt();
        this.completedAt = audit.getCompletedAt();
        this.riskMetricRows = audit.getRiskMetricRows();
        this.backtestResultRows = audit.getBacktestResultRows();
        this.strategySignalRows = audit.getStrategySignalRows();
        this.portfolioEquityRows = audit.getPortfolioEquityRows();
        this.errorMessage = audit.getErrorMessage();
    }

    public Long getId() {
        return id;
    }

    public String getJobName() {
        return jobName;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public int getRiskMetricRows() {
        return riskMetricRows;
    }

    public int getBacktestResultRows() {
        return backtestResultRows;
    }

    public int getStrategySignalRows() {
        return strategySignalRows;
    }

    public int getPortfolioEquityRows() {
        return portfolioEquityRows;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}