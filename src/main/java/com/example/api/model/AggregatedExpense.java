package com.example.api.model;

import java.time.LocalDateTime;

public class AggregatedExpense {
    private Long id;
    private Long userId;
    private String period;
    private Double totalAmount;
    private LocalDateTime createdAt;

    // Constructors, getters and setters
    public AggregatedExpense() {}

    public AggregatedExpense(Long userId, String period, Double totalAmount) {
        this.userId = userId;
        this.period = period;
        this.totalAmount = totalAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
