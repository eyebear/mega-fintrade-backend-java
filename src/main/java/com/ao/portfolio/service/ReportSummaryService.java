package com.ao.portfolio.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ao.portfolio.dto.ReportSummaryResponse;
import com.ao.portfolio.entity.PortfolioEquityPoint;
import com.ao.portfolio.entity.RiskMetric;
import com.ao.portfolio.repository.BacktestResultRepository;
import com.ao.portfolio.repository.PortfolioEquityPointRepository;
import com.ao.portfolio.repository.RiskMetricRepository;
import com.ao.portfolio.repository.StrategySignalRepository;

@Service
public class ReportSummaryService {

    private final RiskMetricRepository riskMetricRepository;
    private final BacktestResultRepository backtestResultRepository;
    private final StrategySignalRepository strategySignalRepository;
    private final PortfolioEquityPointRepository portfolioEquityPointRepository;

    public ReportSummaryService(
            RiskMetricRepository riskMetricRepository,
            BacktestResultRepository backtestResultRepository,
            StrategySignalRepository strategySignalRepository,
            PortfolioEquityPointRepository portfolioEquityPointRepository
    ) {
        this.riskMetricRepository = riskMetricRepository;
        this.backtestResultRepository = backtestResultRepository;
        this.strategySignalRepository = strategySignalRepository;
        this.portfolioEquityPointRepository = portfolioEquityPointRepository;
    }

    public ReportSummaryResponse getSummary() {
        long riskMetricRowCount = riskMetricRepository.count();
        long backtestResultRowCount = backtestResultRepository.count();
        long strategySignalRowCount = strategySignalRepository.count();
        long portfolioEquityPointRowCount = portfolioEquityPointRepository.count();

        List<PortfolioEquityPoint> equityPoints =
                portfolioEquityPointRepository.findAllByOrderByDateAsc();

        PortfolioEquityPoint latestEquityPoint = equityPoints.isEmpty()
                ? null
                : equityPoints.get(equityPoints.size() - 1);

        BigDecimal latestPortfolioWealth = latestEquityPoint == null
                ? null
                : latestEquityPoint.getPortfolioWealth();

        BigDecimal latestBenchmarkWealth = latestEquityPoint == null
                ? null
                : latestEquityPoint.getBenchmarkWealth();

        BigDecimal portfolioCumulativeReturn = latestPortfolioWealth == null
                ? null
                : latestPortfolioWealth.subtract(BigDecimal.ONE);

        BigDecimal benchmarkCumulativeReturn = latestBenchmarkWealth == null
                ? null
                : latestBenchmarkWealth.subtract(BigDecimal.ONE);

        BigDecimal portfolioSharpeRatio = findPortfolioMetricValue("sharpe_ratio");
        BigDecimal portfolioMaxDrawdown = findPortfolioMetricValue("max_drawdown");

        return new ReportSummaryResponse(
                riskMetricRowCount,
                backtestResultRowCount,
                strategySignalRowCount,
                portfolioEquityPointRowCount,
                latestEquityPoint == null ? null : latestEquityPoint.getDate(),
                latestPortfolioWealth,
                latestBenchmarkWealth,
                portfolioCumulativeReturn,
                benchmarkCumulativeReturn,
                portfolioSharpeRatio,
                portfolioMaxDrawdown
        );
    }

    private BigDecimal findPortfolioMetricValue(String metricName) {
        return riskMetricRepository.findByScopeAndSymbol("portfolio", "ALL")
                .stream()
                .filter(riskMetric -> metricName.equals(riskMetric.getMetric()))
                .map(RiskMetric::getValue)
                .findFirst()
                .orElse(null);
    }
}