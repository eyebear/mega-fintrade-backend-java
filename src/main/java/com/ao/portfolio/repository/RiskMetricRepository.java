package com.ao.portfolio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ao.portfolio.entity.RiskMetric;

@Repository
public interface RiskMetricRepository extends JpaRepository<RiskMetric, Long> {

    List<RiskMetric> findByScope(String scope);

    List<RiskMetric> findBySymbol(String symbol);

    List<RiskMetric> findByScopeAndSymbol(String scope, String symbol);
}