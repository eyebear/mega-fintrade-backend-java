package com.ao.portfolio.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ao.portfolio.dto.BacktestResultResponse;
import com.ao.portfolio.service.BacktestResultQueryService;

@RestController
@RequestMapping("/api/backtests")
public class BacktestController {

    private final BacktestResultQueryService backtestResultQueryService;

    public BacktestController(BacktestResultQueryService backtestResultQueryService) {
        this.backtestResultQueryService = backtestResultQueryService;
    }

    @GetMapping("/results")
    public List<BacktestResultResponse> getBacktestResults(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate
    ) {
        if (startDate != null && endDate != null) {
            return backtestResultQueryService.getBacktestResultsByDateRange(startDate, endDate);
        }

        return backtestResultQueryService.getAllBacktestResults();
    }
}