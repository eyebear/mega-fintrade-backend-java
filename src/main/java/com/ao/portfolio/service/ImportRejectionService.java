package com.ao.portfolio.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ao.portfolio.entity.ImportRejection;
import com.ao.portfolio.repository.ImportRejectionRepository;

@Service
public class ImportRejectionService {

    private final ImportRejectionRepository importRejectionRepository;

    public ImportRejectionService(ImportRejectionRepository importRejectionRepository) {
        this.importRejectionRepository = importRejectionRepository;
    }

    public ImportRejection logRejection(String fileName, int lineNumber, String rawRecord, String reason) {
        ImportRejection rejection = new ImportRejection(
                fileName,
                lineNumber,
                rawRecord,
                reason,
                LocalDateTime.now()
        );

        return importRejectionRepository.save(rejection);
    }

    public List<ImportRejection> getRecentRejections() {
        return importRejectionRepository.findTop50ByOrderByCreatedAtDesc();
    }
}