package com.ao.portfolio.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "risk_metrics")
public class RiskMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Scope is required")
    @Size(max = 50, message = "Scope must not exceed 50 characters")
    @Column(nullable = false, length = 50)
    private String scope;

    @NotBlank(message = "Symbol is required")
    @Size(max = 20, message = "Symbol must not exceed 20 characters")
    @Column(nullable = false, length = 20)
    private String symbol;

    @NotBlank(message = "Metric is required")
    @Size(max = 100, message = "Metric name must not exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String metric;

    @NotNull(message = "Metric value is required")
    @Column(nullable = false, precision = 19, scale = 6)
    private BigDecimal value;

    public RiskMetric() {
    }

    public RiskMetric(String scope, String symbol, String metric, BigDecimal value) {
        this.scope = scope;
        this.symbol = symbol;
        this.metric = metric;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}