package com.assignment.rewards;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.assignment.rewards.controller.RewardsController;
import com.assignment.rewards.service.RewardsService;
import com.assignment.rewards.vo.CustomerRewardsRequest;
import com.assignment.rewards.vo.CustomerRewardsResponse;
import com.assignment.rewards.vo.CustomerTransaction;
import com.fasterxml.jackson.databind.ObjectMapper;

class RewardsControllerTest {

	@Mock
	private RewardsService rewardsService;

	@InjectMocks
	private RewardsController rewardsController;

	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	public RewardsControllerTest() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(rewardsController).build();
		this.objectMapper = new ObjectMapper();
	}

	/**
	 * Tests the getRewards endpoint of the RewardsController. 
	 * Validates that the endpoint: 
	 * i.Handles a POST request successfully.
	 * ii.Interacts with the service layer correctly. 
	 * iii.Returns an HTTP 200 OK status for valid input.
	 */
	@Test
	void testGetRewards() throws Exception {
		// Prepare test data
		List<CustomerTransaction> transactions = Arrays.asList(new CustomerTransaction(1, "John Doe", "2023-11", 120.0),
				new CustomerTransaction(2, "Jane Smith", "2023-12", 50.0));

		CustomerRewardsRequest request = new CustomerRewardsRequest();
		request.setCustomerTransactions(transactions);
		request.setMonths(2);

		List<CustomerRewardsResponse> response = Arrays
				.asList(new CustomerRewardsResponse(1, transactions, Map.of("2023-11", 90), 90));
		
		/**
		 * Mock the service call
		 */
		when(rewardsService.getRewards(request)).thenReturn(response);

		/**
		 * Perform the POST request and validate the response status. Test the
		 * controller's endpoint and set content type as JSON,Serialize the request to
		 * JSON and Validate the HTTP status is 200 OK
		 */
		mockMvc.perform(post("/api/rewards/getWebRewardPoints").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk());
	}

}
