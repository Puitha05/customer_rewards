package com.assignment.rewards.controller;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.rewards.service.RewardsService;
import com.assignment.rewards.vo.CustomerInfoAndRewardsRS;
import com.assignment.rewards.vo.CustomerRewardsRequest;
import com.assignment.rewards.vo.CustomerRewardsResponse;

/**
 * REST controller for handling reward points calculations. Exposes endpoints
 * for clients to submit customer transaction data and receive calculated reward
 * points.
 */
@RestController
@RequestMapping("/api/rewards")
public class RewardsController {

	@Autowired
	public RewardsService rewardsService;

	/**
	 * Handles HTTP POST requests to calculate reward points.
	 * 
	 * @param customerRewardsRequest Request body containing customer transaction
	 *                               data and the number of months to consider.
	 * 
	 * @return ResponseEntity containing the calculated rewards or an error message
	 *         in case of failure.
	 */
	@PostMapping("/getWebRewardPoints")
	public ResponseEntity<?> getRewards(@RequestBody CustomerRewardsRequest customerRewardsRequest) {
		try {
			List<CustomerRewardsResponse> res = rewardsService.getRewards(customerRewardsRequest);
			return ResponseEntity.ok(res);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while calculating rewards: " + e.getMessage());
		}
	}

	/**
	 * Handles HTTP GET requests to fetch transactions and rewards for a specific
	 * customer within a given month-year range.
	 *
	 * @param customerId The ID of the customer whose data is being requested.
	 * @param startDate  The start date of the range in "MM-yyyy" format.
	 * @param endDate    The end date of the range in "MM-yyyy" format.
	 * @return ResponseEntity containing the combined data of customer and rewards
	 *         earned by customer or an error message in case of failure.
	 */
	@GetMapping("/getCustomerTransactionsAndRewards")
	public ResponseEntity<?> getCustomerTransactionsAndRewards(@RequestParam Long customerId,
			@RequestParam String startDate, @RequestParam String endDate) {
		try {
			// Parse the startDate and endDate in "MM-yyyy" format
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");
			YearMonth start = YearMonth.parse(startDate, formatter);
			YearMonth end = YearMonth.parse(endDate, formatter);

			/**
			 * Fetch customer transactions and rewards
			 */
			CustomerInfoAndRewardsRS customerInfoAndRewards = rewardsService
					.getCustomerTransactionsAndRewards(customerId, start, end);

			if (customerInfoAndRewards != null) {
				return ResponseEntity.ok(customerInfoAndRewards);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found.");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while fetching transactions and rewards: " + e.getMessage());
		}

	}

}
