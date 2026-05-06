package com.ao.portfolio.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BacktestResultResponse {

    private Long id;
    private LocalDate date;
    private BigDecimal portfolioReturn;
    private BigDecimal benchmarkReturn;
    private BigDecimal turnover;
    private BigDecimal portfolioWealth;
    private BigDecimal benchmarkWealth;

    public BacktestResultResponse() {
    }

    public BacktestResultResponse(
            Long id,
            LocalDate date,
            BigDecimal portfolioReturn,
            BigDecimal benchmarkReturn,
            BigDecimal turnover,
            BigDecimal portfolioWealth,
            BigDecimal benchmarkWealth
    ) {
        this.id = id;
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

    public BigDecimal getPortfolioReturn() {
        return portfolioReturn;
    }

    public BigDecimal getBenchmarkReturn() {
        return benchmarkReturn;
    }

    public BigDecimal getTurnover() {
        return turnover;
    }

    public BigDecimal getPortfolioWealth() {
        return portfolioWealth;
    }

    public BigDecimal getBenchmarkWealth() {
        return benchmarkWealth;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setPortfolioReturn(BigDecimal portfolioReturn) {
        this.portfolioReturn = portfolioReturn;
    }

    public void setBenchmarkReturn(BigDecimal benchmarkReturn) {
        this.benchmarkReturn = benchmarkReturn;
    }

    public void setTurnover(BigDecimal turnover) {
        this.turnover = turnover;
    }

    public void setPortfolioWealth(BigDecimal portfolioWealth) {
        this.portfolioWealth = portfolioWealth;
    }

    public void setBenchmarkWealth(BigDecimal benchmarkWealth) {
        this.benchmarkWealth = benchmarkWealth;
    }
}