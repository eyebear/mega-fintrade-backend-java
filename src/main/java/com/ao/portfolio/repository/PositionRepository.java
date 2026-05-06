package com.ao.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ao.portfolio.entity.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
}