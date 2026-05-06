package com.ao.portfolio.service;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

import com.ao.portfolio.entity.Position;
import com.ao.portfolio.repository.PositionRepository;

public class PositionServiceTest {

    private PositionService positionService;

    @BeforeEach
    void setUp() {
        PositionRepository mockRepository = mock(PositionRepository.class);
        positionService = new PositionService(mockRepository);
    }

    @Test
    void calculatePnL_shouldReturnCorrectProfit() {
        Position position = new Position();
        position.setSymbol("AAPL");
        position.setQuantity(10);
        position.setAvgPrice(BigDecimal.valueOf(150));

        double result = positionService.calculatePnL(position, 160);

        assertEquals(100.0, result);
    }

    @Test
    void calculatePnL_shouldReturnCorrectLoss() {
        Position position = new Position();
        position.setSymbol("AAPL");
        position.setQuantity(10);
        position.setAvgPrice(BigDecimal.valueOf(150));

        double result = positionService.calculatePnL(position, 140);

        assertEquals(-100.0, result);
    }
}