package com.assignment.rewards.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request for calculating customer rewards.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRewardsRequest {

    /**
     * List of customer transactions.
     */
    private List<CustomerTransactionRQ> customerTransactions;

    /**
     * Number of months to consider for rewards calculation.
     */
    private Integer months;
}
