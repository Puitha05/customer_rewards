package com.assignment.rewards.service;

import java.util.List;

import com.assignment.rewards.vo.CustomerInfoAndRewardsRS;
import com.assignment.rewards.vo.CustomerRewardsRequest;
import com.assignment.rewards.vo.CustomerRewardsResponse;
import com.assignment.rewards.vo.MonthlyTransactionRS;

public interface RewardsService {

	public List<CustomerRewardsResponse> getRewards(CustomerRewardsRequest customerRewardsRequest);

	public CustomerInfoAndRewardsRS getCustomerInfoAndRewards(Long customerId);
	
	public List<MonthlyTransactionRS> getTransactionsByMonth(int monthId);
}
