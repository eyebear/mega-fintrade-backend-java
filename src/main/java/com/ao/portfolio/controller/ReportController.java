package com.ao.portfolio.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ao.portfolio.dto.PortfolioEquityPointResponse;
import com.ao.portfolio.dto.ReportSummaryResponse;
import com.ao.portfolio.dto.RiskMetricResponse;
import com.ao.portfolio.service.PortfolioEquityQueryService;
import com.ao.portfolio.service.ReportSummaryService;
import com.ao.portfolio.service.RiskMetricQueryService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final RiskMetricQueryService riskMetricQueryService;
    private final PortfolioEquityQueryService portfolioEquityQueryService;
    private final ReportSummaryService reportSummaryService;

    public ReportController(
            RiskMetricQueryService riskMetricQueryService,
            PortfolioEquityQueryService portfolioEquityQueryService,
            ReportSummaryService reportSummaryService
    ) {
        this.riskMetricQueryService = riskMetricQueryService;
        this.portfolioEquityQueryService = portfolioEquityQueryService;
        this.reportSummaryService = reportSummaryService;
    }

    @GetMapping("/risk")
    public List<RiskMetricResponse> getRiskMetrics(
            @RequestParam(required = false) String scope,
            @RequestParam(required = false) String symbol
    ) {
        if (scope != null && symbol != null) {
            return riskMetricQueryService.getRiskMetricsByScopeAndSymbol(scope, symbol);
        }

        if (scope != null) {
            return riskMetricQueryService.getRiskMetricsByScope(scope);
        }

        if (symbol != null) {
            return riskMetricQueryService.getRiskMetricsBySymbol(symbol);
        }

        return riskMetricQueryService.getAllRiskMetrics();
    }

    @GetMapping("/equity-curve")
    public List<PortfolioEquityPointResponse> getPortfolioEquityCurve(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate
    ) {
        if (startDate != null && endDate != null) {
            return portfolioEquityQueryService.getPortfolioEquityPointsByDateRange(startDate, endDate);
        }

        return portfolioEquityQueryService.getAllPortfolioEquityPoints();
    }

    @GetMapping("/summary")
    public ReportSummaryResponse getReportSummary() {
        return reportSummaryService.getSummary();
    }
}