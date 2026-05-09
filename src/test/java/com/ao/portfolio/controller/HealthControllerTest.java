package com.ao.portfolio.controller;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

class HealthControllerTest {

    @Test
    void healthShouldReturnUpStatusAndServiceName() {
        HealthController controller = new HealthController();

        Map<String, Object> response = controller.health();

        assertEquals("UP", response.get("status"));
        assertEquals("mega-fintrade-backend-java", response.get("service"));
        assertNotNull(response.get("timestamp"));
    }
}