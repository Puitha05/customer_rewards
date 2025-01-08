package com.assignment.rewards.service;

import java.time.LocalDate;
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
		Map<Integer, List<CustomerTransactionRQ>> groupedTransactions = customerRewardsRequest.getCustomerTransactions()
				.stream().filter(t -> t.getMonth().compareTo(month) >= 0) // Filter for the months based on the calculated start month.
				.collect(Collectors.groupingBy(CustomerTransactionRQ::getCustomerId));

		/**
		 * Map the grouped transactions to generate the CustomerRewardsResponse.
		 */
		return groupedTransactions.entrySet().stream().map(entry -> {
			Integer customer = entry.getKey();
			List<CustomerTransactionRQ> transactions = entry.getValue();
			
			List<CustomerTransactionRS> transactionsRSList = transactions.stream()
				    .map(transactionsRQ -> new CustomerTransactionRS(
				        transactionsRQ.getMonth(),
				        transactionsRQ.getAmount()
				    ))
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
			return new CustomerRewardsResponse(customer, transactionsRSList, monthlyPoints, totalPoints);
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
	
//	@Override
//	public CustomerRewardsResponse getCustomerRewards(Integer customerId) {
//	    // Assume `transactions` contains all customer transactions, fetched from a data source.
//		CustomerInfoAndRewardsEntity transactions = fetchCustomerTransactions(customerId);
//
//	    if (transactions.isEmpty()) {
//	        return null; // No transactions found for this customer.
//	    }
//
//	    Map<String, Double> monthlyData = transactions.stream()
//	            .collect(Collectors.groupingBy(CustomerTransaction::getMonth, Collectors.summingDouble(CustomerTransaction::getAmount)));
//
//	    Map<String, Integer> monthlyPoints = monthlyData.entrySet().stream()
//	            .collect(Collectors.toMap(Map.Entry::getKey, e -> calculatePoints(e.getValue())));
//
//	    int totalPoints = monthlyPoints.values().stream().mapToInt(Integer::intValue).sum();
//
//	    return new CustomerRewardsResponse(customerId, transactions, monthlyPoints, totalPoints);
//	}
//	
////	@Override
//    public CustomerInfoAndRewardsEntity fetchCustomerTransactions(Integer customerId) {
//        // Use the repository to fetch transactions by customer ID.
////        return customerTransactionRepository.findByCustomerId(customerId);
//    	try {
////			List<FlightInfoEntity> flightInfoEntity = flightInfoRepository.findByDepartureTimeBetween(fromDate, toDate);
////			return flightInfoEntity;
//			return customerRewardsRepository.findById(customerId);
//		} catch (Exception e) {
//			throw new RuntimeException("Failed to retrieve flight information from database: " + e.getMessage());
//		}
//	}
	
	
	
//	@Override
//	public CustomerInfoAndRewardsRS getCustomerInfoAndRewards(Long customerId) {
//	    // Fetch customer and associated transactions
//	    CustomerInfoAndRewardsEntity customerInfo = customerRewardsRepository.findById(customerId)
//	            .orElse(null);
//
//	    if (customerInfo == null) {
//	        return null; // Customer not found
//	    }
//
//	    List<CustomerTransactionsEntity> transactions = customerInfo.getCustomerRewardsEntity();
//
//	    // Calculate total rewards
//	    int totalPoints = transactions.stream()
//	            .mapToInt(transaction -> calculatePoints(transaction.getAmount()))
//	            .sum();
//
//	    // Create and return response
//	    return new CustomerInfoAndRewardsRS(
//	            customerInfo.getCustomerId().intValue(), // Convert Long to int
//	            customerInfo.getCustomerName(),
//	            totalPoints
//	    );
//	}
	
	@Override
	public CustomerInfoAndRewardsRS getCustomerInfoAndRewards(Long customerId) {
	    // Fetch customer and associated transactions
	    CustomerInfoAndRewardsEntity customerInfo = customerRewardsRepository.findById(customerId)
	            .orElse(null);

	    if (customerInfo == null) {
	        return null; // Customer not found
	    }

	    List<CustomerTransactionsEntity> transactions = customerInfo.getCustomerRewardsEntity();

	    // Map the transactions to CustomerTransactionRS
	    List<CustomerTransactionRS> customerTransactions = transactions.stream()
	            .map(transaction -> new CustomerTransactionRS(transaction.getMonth(), transaction.getAmount()))
	            .collect(Collectors.toList());

	    // Calculate total rewards
	    int totalPoints = transactions.stream()
	            .mapToInt(transaction -> calculatePoints(transaction.getAmount()))
	            .sum();

	    // Create and return response with customer transactions and total points
	    return new CustomerInfoAndRewardsRS(
	            customerInfo.getCustomerId().intValue(), // Convert Long to int
	            customerInfo.getCustomerName(),
	            customerTransactions, // Set customer transactions
	            totalPoints
	    );
	}



}
