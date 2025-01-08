package com.assignment.rewards.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class CustomerTransactionsEntity.
 */
@Entity
@Table(name = "customer_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

}