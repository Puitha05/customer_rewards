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

}
