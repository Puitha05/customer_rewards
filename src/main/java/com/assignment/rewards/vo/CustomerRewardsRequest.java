package com.assignment.rewards.vo;

import java.util.List;

public class CustomerRewardsRequest {

	/**
	 * customerTransactions
	 */
	List<CustomerTransaction> customerTransactions;
	/**
	 * months
	 */
	Integer months;

	public CustomerRewardsRequest(List<CustomerTransaction> customerTransactions, Integer months) {
		super();
		this.customerTransactions = customerTransactions;
		this.months = months;
	}

	public CustomerRewardsRequest() {

	}

	/**
	 * @return the customerTransactions
	 */
	public List<CustomerTransaction> getCustomerTransactions() {
		return customerTransactions;
	}

	/**
	 * @param customerTransactions the customerTransactions to set
	 */
	public void setCustomerTransactions(List<CustomerTransaction> customerTransactions) {
		this.customerTransactions = customerTransactions;
	}

	/**
	 * @return the months
	 */
	public Integer getMonths() {
		return months;
	}

	/**
	 * @param months the months to set
	 */
	public void setMonths(Integer months) {
		this.months = months;
	}

}
