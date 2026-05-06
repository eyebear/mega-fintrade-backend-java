package com.ao.portfolio.dto;

import java.math.BigDecimal;

public class RiskMetricResponse {

    private Long id;
    private String scope;
    private String symbol;
    private String metric;
    private BigDecimal value;

    public RiskMetricResponse() {
    }

    public RiskMetricResponse(
            Long id,
            String scope,
            String symbol,
            String metric,
            BigDecimal value
    ) {
        this.id = id;
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

    public String getSymbol() {
        return symbol;
    }

    public String getMetric() {
        return metric;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}