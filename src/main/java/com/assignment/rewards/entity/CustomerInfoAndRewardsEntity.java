package com.assignment.rewards.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "customer_info")
public class CustomerInfoAndRewardsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    /**
     * Customer name.
     */
    @Column(name = "customer_name", nullable = false)
    private String customerName;

    /**
     * List of customer transactions.
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customerId")
    private List<CustomerTransactionsEntity> customerRewardsEntity;

    // Constructors
    public CustomerInfoAndRewardsEntity() {
    }

    public CustomerInfoAndRewardsEntity(String customerName, List<CustomerTransactionsEntity> customerTransactions) {
        this.customerName = customerName;
        this.customerRewardsEntity = customerTransactions;
    }

    // Getters and Setters
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<CustomerTransactionsEntity> getCustomerRewardsEntity() {
        return customerRewardsEntity;
    }

    public void setCustomerRewardsEntity(List<CustomerTransactionsEntity> customerRewardsEntity) {
        this.customerRewardsEntity = customerRewardsEntity;
    }

    @Override
    public String toString() {
        return "CustomerRewardsEntity{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", customerTransactions=" + customerRewardsEntity +
                '}';
    }
}