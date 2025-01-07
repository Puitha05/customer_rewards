package com.assignment.rewards.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.assignment.rewards.vo.CustomerRewardsRequest;
import com.assignment.rewards.vo.CustomerRewardsResponse;
import com.assignment.rewards.vo.CustomerTransaction;

@Service
public class RewardsServiceImpl implements RewardsService {

	@Override
	/**
	 * @param customerRewardsRequest
	 * @return
	 */
	public List<CustomerRewardsResponse> getRewards(CustomerRewardsRequest customerRewardsRequest) {

		LocalDate currentDate = LocalDate.now();

		/**
		 * Calculate the starting month to filter transactions based on the requested
		 * time frame.
		 */
		String month = currentDate.minusMonths(customerRewardsRequest.getMonths() - 1)
				.format(DateTimeFormatter.ofPattern("yyyy-MM"));

		/**
		 * Filter and group transactions by customer and month, while summing their
		 * amounts.
		 */
		Map<Integer, List<CustomerTransaction>> groupedTransactions = customerRewardsRequest.getCustomerTransactions()
				.stream().filter(t -> t.getMonth().compareTo(month) >= 0) // Filter for the months based on the calculated start month.
				.collect(Collectors.groupingBy(CustomerTransaction::getCustomerId));

		/**
		 * Map the grouped transactions to generate the CustomerRewardsResponse.
		 */
		return groupedTransactions.entrySet().stream().map(entry -> {
			Integer customer = entry.getKey();
			List<CustomerTransaction> transactions = entry.getValue();

			/**
			 * Calculate the total transaction amount per month.
			 */
			Map<String, Double> monthlyData = transactions.stream().collect(Collectors.groupingBy(
					CustomerTransaction::getMonth, Collectors.summingDouble(CustomerTransaction::getAmount)));

			/**
			 * Calculate the reward points per month based on the transaction amount.
			 */
			Map<String, Integer> monthlyPoints = monthlyData.entrySet().stream()
					.collect(Collectors.toMap(Map.Entry::getKey, e -> calculatePoints(e.getValue())));

			/**
			 * Calculate the total reward points across all months for the customer.
			 */
			int totalPoints = monthlyPoints.values().stream().mapToInt(Integer::intValue).sum();

			/**
			 * Return the response with detailed transactions, monthly points, and total
			 * points.
			 */
			return new CustomerRewardsResponse(customer, transactions, monthlyPoints, totalPoints);
		}).collect(Collectors.toList()); // Collect all CustomerRewardsResponse objects into a list.
	}

	/**
	 * 
	 * Calculates reward points based on the transaction amount.
	 * 
	 * Points are awarded as follows: 2 points for every dollar spent over $100. 1
	 * point for every dollar spent between $50 and $100.
	 * 
	 * 
	 * @param amount
	 * @return
	 * 
	 */
	private int calculatePoints(double amount) {
		int points = 0;
		if (amount > 100) {
			points += (amount - 100) * 2;
			points += 50; // Points for the $50-$100 range
		} else if (amount > 50) {
			points += (amount - 50);
		}
		return points;
	}
}
