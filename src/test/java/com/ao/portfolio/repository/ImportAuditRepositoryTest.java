package com.ao.portfolio.repository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ao.portfolio.entity.ImportAudit;

@SpringBootTest
class ImportAuditRepositoryTest {

    @Autowired
    private ImportAuditRepository importAuditRepository;

    @BeforeEach
    void setUp() {
        importAuditRepository.deleteAll();
    }

    @Test
    void shouldSaveAndFindImportAudit() {
        ImportAudit audit = new ImportAudit(
                "TEST_IMPORT_JOB",
                "SUCCESS",
                LocalDateTime.now()
        );

        audit.setCompletedAt(LocalDateTime.now());
        audit.setRiskMetricRows(2);
        audit.setBacktestResultRows(3);
        audit.setStrategySignalRows(4);
        audit.setPortfolioEquityRows(5);

        ImportAudit savedAudit = importAuditRepository.save(audit);

        assertThat(savedAudit.getId()).isNotNull();
        assertThat(savedAudit.getJobName()).isEqualTo("TEST_IMPORT_JOB");
        assertThat(savedAudit.getStatus()).isEqualTo("SUCCESS");
        assertThat(savedAudit.getRiskMetricRows()).isEqualTo(2);
        assertThat(savedAudit.getBacktestResultRows()).isEqualTo(3);
        assertThat(savedAudit.getStrategySignalRows()).isEqualTo(4);
        assertThat(savedAudit.getPortfolioEquityRows()).isEqualTo(5);
    }

    @Test
    void shouldFindRecentAuditsOrderedByStartedAtDescending() {
        ImportAudit olderAudit = new ImportAudit(
                "OLDER_JOB",
                "SUCCESS",
                LocalDateTime.now().minusHours(2)
        );

        ImportAudit newerAudit = new ImportAudit(
                "NEWER_JOB",
                "FAILED",
                LocalDateTime.now()
        );

        importAuditRepository.save(olderAudit);
        importAuditRepository.save(newerAudit);

        List<ImportAudit> audits = importAuditRepository.findTop20ByOrderByStartedAtDesc();

        assertThat(audits).hasSize(2);
        assertThat(audits.get(0).getJobName()).isEqualTo("NEWER_JOB");
        assertThat(audits.get(1).getJobName()).isEqualTo("OLDER_JOB");
    }
}