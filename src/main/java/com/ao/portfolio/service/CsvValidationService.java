package com.ao.portfolio.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CsvValidationService {

    public List<String> readAndValidateFile(Path filePath, String expectedHeader, String fileName) {
        validateFileExists(filePath, fileName);

        List<String> lines = readLines(filePath, fileName);

        if (lines.isEmpty()) {
            throw new IllegalArgumentException("CSV file is empty: " + filePath);
        }

        validateHeader(lines.get(0), expectedHeader, fileName);

        return lines;
    }

    public void validateFileExists(Path filePath, String fileName) {
        if (!Files.exists(filePath)) {
            throw new IllegalArgumentException(fileName + " does not exist: " + filePath);
        }

        if (!Files.isRegularFile(filePath)) {
            throw new IllegalArgumentException(fileName + " path is not a regular file: " + filePath);
        }
    }

    public List<String> readLines(Path filePath, String fileName) {
        try {
            return Files.readAllLines(filePath);
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to read " + fileName + ": " + filePath, exception);
        }
    }

    public void validateHeader(String actualHeader, String expectedHeader, String fileName) {
        String normalizedActualHeader = actualHeader.trim();

        if (!expectedHeader.equals(normalizedActualHeader)) {
            throw new IllegalArgumentException(
                    "Invalid header in "
                            + fileName
                            + ". Expected: "
                            + expectedHeader
                            + ", but found: "
                            + normalizedActualHeader
            );
        }
    }

    public String[] splitAndValidateColumnCount(
            String line,
            int expectedColumnCount,
            int lineNumber,
            String fileName
    ) {
        String[] columns = line.split(",", -1);

        if (columns.length != expectedColumnCount) {
            throw new IllegalArgumentException(
                    "Invalid column count in "
                            + fileName
                            + " at line "
                            + lineNumber
                            + ". Expected "
                            + expectedColumnCount
                            + " columns but found "
                            + columns.length
            );
        }

        return columns;
    }

    public String parseRequiredText(
            String value,
            int lineNumber,
            String columnName,
            String fileName
    ) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    "Missing required value in "
                            + fileName
                            + " at line "
                            + lineNumber
                            + ", column: "
                            + columnName
            );
        }

        return value.trim();
    }

    public LocalDate parseRequiredDate(
            String value,
            int lineNumber,
            String columnName,
            String fileName
    ) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    "Missing date value in "
                            + fileName
                            + " at line "
                            + lineNumber
                            + ", column: "
                            + columnName
            );
        }

        try {
            return LocalDate.parse(value.trim());
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(
                    "Invalid date value in "
                            + fileName
                            + " at line "
                            + lineNumber
                            + ", column: "
                            + columnName
                            + ", value: "
                            + value
                            + ". Expected format: YYYY-MM-DD"
            );
        }
    }

    public BigDecimal parseRequiredBigDecimal(
            String value,
            int lineNumber,
            String columnName,
            String fileName
    ) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    "Missing numeric value in "
                            + fileName
                            + " at line "
                            + lineNumber
                            + ", column: "
                            + columnName
            );
        }

        try {
            return new BigDecimal(value.trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(
                    "Invalid decimal value in "
                            + fileName
                            + " at line "
                            + lineNumber
                            + ", column: "
                            + columnName
                            + ", value: "
                            + value
            );
        }
    }
}