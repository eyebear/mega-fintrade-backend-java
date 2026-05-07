package com.ao.portfolio.service;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CsvValidationService {

    private final ImportRejectionService importRejectionService;

    public CsvValidationService(ImportRejectionService importRejectionService) {
        this.importRejectionService = importRejectionService;
    }

    public List<String> readAndValidateFile(Path filePath, String expectedHeader, String fileName) {
        try {
            if (!Files.exists(filePath)) {
                String reason = "CSV file not found: " + filePath;
                importRejectionService.logRejection(fileName, 0, "", reason);
                throw new IllegalArgumentException(reason);
            }

            List<String> lines = Files.readAllLines(filePath);

            if (lines.isEmpty()) {
                String reason = "CSV file is empty: " + fileName;
                importRejectionService.logRejection(fileName, 0, "", reason);
                throw new IllegalArgumentException(reason);
            }

            String actualHeader = lines.get(0).trim();

            if (!expectedHeader.equals(actualHeader)) {
                String reason = "Invalid CSV header. Expected: " + expectedHeader + ", actual: " + actualHeader;
                importRejectionService.logRejection(fileName, 1, lines.get(0), reason);
                throw new IllegalArgumentException(reason);
            }

            return lines;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            String reason = "Failed to read CSV file: " + e.getMessage();
            importRejectionService.logRejection(fileName, 0, "", reason);
            throw new IllegalArgumentException(reason, e);
        }
    }

    public String[] splitAndValidateColumnCount(String line, int expectedColumnCount, int lineNumber, String fileName) {
        String[] columns = line.split(",", -1);

        if (columns.length != expectedColumnCount) {
            String reason = "Invalid column count. Expected: " + expectedColumnCount + ", actual: " + columns.length;
            importRejectionService.logRejection(fileName, lineNumber, line, reason);
            throw new IllegalArgumentException(reason);
        }

        return columns;
    }

    public String parseRequiredText(String value, int lineNumber, String fieldName, String fileName) {
        if (value == null || value.trim().isEmpty()) {
            String reason = "Missing required field: " + fieldName;
            importRejectionService.logRejection(fileName, lineNumber, value, reason);
            throw new IllegalArgumentException(reason);
        }

        return value.trim();
    }

    public BigDecimal parseRequiredBigDecimal(String value, int lineNumber, String fieldName, String fileName) {
        if (value == null || value.trim().isEmpty()) {
            String reason = "Missing required decimal field: " + fieldName;
            importRejectionService.logRejection(fileName, lineNumber, value, reason);
            throw new IllegalArgumentException(reason);
        }

        try {
            return new BigDecimal(value.trim());
        } catch (NumberFormatException e) {
            String reason = "Invalid decimal field: " + fieldName + ", value: " + value;
            importRejectionService.logRejection(fileName, lineNumber, value, reason);
            throw new IllegalArgumentException(reason);
        }
    }

    public LocalDate parseRequiredDate(String value, int lineNumber, String fieldName, String fileName) {
        if (value == null || value.trim().isEmpty()) {
            String reason = "Missing required date field: " + fieldName;
            importRejectionService.logRejection(fileName, lineNumber, value, reason);
            throw new IllegalArgumentException(reason);
        }

        try {
            return LocalDate.parse(value.trim());
        } catch (Exception e) {
            String reason = "Invalid date field: " + fieldName + ", value: " + value;
            importRejectionService.logRejection(fileName, lineNumber, value, reason);
            throw new IllegalArgumentException(reason);
        }
    }
}
