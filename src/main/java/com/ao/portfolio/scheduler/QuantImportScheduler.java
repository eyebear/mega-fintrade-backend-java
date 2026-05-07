package com.ao.portfolio.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ao.portfolio.dto.QuantCsvImportSummary;
import com.ao.portfolio.entity.ImportAudit;
import com.ao.portfolio.service.ImportAuditService;
import com.ao.portfolio.service.QuantCsvImportOrchestrationService;

@Component
@ConditionalOnProperty(
        name = "app.scheduling.enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class QuantImportScheduler {

    private static final Logger logger = LoggerFactory.getLogger(QuantImportScheduler.class);

    private final QuantCsvImportOrchestrationService quantCsvImportOrchestrationService;
    private final ImportAuditService importAuditService;

    public QuantImportScheduler(
            QuantCsvImportOrchestrationService quantCsvImportOrchestrationService,
            ImportAuditService importAuditService) {
        this.quantCsvImportOrchestrationService = quantCsvImportOrchestrationService;
        this.importAuditService = importAuditService;
    }

    @Scheduled(fixedRate = 300000)
    public void runScheduledQuantImport() {
        logger.info("Scheduled quant import job started.");

        ImportAudit audit = importAuditService.startImport("SCHEDULED_QUANT_IMPORT");

        try {
            QuantCsvImportSummary summary = quantCsvImportOrchestrationService.importAllDefaultFiles();

            importAuditService.markSuccess(audit, summary);

            logger.info(
                    "Scheduled quant import job completed. riskMetrics={}, backtestResults={}, strategySignals={}, portfolioEquity={}",
                    summary.getRiskMetricRowsImported(),
                    summary.getBacktestResultRowsImported(),
                    summary.getStrategySignalRowsImported(),
                    summary.getPortfolioEquityRowsImported()
            );
        } catch (Exception e) {
            importAuditService.markFailure(audit, e);
            logger.error("Scheduled quant import job failed.", e);
        }
    }
}