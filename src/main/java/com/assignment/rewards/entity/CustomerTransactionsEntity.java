package com.assignment.rewards.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * The Class CustomerTransactionsEntity.
 */
@Entity
@Table(name = "customer_transactions")
public class CustomerTransactionsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Month in which the transaction was done.
     */
    @Column(name = "transaction_month", nullable = false)
    private String month;

    /**
     * Transaction amount.
     */
    @Column(name = "transaction_amount", nullable = false)
    private double amount;

    // Constructors
    public CustomerTransactionsEntity() {
    }

    public CustomerTransactionsEntity(String month, double amount) {
        this.month = month;
        this.amount = amount;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CustomerTransaction{" +
                "id=" + id +
                ", month='" + month + '\'' +
                ", amount=" + amount +
                '}';
    }
}