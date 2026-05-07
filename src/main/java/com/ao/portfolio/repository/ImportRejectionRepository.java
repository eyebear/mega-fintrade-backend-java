package com.ao.portfolio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ao.portfolio.entity.ImportRejection;

@Repository
public interface ImportRejectionRepository extends JpaRepository<ImportRejection, Long> {

    List<ImportRejection> findTop50ByOrderByCreatedAtDesc();
}