package com.assignment.rewards.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a customer transaction with details such as customer ID, name,
 * transaction month, and amount.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTransactionRS {

	/**
	 * Month in which the transaction was done.
	 */
	private String month;

	/**
	 * Transaction amount.
	 */
	private double amount;
}
