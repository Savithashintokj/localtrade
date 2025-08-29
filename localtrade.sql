mysql -u root -p
-- Create user and database
CREATE USER 'localtrade'@'localhost' IDENTIFIED BY 'localtrade';
CREATE DATABASE localtrade;
GRANT ALL PRIVILEGES ON localtrade.* TO 'localtrade'@'localhost';
FLUSH PRIVILEGES;

-- Use the new database
USE localtrade;

-- USERS table
CREATE TABLE users (
  user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100),
  mobile_number BIGINT NOT NULL UNIQUE,
  type VARCHAR(1) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CHECK (mobile_number >= 1000000000 AND mobile_number <= 9999999999)
);

-- TRADERS table
CREATE TABLE traders (
  trader_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  company_name VARCHAR(150) NOT NULL,
  category VARCHAR(50) NOT NULL,
  contact_name VARCHAR(150) NOT NULL,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100),
  mobile_number BIGINT NOT NULL UNIQUE,
  address_line TEXT,
  city VARCHAR(100),
  state VARCHAR(100),
  pincode VARCHAR(6),
  description TEXT,
  created_by_user BIGINT NOT NULL,
  linked_user_id BIGINT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (created_by_user) REFERENCES users(user_id) ON DELETE CASCADE,
  FOREIGN KEY (linked_user_id) REFERENCES users(user_id) ON DELETE CASCADE,
  CHECK (mobile_number >= 1000000000 AND mobile_number <= 9999999999),
  CHECK (pincode REGEXP '^[0-9]{6}$')
);

-- OTP_VERIFICATION table
CREATE TABLE otp_verification (
  otp_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  mobile_number BIGINT NOT NULL,
  otp_code VARCHAR(6) NOT NULL,
  otp_status VARCHAR(20),
  verification_status VARCHAR(20),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
  CHECK (mobile_number >= 1000000000 AND mobile_number <= 9999999999)
);
ALTER TABLE otp_verification DROP INDEX mobile_number;
