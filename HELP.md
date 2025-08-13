# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.0/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.0/maven-plugin/build-image.html)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

POSTGRES DB CREATION:

goto cmd

psql -U postgres

CREATE USER localtrade WITH PASSWORD 'localtrade';

CREATE DATABASE localtrade OWNER localtrade;

GRANT ALL PRIVILEGES ON DATABASE localtrade TO localtrade;

\q

psql -U localtrade -d localtrade
-- USERS table
CREATE TABLE users (
user_id BIGSERIAL PRIMARY KEY,
first_name VARCHAR(100) NOT NULL,
last_name  VARCHAR(100),
mobile_number BIGINT NOT NULL UNIQUE CHECK (mobile_number >= 1000000000 AND mobile_number <= 9999999999),
type VARCHAR(1) NOT NULL, 
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- TRADERS table
CREATE TABLE traders (
trader_id BIGSERIAL PRIMARY KEY,
company_name VARCHAR(150) NOT NULL,
category VARCHAR(50) NOT NULL,
contact_name VARCHAR(150) NOT NULL,
first_name VARCHAR(100) NOT NULL,
last_name  VARCHAR(100),
mobile_number BIGINT NOT NULL UNIQUE CHECK (mobile_number >= 1000000000 AND mobile_number <= 9999999999),
address_line TEXT,
city VARCHAR(100),
state VARCHAR(100),
pincode VARCHAR(6) CHECK (pincode ~ '^[0-9]{6}$'),
description TEXT,
created_by_user BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
linked_user_id BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE, -- This links the trader to their user account
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- OTP_VERIFICATION table
CREATE TABLE otp_verification (
otp_id BIGSERIAL PRIMARY KEY,
user_id BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
mobile_number BIGINT NOT NULL UNIQUE CHECK (mobile_number >= 1000000000 AND mobile_number <= 9999999999),
otp_code VARCHAR(6) NOT NULL,
otp_status VARCHAR(20) ,
verification_status VARCHAR(20) ,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


 