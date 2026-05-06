package com.ao.portfolio.service;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ao.portfolio.entity.PortfolioEquityPoint;
import com.ao.portfolio.repository.PortfolioEquityPointRepository;

@Service
public class PortfolioEquityCsvImportService {

    private static final Path DEFAULT_FILE_PATH = Path.of("data/input/portfolio_equity_curve.csv");
    private static final String FILE_NAME = "portfolio_equity_curve.csv";
    private static final String EXPECTED_HEADER = "date,portfolio_wealth,benchmark_wealth";
    private static final int EXPECTED_COLUMN_COUNT = 3;

    private final PortfolioEquityPointRepository portfolioEquityPointRepository;
    private final CsvValidationService csvValidationService;

    public PortfolioEquityCsvImportService(
            PortfolioEquityPointRepository portfolioEquityPointRepository,
            CsvValidationService csvValidationService
    ) {
        this.portfolioEquityPointRepository = portfolioEquityPointRepository;
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

        List<PortfolioEquityPoint> portfolioEquityPoints = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();

            if (line.isEmpty()) {
                continue;
            }

            PortfolioEquityPoint portfolioEquityPoint = parseLine(line, i + 1);
            portfolioEquityPoints.add(portfolioEquityPoint);
        }

        portfolioEquityPointRepository.deleteAllInBatch();
        portfolioEquityPointRepository.saveAll(portfolioEquityPoints);

        return portfolioEquityPoints.size();
    }

    private PortfolioEquityPoint parseLine(String line, int lineNumber) {
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

        BigDecimal portfolioWealth = csvValidationService.parseRequiredBigDecimal(
                columns[1],
                lineNumber,
                "portfolio_wealth",
                FILE_NAME
        );

        BigDecimal benchmarkWealth = csvValidationService.parseRequiredBigDecimal(
                columns[2],
                lineNumber,
                "benchmark_wealth",
                FILE_NAME
        );

        return new PortfolioEquityPoint(
                date,
                portfolioWealth,
                benchmarkWealth
        );
    }
}