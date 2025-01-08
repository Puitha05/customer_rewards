CREATE TABLE customer_info (
    customer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(255) NOT NULL
);

CREATE TABLE customer_transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    transaction_month VARCHAR(255) NOT NULL,
    transaction_amount DOUBLE NOT NULL,
    customer_id BIGINT,
    FOREIGN KEY (customer_id) REFERENCES customer_info(customer_id)
);