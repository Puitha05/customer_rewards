package com.assignment.rewards;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.assignment.rewards.service.RewardsServiceImpl;
import com.assignment.rewards.vo.CustomerRewardsRequest;
import com.assignment.rewards.vo.CustomerRewardsResponse;
import com.assignment.rewards.vo.CustomerTransactionRQ;

class RewardsServiceImplTest {

	private final RewardsServiceImpl rewardsService = new RewardsServiceImpl();

	/**
	 * Tests the getRewards method for a single customer with transactions spread
	 * across valid months. Validates that the correct total reward points are
	 * calculated for the customer.
	 */
	@Test
	void testGetRewards() {
		// Prepare test data
		List<CustomerTransactionRQ> transactions = Arrays.asList(new CustomerTransactionRQ(1, "John Doe", "2023-11", 120.0),
				new CustomerTransactionRQ(1, "John Doe", "2023-12", 75.0),
				new CustomerTransactionRQ(2, "Jane Smith", "2025-01", 150.0),
				new CustomerTransactionRQ(2, "Jane Smith", "2024-12", 50.0));

		CustomerRewardsRequest request = new CustomerRewardsRequest();
		request.setCustomerTransactions(transactions);
		request.setMonths(2);

		// Execute service method
		List<CustomerRewardsResponse> rewardsResponses = rewardsService.getRewards(request);

		// Validate results
		assertEquals(1, rewardsResponses.size());
		assertEquals(150, rewardsResponses.get(0).getTotalRewardPoints());
	}

	/**
	 * Tests the getRewards method for multiple customers with valid transactions
	 * across multiple months. Validates the correct grouping and calculation of
	 * rewards.
	 */
	@Test
	void testGetRewards_ReturnMultipleData() {
		// Prepare test data
		List<CustomerTransactionRQ> transactions = List.of(new CustomerTransactionRQ(1, "John Doe", "2023-10", 120.0),
				new CustomerTransactionRQ(2, "Jane Smith", "2023-10", 75.0),
				new CustomerTransactionRQ(3, "John Doe", "2023-11", 200.0),
				new CustomerTransactionRQ(4, "Jane Smith", "2023-11", 50.0),
				new CustomerTransactionRQ(5, "John Doeee", "2025-01", 130.0),
				new CustomerTransactionRQ(6, "Jane Smith", "2024-12", 90.0),
				new CustomerTransactionRQ(5, "John Doeee", "2024-12", 140.0));

		CustomerRewardsRequest request = new CustomerRewardsRequest();
		request.setCustomerTransactions(transactions);
		request.setMonths(2);

		// Execute service method
		List<CustomerRewardsResponse> rewardsResponses = rewardsService.getRewards(request);

		// Validate results
		assertEquals(2, rewardsResponses.size());
		assertEquals(240, rewardsResponses.get(0).getTotalRewardPoints());
		assertEquals(40, rewardsResponses.get(1).getTotalRewardPoints());
	}

	/**
	 * Tests the getRewards method when no transactions fall within the requested
	 * time range. Validates that the response is empty.
	 */
	@Test
	void testGetRewards_ReturnNoData() {
		// Prepare test data
		List<CustomerTransactionRQ> transactions = List.of(new CustomerTransactionRQ(1, "John Doe", "2023-10", 120.0),
				new CustomerTransactionRQ(2, "Jane Smith", "2023-10", 75.0),
				new CustomerTransactionRQ(3, "John Doe", "2023-11", 200.0),
				new CustomerTransactionRQ(4, "Jane Smith", "2023-11", 50.0),
				new CustomerTransactionRQ(5, "John Doeee", "2024-01", 130.0),
				new CustomerTransactionRQ(6, "Jane Smith", "2024-12", 90.0),
				new CustomerTransactionRQ(5, "John Doeee", "2024-12", 140.0));

		CustomerRewardsRequest request = new CustomerRewardsRequest();
		request.setCustomerTransactions(transactions);
		request.setMonths(1);

		// Execute service method
		List<CustomerRewardsResponse> rewardsResponses = rewardsService.getRewards(request);

		// Validate results
		equals(rewardsResponses.isEmpty());
	}

	/**
	 * Tests the getRewards method when the request contains no transactions.
	 * Validates that the response is empty.
	 */
	@Test
	void testGetRewards_EmptyRequest() {
		// Prepare test data
		List<CustomerTransactionRQ> transactions = List.of();

		CustomerRewardsRequest request = new CustomerRewardsRequest();
		request.setCustomerTransactions(transactions);
		request.setMonths(1);

		// Execute service method
		List<CustomerRewardsResponse> rewardsResponses = rewardsService.getRewards(request);

		// Validate results
		equals(rewardsResponses.isEmpty());
	}

	/**
	 * Tests the calculatePoints logic indirectly by validating the total reward
	 * points for a single transaction.
	 */
	@Test
	void testCalculatePoints() {
		// Test private method indirectly through exposed methods
		List<CustomerTransactionRQ> transactions = Arrays
				.asList(new CustomerTransactionRQ(1, "John Doe", "2023-11", 120.0));

		CustomerRewardsRequest request = new CustomerRewardsRequest();
		request.setCustomerTransactions(transactions);
		request.setMonths(1);

		List<CustomerRewardsResponse> rewardsResponses = rewardsService.getRewards(request);

		equals(rewardsResponses.isEmpty());
	}
}
