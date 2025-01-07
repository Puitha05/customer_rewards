package com.assignment.rewards.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.rewards.service.RewardsService;
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
	 * data and the number of months to consider.
	 * 
	 * @return ResponseEntity containing the calculated rewards or an error message
	 * in case of failure.
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

}
