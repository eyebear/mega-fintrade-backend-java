package com.ao.portfolio.service;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ao.portfolio.entity.BacktestResult;
import com.ao.portfolio.repository.BacktestResultRepository;

@Service
public class BacktestResultCsvImportService {

    private static final Path DEFAULT_FILE_PATH = Path.of("data/input/backtest_results.csv");
    private static final String FILE_NAME = "backtest_results.csv";
    private static final String EXPECTED_HEADER =
            "date,portfolio_return,benchmark_return,turnover,portfolio_wealth,benchmark_wealth";
    private static final int EXPECTED_COLUMN_COUNT = 6;

    private final BacktestResultRepository backtestResultRepository;
    private final CsvValidationService csvValidationService;

    public BacktestResultCsvImportService(
            BacktestResultRepository backtestResultRepository,
            CsvValidationService csvValidationService
    ) {
        this.backtestResultRepository = backtestResultRepository;
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

        List<BacktestResult> backtestResults = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();

            if (line.isEmpty()) {
                continue;
            }

            BacktestResult backtestResult = parseLine(line, i + 1);
            backtestResults.add(backtestResult);
        }

        backtestResultRepository.deleteAllInBatch();
        backtestResultRepository.saveAll(backtestResults);

        return backtestResults.size();
    }

    private BacktestResult parseLine(String line, int lineNumber) {
        String[] columns = csvValidationService.splitAndValidateColumnCount(
                line,
                EXPECTED_COLUMN_COUNT,
                lineNumber,
                FILE_NAME
        );

        LocalDate date = csvValidationService.parseRequiredDate(
                columns[0],
                lineNumber,
                "date",
                FILE_NAME
        );

        BigDecimal portfolioReturn = csvValidationService.parseRequiredBigDecimal(
                columns[1],
                lineNumber,
                "portfolio_return",
                FILE_NAME
        );

        BigDecimal benchmarkReturn = csvValidationService.parseRequiredBigDecimal(
                columns[2],
                lineNumber,
                "benchmark_return",
                FILE_NAME
        );

        BigDecimal turnover = csvValidationService.parseRequiredBigDecimal(
                columns[3],
                lineNumber,
                "turnover",
                FILE_NAME
        );

        BigDecimal portfolioWealth = csvValidationService.parseRequiredBigDecimal(
                columns[4],
                lineNumber,
                "portfolio_wealth",
                FILE_NAME
        );

        BigDecimal benchmarkWealth = csvValidationService.parseRequiredBigDecimal(
                columns[5],
                lineNumber,
                "benchmark_wealth",
                FILE_NAME
        );

        return new BacktestResult(
                date,
                portfolioReturn,
                benchmarkReturn,
                turnover,
                portfolioWealth,
                benchmarkWealth
        );
    }
}