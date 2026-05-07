package com.ao.portfolio.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ao.portfolio.dto.QuantCsvImportSummary;
import com.ao.portfolio.entity.ImportAudit;
import com.ao.portfolio.repository.ImportAuditRepository;

@Service
public class ImportAuditService {

    private final ImportAuditRepository importAuditRepository;

    public ImportAuditService(ImportAuditRepository importAuditRepository) {
        this.importAuditRepository = importAuditRepository;
    }

    public ImportAudit startImport(String jobName) {
        ImportAudit audit = new ImportAudit(jobName, "RUNNING", LocalDateTime.now());
        return importAuditRepository.save(audit);
    }

    public ImportAudit markSuccess(ImportAudit audit, QuantCsvImportSummary summary) {
        audit.setStatus("SUCCESS");
        audit.setCompletedAt(LocalDateTime.now());

        audit.setRiskMetricRows(summary.getRiskMetricRowsImported());
        audit.setBacktestResultRows(summary.getBacktestResultRowsImported());
        audit.setStrategySignalRows(summary.getStrategySignalRowsImported());
        audit.setPortfolioEquityRows(summary.getPortfolioEquityRowsImported());

        return importAuditRepository.save(audit);
    }

    public ImportAudit markFailure(ImportAudit audit, Exception exception) {
        audit.setStatus("FAILED");
        audit.setCompletedAt(LocalDateTime.now());
        audit.setErrorMessage(exception.getMessage());

        return importAuditRepository.save(audit);
    }

    public List<ImportAudit> getRecentAudits() {
        return importAuditRepository.findTop20ByOrderByStartedAtDesc();
    }
}