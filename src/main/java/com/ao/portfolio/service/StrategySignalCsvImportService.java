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

    private static final String EXPECTED_HEADER = "date,aapl_close,aapl_sma_short,aapl_sma_long,aapl_signal,"
            + "msft_close,msft_sma_short,msft_sma_long,msft_signal,"
            + "googl_close,googl_sma_short,googl_sma_long,googl_signal,"
            + "spy_close,spy_sma_short,spy_sma_long,spy_signal";

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

        BigDecimal aaplClose = csvValidationService.parseRequiredBigDecimal(columns[1], lineNumber, "aapl_close", FILE_NAME);
        BigDecimal aaplShortMa = csvValidationService.parseRequiredBigDecimal(columns[2], lineNumber, "aapl_sma_short", FILE_NAME);
        BigDecimal aaplLongMa = csvValidationService.parseRequiredBigDecimal(columns[3], lineNumber, "aapl_sma_long", FILE_NAME);
        BigDecimal aaplSignal = csvValidationService.parseRequiredBigDecimal(columns[4], lineNumber, "aapl_signal", FILE_NAME);

        BigDecimal msftClose = csvValidationService.parseRequiredBigDecimal(columns[5], lineNumber, "msft_close", FILE_NAME);
        BigDecimal msftShortMa = csvValidationService.parseRequiredBigDecimal(columns[6], lineNumber, "msft_sma_short", FILE_NAME);
        BigDecimal msftLongMa = csvValidationService.parseRequiredBigDecimal(columns[7], lineNumber, "msft_sma_long", FILE_NAME);
        BigDecimal msftSignal = csvValidationService.parseRequiredBigDecimal(columns[8], lineNumber, "msft_signal", FILE_NAME);

        BigDecimal googlClose = csvValidationService.parseRequiredBigDecimal(columns[9], lineNumber, "googl_close", FILE_NAME);
        BigDecimal googlShortMa = csvValidationService.parseRequiredBigDecimal(columns[10], lineNumber, "googl_sma_short", FILE_NAME);
        BigDecimal googlLongMa = csvValidationService.parseRequiredBigDecimal(columns[11], lineNumber, "googl_sma_long", FILE_NAME);
        BigDecimal googlSignal = csvValidationService.parseRequiredBigDecimal(columns[12], lineNumber, "googl_signal", FILE_NAME);

        BigDecimal spyClose = csvValidationService.parseRequiredBigDecimal(columns[13], lineNumber, "spy_close", FILE_NAME);
        BigDecimal spyShortMa = csvValidationService.parseRequiredBigDecimal(columns[14], lineNumber, "spy_sma_short", FILE_NAME);
        BigDecimal spyLongMa = csvValidationService.parseRequiredBigDecimal(columns[15], lineNumber, "spy_sma_long", FILE_NAME);
        BigDecimal spySignal = csvValidationService.parseRequiredBigDecimal(columns[16], lineNumber, "spy_signal", FILE_NAME);

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