package com.ao.portfolio.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ao.portfolio.dto.ReportSummaryResponse;
import com.ao.portfolio.entity.BacktestResult;
import com.ao.portfolio.entity.PortfolioEquityPoint;
import com.ao.portfolio.entity.RiskMetric;
import com.ao.portfolio.entity.StrategySignal;
import com.ao.portfolio.repository.BacktestResultRepository;
import com.ao.portfolio.repository.PortfolioEquityPointRepository;
import com.ao.portfolio.repository.RiskMetricRepository;
import com.ao.portfolio.repository.StrategySignalRepository;

@SpringBootTest
class ReportSummaryServiceTest {

    @Autowired
    private ReportSummaryService reportSummaryService;

    @Autowired
    private RiskMetricRepository riskMetricRepository;

    @Autowired
    private BacktestResultRepository backtestResultRepository;

    @Autowired
    private StrategySignalRepository strategySignalRepository;

    @Autowired
    private PortfolioEquityPointRepository portfolioEquityPointRepository;

    @BeforeEach
    void setUp() {
        strategySignalRepository.deleteAll();
        portfolioEquityPointRepository.deleteAll();
        backtestResultRepository.deleteAll();
        riskMetricRepository.deleteAll();
    }

    @Test
    void shouldReturnEmptySummaryWhenNoDataExists() {
        ReportSummaryResponse summary = reportSummaryService.getSummary();

        assertThat(summary.getRiskMetricRowCount()).isZero();
        assertThat(summary.getBacktestResultRowCount()).isZero();
        assertThat(summary.getStrategySignalRowCount()).isZero();
        assertThat(summary.getPortfolioEquityPointRowCount()).isZero();

        assertThat(summary.getLatestEquityDate()).isNull();
        assertThat(summary.getLatestPortfolioWealth()).isNull();
        assertThat(summary.getLatestBenchmarkWealth()).isNull();
        assertThat(summary.getPortfolioCumulativeReturn()).isNull();
        assertThat(summary.getBenchmarkCumulativeReturn()).isNull();
        assertThat(summary.getPortfolioSharpeRatio()).isNull();
        assertThat(summary.getPortfolioMaxDrawdown()).isNull();
    }

    @Test
    void shouldCalculateReportSummaryFromSavedData() {
        riskMetricRepository.save(new RiskMetric(
                "portfolio",
                "ALL",
                "sharpe_ratio",
                new BigDecimal("1.250000")
        ));

        riskMetricRepository.save(new RiskMetric(
                "portfolio",
                "ALL",
                "max_drawdown",
                new BigDecimal("-0.180000")
        ));

        backtestResultRepository.save(new BacktestResult(
                LocalDate.of(2026, 1, 2),
                new BigDecimal("0.010000"),
                new BigDecimal("0.008000"),
                new BigDecimal("0.120000"),
                new BigDecimal("1.010000"),
                new BigDecimal("1.008000")
        ));

        strategySignalRepository.save(new StrategySignal(
                LocalDate.of(2026, 1, 2),
                new BigDecimal("190.000000"),
                new BigDecimal("188.000000"),
                new BigDecimal("185.000000"),
                new BigDecimal("1.000000"),
                new BigDecimal("410.000000"),
                new BigDecimal("408.000000"),
                new BigDecimal("405.000000"),
                new BigDecimal("1.000000"),
                new BigDecimal("140.000000"),
                new BigDecimal("138.000000"),
                new BigDecimal("135.000000"),
                new BigDecimal("1.000000"),
                new BigDecimal("500.000000"),
                new BigDecimal("498.000000"),
                new BigDecimal("495.000000"),
                new BigDecimal("1.000000")
        ));

        portfolioEquityPointRepository.save(new PortfolioEquityPoint(
                LocalDate.of(2026, 1, 2),
                new BigDecimal("1.010000"),
                new BigDecimal("1.008000")
        ));

        portfolioEquityPointRepository.save(new PortfolioEquityPoint(
                LocalDate.of(2026, 1, 3),
                new BigDecimal("1.150000"),
                new BigDecimal("1.100000")
        ));

        ReportSummaryResponse summary = reportSummaryService.getSummary();

        assertThat(summary.getRiskMetricRowCount()).isEqualTo(2);
        assertThat(summary.getBacktestResultRowCount()).isEqualTo(1);
        assertThat(summary.getStrategySignalRowCount()).isEqualTo(1);
        assertThat(summary.getPortfolioEquityPointRowCount()).isEqualTo(2);

        assertThat(summary.getLatestEquityDate()).isEqualTo(LocalDate.of(2026, 1, 3));

        assertThat(summary.getLatestPortfolioWealth()).isEqualByComparingTo(new BigDecimal("1.150000"));
        assertThat(summary.getLatestBenchmarkWealth()).isEqualByComparingTo(new BigDecimal("1.100000"));

        assertThat(summary.getPortfolioCumulativeReturn()).isEqualByComparingTo(new BigDecimal("0.150000"));
        assertThat(summary.getBenchmarkCumulativeReturn()).isEqualByComparingTo(new BigDecimal("0.100000"));

        assertThat(summary.getPortfolioSharpeRatio()).isEqualByComparingTo(new BigDecimal("1.250000"));
        assertThat(summary.getPortfolioMaxDrawdown()).isEqualByComparingTo(new BigDecimal("-0.180000"));
    }
}