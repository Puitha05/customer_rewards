package com.assignment.rewards.service;

import java.time.YearMonth;
import java.util.List;

import com.assignment.rewards.vo.CustomerInfoAndRewardsRS;
import com.assignment.rewards.vo.CustomerRewardsRequest;
import com.assignment.rewards.vo.CustomerRewardsResponse;

public interface RewardsService {

	/**
	 * @param customerRewardsRequest
	 * @return
	 */
	public List<CustomerRewardsResponse> getRewards(CustomerRewardsRequest customerRewardsRequest);

	/**
	 * Returns the specific customer transaction which falls under specific start
	 * and endMonth
	 * @param customerId
	 * @param startMonth
	 * @param endMonth
	 */
	CustomerInfoAndRewardsRS getCustomerTransactionsAndRewards(Long customerId, YearMonth startMonth,
			YearMonth endMonth);
}
