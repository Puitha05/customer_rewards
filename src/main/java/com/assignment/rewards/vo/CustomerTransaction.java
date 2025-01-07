package com.assignment.rewards.vo;

public class CustomerTransaction {

	/**
	 * customerId --unique customer Id
	 */
	private int customerId;
	/**
	 * customerName
	 */
	private String customerName;
	/**
	 * month- month in which transaction is done
	 */
	private String month;
	/**
	 * amount
	 */
	private double amount;

	public CustomerTransaction(int customerId, String customerName, String month, double amount) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.month = month;
		this.amount = amount;
	}

	public CustomerTransaction() {

	}

	/**
	 * @return the customerId
	 */
	public int getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerName=" + customerName + ", month=" + month
				+ ", amount=" + amount + "]";
	}

}
