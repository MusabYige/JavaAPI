package com.example.api.model;
import java.time.LocalDate;

public class Transaction {


    private Long id;
    private Double amount;
    private String description;
    private LocalDate date;
    private Long userId;

    public Transaction(Long id, Double amount, String description, LocalDate date, Long userId) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.userId = userId;
    }

    //empty constructor
    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
