package com.ao.portfolio.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "import_audit")
public class ImportAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_name", nullable = false, length = 100)
    private String jobName;

    @Column(nullable = false, length = 30)
    private String status;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "risk_metric_rows")
    private int riskMetricRows;

    @Column(name = "backtest_result_rows")
    private int backtestResultRows;

    @Column(name = "strategy_signal_rows")
    private int strategySignalRows;

    @Column(name = "portfolio_equity_rows")
    private int portfolioEquityRows;

    @Column(name = "error_message", length = 1000)
    private String errorMessage;

    public ImportAudit() {
    }

    public ImportAudit(String jobName, String status, LocalDateTime startedAt) {
        this.jobName = jobName;
        this.status = status;
        this.startedAt = startedAt;
    }

    public Long getId() {
        return id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public int getRiskMetricRows() {
        return riskMetricRows;
    }

    public void setRiskMetricRows(int riskMetricRows) {
        this.riskMetricRows = riskMetricRows;
    }

    public int getBacktestResultRows() {
        return backtestResultRows;
    }

    public void setBacktestResultRows(int backtestResultRows) {
        this.backtestResultRows = backtestResultRows;
    }

    public int getStrategySignalRows() {
        return strategySignalRows;
    }

    public void setStrategySignalRows(int strategySignalRows) {
        this.strategySignalRows = strategySignalRows;
    }

    public int getPortfolioEquityRows() {
        return portfolioEquityRows;
    }

    public void setPortfolioEquityRows(int portfolioEquityRows) {
        this.portfolioEquityRows = portfolioEquityRows;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}