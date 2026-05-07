package com.ao.portfolio.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ao.portfolio.dto.BacktestResultResponse;
import com.ao.portfolio.entity.BacktestResult;
import com.ao.portfolio.repository.BacktestResultRepository;

@Service
public class BacktestResultQueryService {

    private final BacktestResultRepository backtestResultRepository;

    public BacktestResultQueryService(BacktestResultRepository backtestResultRepository) {
        this.backtestResultRepository = backtestResultRepository;
    }

    public List<BacktestResultResponse> getAllBacktestResults() {
        return backtestResultRepository.findAllByOrderByDateAsc()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<BacktestResultResponse> getBacktestResultsByDateRange(
            LocalDate startDate,
            LocalDate endDate
    ) {
        return backtestResultRepository.findByDateBetweenOrderByDateAsc(startDate, endDate)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private BacktestResultResponse toResponse(BacktestResult backtestResult) {
        return new BacktestResultResponse(
                backtestResult.getId(),
                backtestResult.getDate(),
                backtestResult.getPortfolioReturn(),
                backtestResult.getBenchmarkReturn(),
                backtestResult.getTurnover(),
                backtestResult.getPortfolioWealth(),
                backtestResult.getBenchmarkWealth()
        );
    }
}