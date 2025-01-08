package com.assignment.rewards.vo;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a response for customer rewards including transaction details,
 * monthly points breakdown, and total reward points.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRewardsResponse {

	/**
	 * Unique customer ID.
	 */
	private Integer customerId;

	/**
	 * List of customer transactions.
	 */
	private List<CustomerTransactionRS> customerTransactions;

	/**
	 * Mapping of month to reward points.
	 */
	private Map<String, Integer> monthlyPoints;

	/**
	 * Total reward points accumulated by the customer.
	 */
	private int totalPoints;
}
