package com.ao.portfolio.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ao.portfolio.dto.ReportSummaryResponse;
import com.ao.portfolio.service.ReportSummaryService;

@Component
public class ReportRecalculationScheduler {

    private static final Logger logger = LoggerFactory.getLogger(ReportRecalculationScheduler.class);

    private final ReportSummaryService reportSummaryService;

    public ReportRecalculationScheduler(ReportSummaryService reportSummaryService) {
        this.reportSummaryService = reportSummaryService;
    }

    @Scheduled(initialDelay = 60000, fixedRate = 300000)
    public void runScheduledReportRecalculation() {
        logger.info("Scheduled report recalculation job started.");

        try {
            ReportSummaryResponse summary = reportSummaryService.getSummary();

            logger.info(
                    "Scheduled report recalculation completed. riskMetrics={}, backtestResults={}, strategySignals={}, portfolioEquityPoints={}, latestEquityDate={}, latestPortfolioWealth={}, portfolioSharpeRatio={}, portfolioMaxDrawdown={}",
                    summary.getRiskMetricRowCount(),
                    summary.getBacktestResultRowCount(),
                    summary.getStrategySignalRowCount(),
                    summary.getPortfolioEquityPointRowCount(),
                    summary.getLatestEquityDate(),
                    summary.getLatestPortfolioWealth(),
                    summary.getPortfolioSharpeRatio(),
                    summary.getPortfolioMaxDrawdown()
            );
        } catch (Exception e) {
            logger.error("Scheduled report recalculation job failed.", e);
        }
    }
}