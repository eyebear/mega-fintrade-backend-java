package com.ao.portfolio.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PortfolioEquityPointResponse {

    private Long id;
    private LocalDate date;
    private BigDecimal portfolioWealth;
    private BigDecimal benchmarkWealth;

    public PortfolioEquityPointResponse() {
    }

    public PortfolioEquityPointResponse(
            Long id,
            LocalDate date,
            BigDecimal portfolioWealth,
            BigDecimal benchmarkWealth
    ) {
        this.id = id;
        this.date = date;
        this.portfolioWealth = portfolioWealth;
        this.benchmarkWealth = benchmarkWealth;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
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

    public void setPortfolioWealth(BigDecimal portfolioWealth) {
        this.portfolioWealth = portfolioWealth;
    }

    public void setBenchmarkWealth(BigDecimal benchmarkWealth) {
        this.benchmarkWealth = benchmarkWealth;
    }
}