CREATE TABLE IF NOT EXISTS person (
    person_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    identification VARCHAR(255) NOT NULL UNIQUE,
    gender VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    phone VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    PRIMARY KEY (person_id)
);
CREATE TABLE IF NOT EXISTS customer (
    customer_id INT PRIMARY KEY REFERENCES person (person_id),
    customer_secret VARCHAR(255) NOT NULL,
    customer_status VARCHAR(255) NOT NULL,
);
CREATE TABLE IF NOT EXISTS account (
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    account_number VARCHAR(255) NOT NULL UNIQUE,
    account_status BOOLEAN NOT NULL,
    account_balance BIGINT NOT NULL,
    account_owner INT NOT NULL REFERENCES person (person_id),
);
CREATE TABLE IF NOT EXISTS transaction (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    transaction_type VARCHAR(255) NOT NULL,
    transaction_amount BIGINT NOT NULL,
    transaction_date DATETIME NOT NULL,
    transaction_account INT NOT NULL REFERENCES account (account_id),
);