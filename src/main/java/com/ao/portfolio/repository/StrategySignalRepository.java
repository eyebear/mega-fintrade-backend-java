package com.ao.portfolio.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ao.portfolio.entity.StrategySignal;

@Repository
public interface StrategySignalRepository extends JpaRepository<StrategySignal, Long> {

    List<StrategySignal> findByDateBetweenOrderByDateAsc(LocalDate startDate, LocalDate endDate);

    List<StrategySignal> findAllByOrderByDateAsc();
}