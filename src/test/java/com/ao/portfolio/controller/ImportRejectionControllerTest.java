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

import com.ao.portfolio.entity.ImportRejection;
import com.ao.portfolio.repository.ImportRejectionRepository;

@SpringBootTest
@AutoConfigureMockMvc
class ImportRejectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ImportRejectionRepository importRejectionRepository;

    @BeforeEach
    void setUp() {
        importRejectionRepository.deleteAll();
    }

    @Test
    void shouldReturnRecentImportRejections() throws Exception {
        ImportRejection rejection = new ImportRejection(
                "risk_metrics.csv",
                2,
                "BROKEN,ROW",
                "Invalid column count. Expected: 4, actual: 2",
                LocalDateTime.now()
        );

        importRejectionRepository.save(rejection);

        mockMvc.perform(get("/api/import/rejections"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].fileName").value("risk_metrics.csv"))
                .andExpect(jsonPath("$[0].lineNumber").value(2))
                .andExpect(jsonPath("$[0].rawRecord").value("BROKEN,ROW"))
                .andExpect(jsonPath("$[0].reason").value("Invalid column count. Expected: 4, actual: 2"));
    }

    @Test
    void shouldReturnEmptyRejectionListWhenNoRejectionExists() throws Exception {
        mockMvc.perform(get("/api/import/rejections"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}