package com.assignment.rewards.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInfoAndRewardsRS {
	
	/**
     * Unique customer ID.
     */
    private int customerId;

    /**
     * Customer name.
     */
    private String customerName;
    /**
     * List of customer transactions.
     */
    private List<CustomerTransactionRS> customerTransactions;
    /**
     * Total reward points accumulated by the customer.
     */
    private int totalRewardPoints;

}
