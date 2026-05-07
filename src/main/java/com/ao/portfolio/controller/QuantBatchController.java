package com.ao.portfolio.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ao.portfolio.dto.BatchRunSummary;
import com.ao.portfolio.service.QuantBatchRunService;

@RestController
@RequestMapping("/api/batch")
public class QuantBatchController {

    private final QuantBatchRunService quantBatchRunService;

    public QuantBatchController(QuantBatchRunService quantBatchRunService) {
        this.quantBatchRunService = quantBatchRunService;
    }

    @PostMapping("/run")
    public BatchRunSummary runQuantImportBatchJobs() {
        return quantBatchRunService.runAllQuantImportJobs();
    }
}