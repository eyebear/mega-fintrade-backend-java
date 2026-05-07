package com.ao.portfolio.dto;

import java.time.LocalDateTime;

import com.ao.portfolio.entity.ImportRejection;

public class ImportRejectionResponse {

    private Long id;
    private String fileName;
    private int lineNumber;
    private String rawRecord;
    private String reason;
    private LocalDateTime createdAt;

    public ImportRejectionResponse() {
    }

    public ImportRejectionResponse(ImportRejection rejection) {
        this.id = rejection.getId();
        this.fileName = rejection.getFileName();
        this.lineNumber = rejection.getLineNumber();
        this.rawRecord = rejection.getRawRecord();
        this.reason = rejection.getReason();
        this.createdAt = rejection.getCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getRawRecord() {
        return rawRecord;
    }

    public String getReason() {
        return reason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}