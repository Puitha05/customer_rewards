package com.assignment.rewards.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.rewards.service.RewardsService;
import com.assignment.rewards.vo.CustomerInfoAndRewardsRS;
import com.assignment.rewards.vo.CustomerRewardsRequest;
import com.assignment.rewards.vo.CustomerRewardsResponse;
import com.assignment.rewards.vo.MonthlyTransactionRS;

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
	 * Handles HTTP GET requests to fetch reward points for a specific customer.
	 * 
	 * @param customerId The ID of the customer whose rewards data is being
	 *                   requested.
	 * @return ResponseEntity containing the reward points or an error message in
	 *         case of failure.
	 */
	@GetMapping("/getCustomerInfoAndRewards/{customerId}")
	public ResponseEntity<?> getCustomerRewards(@PathVariable Long customerId) {
		try {
			CustomerInfoAndRewardsRS response = rewardsService.getCustomerInfoAndRewards(customerId);
			if (response != null) {
				return ResponseEntity.ok(response);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found.");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while fetching rewards: " + e.getMessage());
		}
	}

	/**
	 * Endpoint to fetch customer transaction data for a specific months period.
	 * 
	 * @param monthId - 1 for current month, 2 for previous and current month
	 * @return List of customer transaction data for the given month(s)
	 */
	@GetMapping("/getTransactionsByMonth/{monthId}")
	public ResponseEntity<?> getTransactionsByMonth(@PathVariable int monthId) {
		try {
			// Get the rewards based on the monthId
			List<MonthlyTransactionRS> res = rewardsService.getTransactionsByMonth(monthId);
			return ResponseEntity.ok(res);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while fetching customer data for the month: " + e.getMessage());
		}
	}

}
