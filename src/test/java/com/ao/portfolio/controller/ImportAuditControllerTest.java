package com.ao.portfolio.controller;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ao.portfolio.entity.ImportAudit;
import com.ao.portfolio.repository.ImportAuditRepository;

@SpringBootTest
@AutoConfigureMockMvc
class ImportAuditControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ImportAuditRepository importAuditRepository;

    @BeforeEach
    void setUp() {
        importAuditRepository.deleteAll();
    }

    @Test
    void shouldReturnRecentImportAudits() throws Exception {
        ImportAudit audit = new ImportAudit(
                "SCHEDULED_QUANT_IMPORT",
                "SUCCESS",
                LocalDateTime.now()
        );

        audit.setCompletedAt(LocalDateTime.now());
        audit.setRiskMetricRows(2);
        audit.setBacktestResultRows(3);
        audit.setStrategySignalRows(4);
        audit.setPortfolioEquityRows(5);

        importAuditRepository.save(audit);

        mockMvc.perform(get("/api/import/audit"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].jobName").value("SCHEDULED_QUANT_IMPORT"))
                .andExpect(jsonPath("$[0].status").value("SUCCESS"))
                .andExpect(jsonPath("$[0].riskMetricRows").value(2))
                .andExpect(jsonPath("$[0].backtestResultRows").value(3))
                .andExpect(jsonPath("$[0].strategySignalRows").value(4))
                .andExpect(jsonPath("$[0].portfolioEquityRows").value(5));
    }

    @Test
    void shouldReturnEmptyAuditListWhenNoAuditExists() throws Exception {
        mockMvc.perform(get("/api/import/audit"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}