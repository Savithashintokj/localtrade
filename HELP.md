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
