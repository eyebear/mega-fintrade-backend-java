package com.ao.portfolio.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ao.portfolio.dto.StrategySignalResponse;
import com.ao.portfolio.entity.StrategySignal;
import com.ao.portfolio.repository.StrategySignalRepository;

@Service
public class StrategySignalQueryService {

    private final StrategySignalRepository strategySignalRepository;

    public StrategySignalQueryService(StrategySignalRepository strategySignalRepository) {
        this.strategySignalRepository = strategySignalRepository;
    }

    public List<StrategySignalResponse> getAllStrategySignals() {
        return strategySignalRepository.findAllByOrderByDateAsc()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<StrategySignalResponse> getStrategySignalsByDateRange(
            LocalDate startDate,
            LocalDate endDate
    ) {
        return strategySignalRepository.findByDateBetweenOrderByDateAsc(startDate, endDate)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private StrategySignalResponse toResponse(StrategySignal strategySignal) {
        return new StrategySignalResponse(
                strategySignal.getId(),
                strategySignal.getDate(),
                strategySignal.getAaplClose(),
                strategySignal.getAaplShortMa(),
                strategySignal.getAaplLongMa(),
                strategySignal.getAaplSignal(),
                strategySignal.getMsftClose(),
                strategySignal.getMsftShortMa(),
                strategySignal.getMsftLongMa(),
                strategySignal.getMsftSignal(),
                strategySignal.getGooglClose(),
                strategySignal.getGooglShortMa(),
                strategySignal.getGooglLongMa(),
                strategySignal.getGooglSignal(),
                strategySignal.getSpyClose(),
                strategySignal.getSpyShortMa(),
                strategySignal.getSpyLongMa(),
                strategySignal.getSpySignal()
        );
    }
}