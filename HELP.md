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

CREATE TABLE users (
id SERIAL PRIMARY KEY,
first_name VARCHAR(100) NOT NULL,
last_name VARCHAR(100) NOT NULL,
mobile_number VARCHAR(20) NOT NULL UNIQUE,
otp VARCHAR(10),
verified BOOLEAN DEFAULT FALSE
);

select * from users;

CREATE TABLE traders (
id SERIAL PRIMARY KEY,
name VARCHAR(150) NOT NULL,
category VARCHAR(50) NOT NULL,
phone_number VARCHAR(20),
address TEXT,
city VARCHAR(100),
state VARCHAR(100),
pincode VARCHAR(10),
description TEXT,
company_name VARCHAR(150),
created_by_user BIGINT NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
CONSTRAINT fk_user FOREIGN KEY (created_by_user)
REFERENCES users(id) ON DELETE CASCADE
);
select * from traders;


postman collections
{
"info": {
"name": "Localtrade API",
"_postman_id": "f9d30e9c-1234-5678-abcd-123456789abc",
"description": "Test collection for TraderController + User Registration + OTP Verification",
"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
},
"item": [
{
"name": "Register User",
"request": {
"method": "POST",
"header": [
{
"key": "Content-Type",
"value": "application/json"
}
],
"body": {
"mode": "raw",
"raw": "{\n  \"firstname\": \"Ajay\",\n  \"lastname\": \"Kumar\",\n  \"mobileNumber\": \"9876543210\"\n}"
},
"url": {
"raw": "http://localhost:8080/api/users/register",
"protocol": "http",
"host": ["localhost"],
"port": "8080",
"path": ["api", "users", "register"]
}
},
"response": []
},
{
"name": "Verify OTP",
"request": {
"method": "POST",
"header": [
{
"key": "Content-Type",
"value": "application/json"
}
],
"body": {
"mode": "raw",
"raw": "{\n  \"mobileNumber\": \"9876543210\",\n  \"otp\": \"123456\"\n}"
},
"url": {
"raw": "http://localhost:8080/api/users/verify-otp",
"protocol": "http",
"host": ["localhost"],
"port": "8080",
"path": ["api", "users", "verify-otp"]
}
},
"response": []
},
{
"name": "Create Trader",
"request": {
"method": "POST",
"header": [
{
"key": "Content-Type",
"value": "application/json"
}
],
"body": {
"mode": "raw",
"raw": "{\n  \"name\": \"John Doe\",\n  \"category\": \"Plumber\",\n  \"phoneNumber\": \"9876543210\",\n  \"address\": \"123 Street\",\n  \"city\": \"Mumbai\",\n  \"state\": \"Maharashtra\",\n  \"pincode\": \"400001\",\n  \"description\": \"Experienced plumber\",\n  \"companyName\": \"John's Plumbing\",\n  \"createdByUserId\": 1\n}"
},
"url": {
"raw": "http://localhost:8080/api/traders",
"protocol": "http",
"host": ["localhost"],
"port": "8080",
"path": ["api", "traders"]
}
},
"response": []
},
{
"name": "Get All Traders",
"request": {
"method": "GET",
"header": [],
"url": {
"raw": "http://localhost:8080/api/traders",
"protocol": "http",
"host": ["localhost"],
"port": "8080",
"path": ["api", "traders"]
}
},
"response": []
},
{
"name": "Get Trader by ID",
"request": {
"method": "GET",
"header": [],
"url": {
"raw": "http://localhost:8080/api/traders/1",
"protocol": "http",
"host": ["localhost"],
"port": "8080",
"path": ["api", "traders", "1"]
}
},
"response": []
},
{
"name": "Update Trader",
"request": {
"method": "PUT",
"header": [
{
"key": "Content-Type",
"value": "application/json"
}
],
"body": {
"mode": "raw",
"raw": "{\n  \"name\": \"John Doe Updated\",\n  \"category\": \"Electrician\",\n  \"phoneNumber\": \"9876543210\",\n  \"address\": \"456 New Street\",\n  \"city\": \"Mumbai\",\n  \"state\": \"Maharashtra\",\n  \"pincode\": \"400001\",\n  \"description\": \"Certified electrician\",\n  \"companyName\": \"John's Electricals\",\n  \"createdByUserId\": 1\n}"
},
"url": {
"raw": "http://localhost:8080/api/traders/1",
"protocol": "http",
"host": ["localhost"],
"port": "8080",
"path": ["api", "traders", "1"]
}
},
"response": []
},
{
"name": "Delete Trader",
"request": {
"method": "DELETE",
"header": [],
"url": {
"raw": "http://localhost:8080/api/traders/1",
"protocol": "http",
"host": ["localhost"],
"port": "8080",
"path": ["api", "traders", "1"]
}
},
"response": []
},
{
"name": "Search Traders by Pincode",
"request": {
"method": "GET",
"header": [],
"url": {
"raw": "http://localhost:8080/api/traders/search?pincode=400001",
"protocol": "http",
"host": ["localhost"],
"port": "8080",
"path": ["api", "traders", "search"],
"query": [
{
"key": "pincode",
"value": "400001"
}
]
}
},
"response": []
}
]
}


How to import this collection:

Copy the above JSON.

Open Postman.

Click Import (top left).

Select Raw text tab.

Paste the JSON.

Click Import.