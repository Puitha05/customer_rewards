package com.assignment.rewards.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assignment.rewards.entity.CustomerInfoAndRewardsEntity;
import com.assignment.rewards.entity.CustomerTransactionsEntity;

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
	 @Query("SELECT c FROM CustomerTransactionsEntity c WHERE c.month IN :months")
	    List<CustomerTransactionsEntity> findByMonths(@Param("months") List<String> months);
	

}
