package com.assignment.rewards.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyTransactionRS {
	/**
     * Unique customer ID.
     */
    private Integer customerId;
    
    List<CustomerTransactionRS> customerTransactions;
//    /**
//     * Month in which the transaction was done.
//     */
//    private String month;
//
//    /**
//     * Transaction amount.
//     */
//    private double amount;

}
