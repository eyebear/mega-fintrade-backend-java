package com.ao.portfolio.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ao.portfolio.dto.PortfolioEquityPointResponse;
import com.ao.portfolio.entity.PortfolioEquityPoint;
import com.ao.portfolio.repository.PortfolioEquityPointRepository;

@Service
public class PortfolioEquityQueryService {

    private final PortfolioEquityPointRepository portfolioEquityPointRepository;

    public PortfolioEquityQueryService(
            PortfolioEquityPointRepository portfolioEquityPointRepository
    ) {
        this.portfolioEquityPointRepository = portfolioEquityPointRepository;
    }

    public List<PortfolioEquityPointResponse> getAllPortfolioEquityPoints() {
        return portfolioEquityPointRepository.findAllByOrderByDateAsc()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<PortfolioEquityPointResponse> getPortfolioEquityPointsByDateRange(
            LocalDate startDate,
            LocalDate endDate
    ) {
        return portfolioEquityPointRepository.findByDateBetweenOrderByDateAsc(startDate, endDate)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private PortfolioEquityPointResponse toResponse(PortfolioEquityPoint portfolioEquityPoint) {
        return new PortfolioEquityPointResponse(
                portfolioEquityPoint.getId(),
                portfolioEquityPoint.getDate(),
                portfolioEquityPoint.getPortfolioWealth(),
                portfolioEquityPoint.getBenchmarkWealth()
        );
    }
}