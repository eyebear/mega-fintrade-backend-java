package com.ao.portfolio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ao.portfolio.entity.ImportAudit;

@Repository
public interface ImportAuditRepository extends JpaRepository<ImportAudit, Long> {

    List<ImportAudit> findTop20ByOrderByStartedAtDesc();
}