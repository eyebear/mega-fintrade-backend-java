package com.ao.portfolio.repository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ao.portfolio.entity.ImportRejection;

@SpringBootTest
class ImportRejectionRepositoryTest {

    @Autowired
    private ImportRejectionRepository importRejectionRepository;

    @BeforeEach
    void setUp() {
        importRejectionRepository.deleteAll();
    }

    @Test
    void shouldSaveAndFindImportRejection() {
        ImportRejection rejection = new ImportRejection(
                "risk_metrics.csv",
                3,
                "BROKEN,ROW",
                "Invalid column count. Expected: 4, actual: 2",
                LocalDateTime.now()
        );

        ImportRejection savedRejection = importRejectionRepository.save(rejection);

        assertThat(savedRejection.getId()).isNotNull();
        assertThat(savedRejection.getFileName()).isEqualTo("risk_metrics.csv");
        assertThat(savedRejection.getLineNumber()).isEqualTo(3);
        assertThat(savedRejection.getRawRecord()).isEqualTo("BROKEN,ROW");
        assertThat(savedRejection.getReason()).isEqualTo("Invalid column count. Expected: 4, actual: 2");
    }

    @Test
    void shouldFindRecentRejectionsOrderedByCreatedAtDescending() {
        ImportRejection olderRejection = new ImportRejection(
                "risk_metrics.csv",
                2,
                "OLD,BROKEN",
                "Old rejection",
                LocalDateTime.now().minusHours(2)
        );

        ImportRejection newerRejection = new ImportRejection(
                "portfolio_equity.csv",
                5,
                "NEW,BROKEN",
                "New rejection",
                LocalDateTime.now()
        );

        importRejectionRepository.save(olderRejection);
        importRejectionRepository.save(newerRejection);

        List<ImportRejection> rejections = importRejectionRepository.findTop50ByOrderByCreatedAtDesc();

        assertThat(rejections).hasSize(2);
        assertThat(rejections.get(0).getFileName()).isEqualTo("portfolio_equity.csv");
        assertThat(rejections.get(1).getFileName()).isEqualTo("risk_metrics.csv");
    }
}