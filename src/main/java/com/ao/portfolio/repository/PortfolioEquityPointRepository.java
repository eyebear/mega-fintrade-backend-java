package com.ao.portfolio.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ao.portfolio.entity.PortfolioEquityPoint;

@Repository
public interface PortfolioEquityPointRepository extends JpaRepository<PortfolioEquityPoint, Long> {

    List<PortfolioEquityPoint> findByDateBetweenOrderByDateAsc(LocalDate startDate, LocalDate endDate);

    List<PortfolioEquityPoint> findAllByOrderByDateAsc();
}