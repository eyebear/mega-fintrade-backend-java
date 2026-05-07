package com.ao.portfolio.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ao.portfolio.dto.ImportAuditResponse;
import com.ao.portfolio.service.ImportAuditService;

@RestController
@RequestMapping("/api/import")
public class ImportAuditController {

    private final ImportAuditService importAuditService;

    public ImportAuditController(ImportAuditService importAuditService) {
        this.importAuditService = importAuditService;
    }

    @GetMapping("/audit")
    public List<ImportAuditResponse> getRecentImportAudits() {
        return importAuditService.getRecentAudits()
                .stream()
                .map(ImportAuditResponse::new)
                .toList();
    }
}