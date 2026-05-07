package com.ao.portfolio.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ao.portfolio.dto.ImportRejectionResponse;
import com.ao.portfolio.service.ImportRejectionService;

@RestController
@RequestMapping("/api/import")
public class ImportRejectionController {

    private final ImportRejectionService importRejectionService;

    public ImportRejectionController(ImportRejectionService importRejectionService) {
        this.importRejectionService = importRejectionService;
    }

    @GetMapping("/rejections")
    public List<ImportRejectionResponse> getRecentImportRejections() {
        return importRejectionService.getRecentRejections()
                .stream()
                .map(ImportRejectionResponse::new)
                .toList();
    }
}