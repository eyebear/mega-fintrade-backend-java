package com.ao.portfolio.service;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ao.portfolio.entity.StrategySignal;
import com.ao.portfolio.repository.StrategySignalRepository;

@Service
public class StrategySignalCsvImportService {

    private static final Path DEFAULT_FILE_PATH = Path.of("data/input/strategy_signals.csv");
    private static final String FILE_NAME = "strategy_signals.csv";

    private static final String EXPECTED_HEADER =
            "date,AAPL_close,AAPL_short_ma,AAPL_long_ma,AAPL_signal,"
                    + "MSFT_close,MSFT_short_ma,MSFT_long_ma,MSFT_signal,"
                    + "GOOGL_close,GOOGL_short_ma,GOOGL_long_ma,GOOGL_signal,"
                    + "SPY_close,SPY_short_ma,SPY_long_ma,SPY_signal";

    private static final int EXPECTED_COLUMN_COUNT = 17;

    private final StrategySignalRepository strategySignalRepository;
    private final CsvValidationService csvValidationService;

    public StrategySignalCsvImportService(
            StrategySignalRepository strategySignalRepository,
            CsvValidationService csvValidationService
    ) {
        this.strategySignalRepository = strategySignalRepository;
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

        List<StrategySignal> strategySignals = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();

            if (line.isEmpty()) {
                continue;
            }

            StrategySignal strategySignal = parseLine(line, i + 1);
            strategySignals.add(strategySignal);
        }

        strategySignalRepository.deleteAllInBatch();
        strategySignalRepository.saveAll(strategySignals);

        return strategySignals.size();
    }

    private StrategySignal parseLine(String line, int lineNumber) {
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

        BigDecimal aaplClose = csvValidationService.parseRequiredBigDecimal(columns[1], lineNumber, "AAPL_close", FILE_NAME);
        BigDecimal aaplShortMa = csvValidationService.parseRequiredBigDecimal(columns[2], lineNumber, "AAPL_short_ma", FILE_NAME);
        BigDecimal aaplLongMa = csvValidationService.parseRequiredBigDecimal(columns[3], lineNumber, "AAPL_long_ma", FILE_NAME);
        BigDecimal aaplSignal = csvValidationService.parseRequiredBigDecimal(columns[4], lineNumber, "AAPL_signal", FILE_NAME);

        BigDecimal msftClose = csvValidationService.parseRequiredBigDecimal(columns[5], lineNumber, "MSFT_close", FILE_NAME);
        BigDecimal msftShortMa = csvValidationService.parseRequiredBigDecimal(columns[6], lineNumber, "MSFT_short_ma", FILE_NAME);
        BigDecimal msftLongMa = csvValidationService.parseRequiredBigDecimal(columns[7], lineNumber, "MSFT_long_ma", FILE_NAME);
        BigDecimal msftSignal = csvValidationService.parseRequiredBigDecimal(columns[8], lineNumber, "MSFT_signal", FILE_NAME);

        BigDecimal googlClose = csvValidationService.parseRequiredBigDecimal(columns[9], lineNumber, "GOOGL_close", FILE_NAME);
        BigDecimal googlShortMa = csvValidationService.parseRequiredBigDecimal(columns[10], lineNumber, "GOOGL_short_ma", FILE_NAME);
        BigDecimal googlLongMa = csvValidationService.parseRequiredBigDecimal(columns[11], lineNumber, "GOOGL_long_ma", FILE_NAME);
        BigDecimal googlSignal = csvValidationService.parseRequiredBigDecimal(columns[12], lineNumber, "GOOGL_signal", FILE_NAME);

        BigDecimal spyClose = csvValidationService.parseRequiredBigDecimal(columns[13], lineNumber, "SPY_close", FILE_NAME);
        BigDecimal spyShortMa = csvValidationService.parseRequiredBigDecimal(columns[14], lineNumber, "SPY_short_ma", FILE_NAME);
        BigDecimal spyLongMa = csvValidationService.parseRequiredBigDecimal(columns[15], lineNumber, "SPY_long_ma", FILE_NAME);
        BigDecimal spySignal = csvValidationService.parseRequiredBigDecimal(columns[16], lineNumber, "SPY_signal", FILE_NAME);

        return new StrategySignal(
                date,
                aaplClose,
                aaplShortMa,
                aaplLongMa,
                aaplSignal,
                msftClose,
                msftShortMa,
                msftLongMa,
                msftSignal,
                googlClose,
                googlShortMa,
                googlLongMa,
                googlSignal,
                spyClose,
                spyShortMa,
                spyLongMa,
                spySignal
        );
    }
}