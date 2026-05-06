package com.ao.portfolio.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "positions")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Symbol is required")
    @Size(max = 20, message = "Symbol must not exceed 20 characters")
    @Column(nullable = false, length = 20)
    private String symbol;

    @Positive(message = "Quantity must be greater than zero")
    @Column(nullable = false)
    private int quantity;

    @Positive(message = "Average price must be greater than zero")
    @Column(name = "avg_price", nullable = false, precision = 19, scale = 4)
    private BigDecimal avgPrice;

    public Position() {
    }

    public Position(String symbol, int quantity, BigDecimal avgPrice) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.avgPrice = avgPrice;
    }

    public Long getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }
}