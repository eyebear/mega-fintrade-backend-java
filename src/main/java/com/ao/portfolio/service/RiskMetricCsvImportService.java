package com.ao.portfolio.service;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ao.portfolio.entity.RiskMetric;
import com.ao.portfolio.repository.RiskMetricRepository;

@Service
public class RiskMetricCsvImportService {

    private static final Path DEFAULT_FILE_PATH = Path.of("data/input/risk_metrics.csv");
    private static final String FILE_NAME = "risk_metrics.csv";
    private static final String EXPECTED_HEADER = "scope,symbol,metric,value";
    private static final int EXPECTED_COLUMN_COUNT = 4;

    private final RiskMetricRepository riskMetricRepository;
    private final CsvValidationService csvValidationService;

    public RiskMetricCsvImportService(
            RiskMetricRepository riskMetricRepository,
            CsvValidationService csvValidationService
    ) {
        this.riskMetricRepository = riskMetricRepository;
        this.csvValidationService = csvValidationService;
    }

    @Transactional
    public int importDefaultFile() {
        return importFile(DEFAULT_FILE_PATH);
    }

    @Transactional
    public int importFile(Path filePath) {
        List<String> lines = csvValidationService.readAndValidateFile(
                filePath,
                EXPECTED_HEADER,
                FILE_NAME
        );

        List<RiskMetric> riskMetrics = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();

            if (line.isEmpty()) {
                continue;
            }

            RiskMetric riskMetric = parseLine(line, i + 1);
            riskMetrics.add(riskMetric);
        }

        riskMetricRepository.deleteAllInBatch();
        riskMetricRepository.saveAll(riskMetrics);

        return riskMetrics.size();
    }

    private RiskMetric parseLine(String line, int lineNumber) {
        String[] columns = csvValidationService.splitAndValidateColumnCount(
                line,
                EXPECTED_COLUMN_COUNT,
                lineNumber,
                FILE_NAME
        );

        String scope = csvValidationService.parseRequiredText(
                columns[0],
                lineNumber,
                "scope",
                FILE_NAME
        );

        String symbol = csvValidationService.parseRequiredText(
                columns[1],
                lineNumber,
                "symbol",
                FILE_NAME
        );

        String metric = csvValidationService.parseRequiredText(
                columns[2],
                lineNumber,
                "metric",
                FILE_NAME
        );

        BigDecimal value = csvValidationService.parseRequiredBigDecimal(
                columns[3],
                lineNumber,
                "value",
                FILE_NAME
        );

        return new RiskMetric(scope, symbol, metric, value);
    }
}