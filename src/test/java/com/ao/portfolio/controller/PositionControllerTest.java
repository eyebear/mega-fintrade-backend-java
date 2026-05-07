package com.ao.portfolio.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PositionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createPosition_shouldReturnSavedPosition() throws Exception {
        String json = """
                {
                  "symbol": "AAPL",
                  "quantity": 10,
                  "avgPrice": 150
                }
                """;

        mockMvc.perform(post("/api/positions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol").value("AAPL"))
                .andExpect(jsonPath("$.quantity").value(10))
                .andExpect(jsonPath("$.avgPrice").value(150.0));
    }

    @Test
    void createPosition_shouldReturnBadRequestForInvalidInput() throws Exception {
        String json = """
                {
                  "symbol": "",
                  "quantity": -10,
                  "avgPrice": -5
                }
                """;

        mockMvc.perform(post("/api/positions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getMissingPosition_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/positions/999999"))
                .andExpect(status().isNotFound());
    }
}