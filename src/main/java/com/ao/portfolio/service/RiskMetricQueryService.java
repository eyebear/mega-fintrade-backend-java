package com.ao.portfolio.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ao.portfolio.dto.RiskMetricResponse;
import com.ao.portfolio.entity.RiskMetric;
import com.ao.portfolio.repository.RiskMetricRepository;

@Service
public class RiskMetricQueryService {

    private final RiskMetricRepository riskMetricRepository;

    public RiskMetricQueryService(RiskMetricRepository riskMetricRepository) {
        this.riskMetricRepository = riskMetricRepository;
    }

    public List<RiskMetricResponse> getAllRiskMetrics() {
        return riskMetricRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<RiskMetricResponse> getRiskMetricsByScope(String scope) {
        return riskMetricRepository.findByScope(scope)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<RiskMetricResponse> getRiskMetricsBySymbol(String symbol) {
        return riskMetricRepository.findBySymbol(symbol)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<RiskMetricResponse> getRiskMetricsByScopeAndSymbol(String scope, String symbol) {
        return riskMetricRepository.findByScopeAndSymbol(scope, symbol)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private RiskMetricResponse toResponse(RiskMetric riskMetric) {
        return new RiskMetricResponse(
                riskMetric.getId(),
                riskMetric.getScope(),
                riskMetric.getSymbol(),
                riskMetric.getMetric(),
                riskMetric.getValue()
        );
    }
}