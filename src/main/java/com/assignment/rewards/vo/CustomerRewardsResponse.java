package com.assignment.rewards.vo;

import java.util.List;
import java.util.Map;

public class CustomerRewardsResponse {
	private Integer customerId;
	private List<CustomerTransaction> customerTransactions;
	private Map<String, Integer> monthlyPoints;
	private int totalPoints;

	public CustomerRewardsResponse(Integer customerId, List<CustomerTransaction> customerTransactions,
			Map<String, Integer> monthlyPoints, int totalPoints) {
		super();
		this.customerId = customerId;
		this.customerTransactions = customerTransactions;
		this.monthlyPoints = monthlyPoints;
		this.totalPoints = totalPoints;
	}

	/**
	 * @return the customerId
	 */
	public Integer getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
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
	 * @return the monthlyPoints
	 */
	public Map<String, Integer> getMonthlyPoints() {
		return monthlyPoints;
	}

	/**
	 * @param monthlyPoints the monthlyPoints to set
	 */
	public void setMonthlyPoints(Map<String, Integer> monthlyPoints) {
		this.monthlyPoints = monthlyPoints;
	}

	/**
	 * @return the totalPoints
	 */
	public int getTotalPoints() {
		return totalPoints;
	}

	/**
	 * @param totalPoints the totalPoints to set
	 */
	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}

}
