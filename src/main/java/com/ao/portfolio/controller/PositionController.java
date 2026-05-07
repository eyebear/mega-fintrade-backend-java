package com.ao.portfolio.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ao.portfolio.dto.PositionRequest;
import com.ao.portfolio.dto.PositionResponse;
import com.ao.portfolio.entity.Position;
import com.ao.portfolio.service.PositionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/positions")
public class PositionController {

    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping
    public List<PositionResponse> getAllPositions() {
        return positionService.getAllPositions();
    }

    @PostMapping
    public PositionResponse createPosition(@Valid @RequestBody PositionRequest request) {
        return positionService.savePosition(request);
    }

    @PostMapping("/batch")
    public List<PositionResponse> createPositionsBatch(
            @RequestBody List<@Valid PositionRequest> requests
    ) {
        return positionService.saveAll(requests);
    }

    @GetMapping("/{id}")
    public PositionResponse getPositionById(@PathVariable Long id) {
        return positionService.getPositionById(id);
    }

    @GetMapping("/{id}/pnl/{price}")
    public double getProfitAndLossByPositionId(
            @PathVariable Long id,
            @PathVariable double price
    ) {
        Position position = positionService.getEntityById(id);
        return positionService.calculatePnL(position, price);
    }
}