package com.assignment.rewards.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.rewards.entity.CustomerInfoAndRewardsEntity;

/**
 * The Interface CustomerRewardsRepository.
 */
@Repository
public interface CustomerRewardsRepository extends JpaRepository<CustomerInfoAndRewardsEntity, Long>{

	/**
	 * Find by departure time between.
	 *
	 * @param fromDate the from date
	 * @param toDate the to date
	 * @return the list
	 */
//	CustomerInfoAndRewardsEntity findByCustomerId(LocalDate fromDate, LocalDate toDate);
	CustomerInfoAndRewardsEntity findByCustomerId(Long customerId);
	

}
