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
@Table(name = "portfolio_equity_points")
public class PortfolioEquityPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Date is required")
    @Column(nullable = false)
    private LocalDate date;

    @NotNull(message = "Portfolio wealth is required")
    @Column(name = "portfolio_wealth", nullable = false, precision = 19, scale = 6)
    private BigDecimal portfolioWealth;

    @NotNull(message = "Benchmark wealth is required")
    @Column(name = "benchmark_wealth", nullable = false, precision = 19, scale = 6)
    private BigDecimal benchmarkWealth;

    public PortfolioEquityPoint() {
    }

    public PortfolioEquityPoint(
            LocalDate date,
            BigDecimal portfolioWealth,
            BigDecimal benchmarkWealth
    ) {
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

    public void setDate(LocalDate date) {
        this.date = date;
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