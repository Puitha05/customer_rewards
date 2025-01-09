package com.assignment.rewards.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.rewards.entity.CustomerInfoAndRewardsEntity;
import com.assignment.rewards.entity.CustomerTransactionsEntity;
import com.assignment.rewards.repository.CustomerRewardsRepository;
import com.assignment.rewards.vo.CustomerInfoAndRewardsRS;
import com.assignment.rewards.vo.CustomerRewardsRequest;
import com.assignment.rewards.vo.CustomerRewardsResponse;
import com.assignment.rewards.vo.CustomerTransactionRQ;
import com.assignment.rewards.vo.CustomerTransactionRS;

@Service
public class RewardsServiceImpl implements RewardsService {

	/** The flight info repository. */
	@Autowired
	private CustomerRewardsRepository customerRewardsRepository;

	/**
	 * @param customerRewardsRequest
	 * @return
	 */
	@Override
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
		Map<Integer, List<CustomerTransactionRQ>> groupedTransactions = customerRewardsRequest.getCustomerTransactions()
				.stream().filter(t -> t.getMonth().compareTo(month) >= 0) // Filter for the months based on the
																			// calculated start month.
				.collect(Collectors.groupingBy(CustomerTransactionRQ::getCustomerId));

		/**
		 * Map the grouped transactions to generate the CustomerRewardsResponse.
		 */
		return groupedTransactions.entrySet().stream().map(entry -> {
			Integer customer = entry.getKey();
			List<CustomerTransactionRQ> transactions = entry.getValue();
			String customerName = transactions.get(0).getCustomerName();
			List<CustomerTransactionRS> transactionsRSList = transactions.stream().map(
					transactionsRQ -> new CustomerTransactionRS(transactionsRQ.getMonth(), transactionsRQ.getAmount()))
					.collect(Collectors.toList());

			/**
			 * Calculate the total transaction amount per month.
			 */
			Map<String, Double> monthlyData = transactions.stream().collect(Collectors.groupingBy(
					CustomerTransactionRQ::getMonth, Collectors.summingDouble(CustomerTransactionRQ::getAmount)));

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
			return new CustomerRewardsResponse(customer, customerName, transactionsRSList, monthlyPoints, totalPoints);
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

	/**
	 * Returns the specific customer transaction which falls under specific start
	 * and endMonth
	 * 
	 * @param customerId
	 * @param startMonth
	 * @param endMonth
	 */

	@Override
	public CustomerInfoAndRewardsRS getCustomerTransactionsAndRewards(Long customerId, YearMonth startMonth,
			YearMonth endMonth) {
		CustomerInfoAndRewardsEntity customerInfo = customerRewardsRepository.findById(customerId).orElse(null);

		if (customerInfo == null) {
			return null;
		}

		/**
		 * Filter transactions within the month-year range
		 */
		List<CustomerTransactionsEntity> filteredTransactions = customerInfo.getCustomerTransactionsEntity().stream()
				.filter(transaction -> {
					YearMonth transactionMonth = YearMonth.parse(transaction.getMonth(),
							DateTimeFormatter.ofPattern("yyyy-MM"));
					return (transactionMonth.equals(startMonth) || transactionMonth.isAfter(startMonth))
							&& (transactionMonth.equals(endMonth) || transactionMonth.isBefore(endMonth));
				}).collect(Collectors.toList());

		/**
		 * If no transactions are found, return null
		 */
		if (filteredTransactions.isEmpty()) {
			return null;
		}

		/**
		 * Map transactions to CustomerTransactionRS
		 */
		List<CustomerTransactionRS> transactionsRS = filteredTransactions.stream()
				.map(transaction -> new CustomerTransactionRS(transaction.getMonth(), transaction.getAmount()))
				.collect(Collectors.toList());

		/**
		 * Calculate total transaction amount per month
		 */
		Map<String, Double> monthlyTransactionAmounts = filteredTransactions.stream().collect(Collectors.groupingBy(
				CustomerTransactionsEntity::getMonth, Collectors.summingDouble(CustomerTransactionsEntity::getAmount)));

		/**
		 * Calculate reward points per month based on transaction amounts
		 */
		Map<String, Integer> monthlyPoints = monthlyTransactionAmounts.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, entry -> calculatePoints(entry.getValue())));

		/**
		 * Calculate total reward points across all months
		 */
		int totalPoints = monthlyPoints.values().stream().mapToInt(Integer::intValue).sum();

		return new CustomerInfoAndRewardsRS(customerInfo.getCustomerId().intValue(), customerInfo.getCustomerName(),
				transactionsRS, monthlyPoints, totalPoints);
	}

}
