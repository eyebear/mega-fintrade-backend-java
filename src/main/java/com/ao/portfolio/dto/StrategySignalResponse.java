package com.ao.portfolio.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class StrategySignalResponse {

    private Long id;
    private LocalDate date;

    private BigDecimal aaplClose;
    private BigDecimal aaplShortMa;
    private BigDecimal aaplLongMa;
    private BigDecimal aaplSignal;

    private BigDecimal msftClose;
    private BigDecimal msftShortMa;
    private BigDecimal msftLongMa;
    private BigDecimal msftSignal;

    private BigDecimal googlClose;
    private BigDecimal googlShortMa;
    private BigDecimal googlLongMa;
    private BigDecimal googlSignal;

    private BigDecimal spyClose;
    private BigDecimal spyShortMa;
    private BigDecimal spyLongMa;
    private BigDecimal spySignal;

    public StrategySignalResponse() {
    }

    public StrategySignalResponse(
            Long id,
            LocalDate date,
            BigDecimal aaplClose,
            BigDecimal aaplShortMa,
            BigDecimal aaplLongMa,
            BigDecimal aaplSignal,
            BigDecimal msftClose,
            BigDecimal msftShortMa,
            BigDecimal msftLongMa,
            BigDecimal msftSignal,
            BigDecimal googlClose,
            BigDecimal googlShortMa,
            BigDecimal googlLongMa,
            BigDecimal googlSignal,
            BigDecimal spyClose,
            BigDecimal spyShortMa,
            BigDecimal spyLongMa,
            BigDecimal spySignal
    ) {
        this.id = id;
        this.date = date;
        this.aaplClose = aaplClose;
        this.aaplShortMa = aaplShortMa;
        this.aaplLongMa = aaplLongMa;
        this.aaplSignal = aaplSignal;
        this.msftClose = msftClose;
        this.msftShortMa = msftShortMa;
        this.msftLongMa = msftLongMa;
        this.msftSignal = msftSignal;
        this.googlClose = googlClose;
        this.googlShortMa = googlShortMa;
        this.googlLongMa = googlLongMa;
        this.googlSignal = googlSignal;
        this.spyClose = spyClose;
        this.spyShortMa = spyShortMa;
        this.spyLongMa = spyLongMa;
        this.spySignal = spySignal;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getAaplClose() {
        return aaplClose;
    }

    public BigDecimal getAaplShortMa() {
        return aaplShortMa;
    }

    public BigDecimal getAaplLongMa() {
        return aaplLongMa;
    }

    public BigDecimal getAaplSignal() {
        return aaplSignal;
    }

    public BigDecimal getMsftClose() {
        return msftClose;
    }

    public BigDecimal getMsftShortMa() {
        return msftShortMa;
    }

    public BigDecimal getMsftLongMa() {
        return msftLongMa;
    }

    public BigDecimal getMsftSignal() {
        return msftSignal;
    }

    public BigDecimal getGooglClose() {
        return googlClose;
    }

    public BigDecimal getGooglShortMa() {
        return googlShortMa;
    }

    public BigDecimal getGooglLongMa() {
        return googlLongMa;
    }

    public BigDecimal getGooglSignal() {
        return googlSignal;
    }

    public BigDecimal getSpyClose() {
        return spyClose;
    }

    public BigDecimal getSpyShortMa() {
        return spyShortMa;
    }

    public BigDecimal getSpyLongMa() {
        return spyLongMa;
    }

    public BigDecimal getSpySignal() {
        return spySignal;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setAaplClose(BigDecimal aaplClose) {
        this.aaplClose = aaplClose;
    }

    public void setAaplShortMa(BigDecimal aaplShortMa) {
        this.aaplShortMa = aaplShortMa;
    }

    public void setAaplLongMa(BigDecimal aaplLongMa) {
        this.aaplLongMa = aaplLongMa;
    }

    public void setAaplSignal(BigDecimal aaplSignal) {
        this.aaplSignal = aaplSignal;
    }

    public void setMsftClose(BigDecimal msftClose) {
        this.msftClose = msftClose;
    }

    public void setMsftShortMa(BigDecimal msftShortMa) {
        this.msftShortMa = msftShortMa;
    }

    public void setMsftLongMa(BigDecimal msftLongMa) {
        this.msftLongMa = msftLongMa;
    }

    public void setMsftSignal(BigDecimal msftSignal) {
        this.msftSignal = msftSignal;
    }

    public void setGooglClose(BigDecimal googlClose) {
        this.googlClose = googlClose;
    }

    public void setGooglShortMa(BigDecimal googlShortMa) {
        this.googlShortMa = googlShortMa;
    }

    public void setGooglLongMa(BigDecimal googlLongMa) {
        this.googlLongMa = googlLongMa;
    }

    public void setGooglSignal(BigDecimal googlSignal) {
        this.googlSignal = googlSignal;
    }

    public void setSpyClose(BigDecimal spyClose) {
        this.spyClose = spyClose;
    }

    public void setSpyShortMa(BigDecimal spyShortMa) {
        this.spyShortMa = spyShortMa;
    }

    public void setSpyLongMa(BigDecimal spyLongMa) {
        this.spyLongMa = spyLongMa;
    }

    public void setSpySignal(BigDecimal spySignal) {
        this.spySignal = spySignal;
    }
}