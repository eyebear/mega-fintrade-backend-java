package com.ao.portfolio.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "strategy_signals")
public class StrategySignal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Date is required")
    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "aapl_close", precision = 19, scale = 6)
    private BigDecimal aaplClose;

    @Column(name = "aapl_short_ma", precision = 19, scale = 6)
    private BigDecimal aaplShortMa;

    @Column(name = "aapl_long_ma", precision = 19, scale = 6)
    private BigDecimal aaplLongMa;

    @Column(name = "aapl_signal", precision = 19, scale = 6)
    private BigDecimal aaplSignal;

    @Column(name = "msft_close", precision = 19, scale = 6)
    private BigDecimal msftClose;

    @Column(name = "msft_short_ma", precision = 19, scale = 6)
    private BigDecimal msftShortMa;

    @Column(name = "msft_long_ma", precision = 19, scale = 6)
    private BigDecimal msftLongMa;

    @Column(name = "msft_signal", precision = 19, scale = 6)
    private BigDecimal msftSignal;

    @Column(name = "googl_close", precision = 19, scale = 6)
    private BigDecimal googlClose;

    @Column(name = "googl_short_ma", precision = 19, scale = 6)
    private BigDecimal googlShortMa;

    @Column(name = "googl_long_ma", precision = 19, scale = 6)
    private BigDecimal googlLongMa;

    @Column(name = "googl_signal", precision = 19, scale = 6)
    private BigDecimal googlSignal;

    @Column(name = "spy_close", precision = 19, scale = 6)
    private BigDecimal spyClose;

    @Column(name = "spy_short_ma", precision = 19, scale = 6)
    private BigDecimal spyShortMa;

    @Column(name = "spy_long_ma", precision = 19, scale = 6)
    private BigDecimal spyLongMa;

    @Column(name = "spy_signal", precision = 19, scale = 6)
    private BigDecimal spySignal;

    public StrategySignal() {
    }

    public StrategySignal(
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

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAaplClose() {
        return aaplClose;
    }

    public void setAaplClose(BigDecimal aaplClose) {
        this.aaplClose = aaplClose;
    }

    public BigDecimal getAaplShortMa() {
        return aaplShortMa;
    }

    public void setAaplShortMa(BigDecimal aaplShortMa) {
        this.aaplShortMa = aaplShortMa;
    }

    public BigDecimal getAaplLongMa() {
        return aaplLongMa;
    }

    public void setAaplLongMa(BigDecimal aaplLongMa) {
        this.aaplLongMa = aaplLongMa;
    }

    public BigDecimal getAaplSignal() {
        return aaplSignal;
    }

    public void setAaplSignal(BigDecimal aaplSignal) {
        this.aaplSignal = aaplSignal;
    }

    public BigDecimal getMsftClose() {
        return msftClose;
    }

    public void setMsftClose(BigDecimal msftClose) {
        this.msftClose = msftClose;
    }

    public BigDecimal getMsftShortMa() {
        return msftShortMa;
    }

    public void setMsftShortMa(BigDecimal msftShortMa) {
        this.msftShortMa = msftShortMa;
    }

    public BigDecimal getMsftLongMa() {
        return msftLongMa;
    }

    public void setMsftLongMa(BigDecimal msftLongMa) {
        this.msftLongMa = msftLongMa;
    }

    public BigDecimal getMsftSignal() {
        return msftSignal;
    }

    public void setMsftSignal(BigDecimal msftSignal) {
        this.msftSignal = msftSignal;
    }

    public BigDecimal getGooglClose() {
        return googlClose;
    }

    public void setGooglClose(BigDecimal googlClose) {
        this.googlClose = googlClose;
    }

    public BigDecimal getGooglShortMa() {
        return googlShortMa;
    }

    public void setGooglShortMa(BigDecimal googlShortMa) {
        this.googlShortMa = googlShortMa;
    }

    public BigDecimal getGooglLongMa() {
        return googlLongMa;
    }

    public void setGooglLongMa(BigDecimal googlLongMa) {
        this.googlLongMa = googlLongMa;
    }

    public BigDecimal getGooglSignal() {
        return googlSignal;
    }

    public void setGooglSignal(BigDecimal googlSignal) {
        this.googlSignal = googlSignal;
    }

    public BigDecimal getSpyClose() {
        return spyClose;
    }

    public void setSpyClose(BigDecimal spyClose) {
        this.spyClose = spyClose;
    }

    public BigDecimal getSpyShortMa() {
        return spyShortMa;
    }

    public void setSpyShortMa(BigDecimal spyShortMa) {
        this.spyShortMa = spyShortMa;
    }

    public BigDecimal getSpyLongMa() {
        return spyLongMa;
    }

    public void setSpyLongMa(BigDecimal spyLongMa) {
        this.spyLongMa = spyLongMa;
    }

    public BigDecimal getSpySignal() {
        return spySignal;
    }

    public void setSpySignal(BigDecimal spySignal) {
        this.spySignal = spySignal;
    }
}