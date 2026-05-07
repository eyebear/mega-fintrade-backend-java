package com.ao.portfolio.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ao.portfolio.dto.StrategySignalResponse;
import com.ao.portfolio.service.StrategySignalQueryService;

@RestController
@RequestMapping("/api/signals")
public class SignalController {

    private final StrategySignalQueryService strategySignalQueryService;

    public SignalController(StrategySignalQueryService strategySignalQueryService) {
        this.strategySignalQueryService = strategySignalQueryService;
    }

    @GetMapping
    public List<StrategySignalResponse> getStrategySignals(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate
    ) {
        if (startDate != null && endDate != null) {
            return strategySignalQueryService.getStrategySignalsByDateRange(startDate, endDate);
        }

        return strategySignalQueryService.getAllStrategySignals();
    }
}