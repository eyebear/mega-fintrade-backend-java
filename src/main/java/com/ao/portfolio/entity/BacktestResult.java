package com.ao.portfolio.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "backtest_results")
public class BacktestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Date is required")
    @Column(nullable = false)
    private LocalDate date;

    @NotNull(message = "Portfolio return is required")
    @Column(name = "portfolio_return", nullable = false, precision = 19, scale = 6)
    private BigDecimal portfolioReturn;

    @NotNull(message = "Benchmark return is required")
    @Column(name = "benchmark_return", nullable = false, precision = 19, scale = 6)
    private BigDecimal benchmarkReturn;

    @NotNull(message = "Turnover is required")
    @Column(nullable = false, precision = 19, scale = 6)
    private BigDecimal turnover;

    @NotNull(message = "Portfolio wealth is required")
    @Column(name = "portfolio_wealth", nullable = false, precision = 19, scale = 6)
    private BigDecimal portfolioWealth;

    @NotNull(message = "Benchmark wealth is required")
    @Column(name = "benchmark_wealth", nullable = false, precision = 19, scale = 6)
    private BigDecimal benchmarkWealth;

    public BacktestResult() {
    }

    public BacktestResult(
            LocalDate date,
            BigDecimal portfolioReturn,
            BigDecimal benchmarkReturn,
            BigDecimal turnover,
            BigDecimal portfolioWealth,
            BigDecimal benchmarkWealth
    ) {
        this.date = date;
        this.portfolioReturn = portfolioReturn;
        this.benchmarkReturn = benchmarkReturn;
        this.turnover = turnover;
        this.portfolioWealth = portfolioWealth;
        this.benchmarkWealth = benchmarkWealth;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getPortfolioReturn() {
        return portfolioReturn;
    }

    public void setPortfolioReturn(BigDecimal portfolioReturn) {
        this.portfolioReturn = portfolioReturn;
    }

    public BigDecimal getBenchmarkReturn() {
        return benchmarkReturn;
    }

    public void setBenchmarkReturn(BigDecimal benchmarkReturn) {
        this.benchmarkReturn = benchmarkReturn;
    }

    public BigDecimal getTurnover() {
        return turnover;
    }

    public void setTurnover(BigDecimal turnover) {
        this.turnover = turnover;
    }

    public BigDecimal getPortfolioWealth() {
        return portfolioWealth;
    }

    public void setPortfolioWealth(BigDecimal portfolioWealth) {
        this.portfolioWealth = portfolioWealth;
    }

    public BigDecimal getBenchmarkWealth() {
        return benchmarkWealth;
    }

    public void setBenchmarkWealth(BigDecimal benchmarkWealth) {
        this.benchmarkWealth = benchmarkWealth;
    }
}