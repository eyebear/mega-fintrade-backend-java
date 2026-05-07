package com.ao.portfolio.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ao.portfolio.entity.BacktestResult;
import com.ao.portfolio.entity.PortfolioEquityPoint;
import com.ao.portfolio.entity.RiskMetric;
import com.ao.portfolio.entity.StrategySignal;
import com.ao.portfolio.repository.BacktestResultRepository;
import com.ao.portfolio.repository.PortfolioEquityPointRepository;
import com.ao.portfolio.repository.RiskMetricRepository;
import com.ao.portfolio.repository.StrategySignalRepository;

@SpringBootTest
@AutoConfigureMockMvc
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
    void shouldReturnReportSummary() throws Exception {
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
                LocalDate.of(2026, 1, 3),
                new BigDecimal("1.150000"),
                new BigDecimal("1.100000")
        ));

        mockMvc.perform(get("/api/reports/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.riskMetricRowCount").value(2))
                .andExpect(jsonPath("$.backtestResultRowCount").value(1))
                .andExpect(jsonPath("$.strategySignalRowCount").value(1))
                .andExpect(jsonPath("$.portfolioEquityPointRowCount").value(1))
                .andExpect(jsonPath("$.latestEquityDate").value("2026-01-03"))
                .andExpect(jsonPath("$.latestPortfolioWealth").value(1.150000))
                .andExpect(jsonPath("$.latestBenchmarkWealth").value(1.100000))
                .andExpect(jsonPath("$.portfolioCumulativeReturn").value(0.150000))
                .andExpect(jsonPath("$.benchmarkCumulativeReturn").value(0.100000))
                .andExpect(jsonPath("$.portfolioSharpeRatio").value(1.250000))
                .andExpect(jsonPath("$.portfolioMaxDrawdown").value(-0.180000));
    }

    @Test
    void shouldReturnEmptyReportSummaryWhenNoDataExists() throws Exception {
        mockMvc.perform(get("/api/reports/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.riskMetricRowCount").value(0))
                .andExpect(jsonPath("$.backtestResultRowCount").value(0))
                .andExpect(jsonPath("$.strategySignalRowCount").value(0))
                .andExpect(jsonPath("$.portfolioEquityPointRowCount").value(0))
                .andExpect(jsonPath("$.latestEquityDate").doesNotExist())
                .andExpect(jsonPath("$.latestPortfolioWealth").doesNotExist())
                .andExpect(jsonPath("$.latestBenchmarkWealth").doesNotExist())
                .andExpect(jsonPath("$.portfolioCumulativeReturn").doesNotExist())
                .andExpect(jsonPath("$.benchmarkCumulativeReturn").doesNotExist())
                .andExpect(jsonPath("$.portfolioSharpeRatio").doesNotExist())
                .andExpect(jsonPath("$.portfolioMaxDrawdown").doesNotExist());
    }
}