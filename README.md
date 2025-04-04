# API Gateway Throttling

An API gateway implementation using Spring Cloud Gateway for request routing and throttling with Bucket4j based on the token-bucket algorithm.

## Overview

This project is built with Java 21 and Spring Boot 3.3.4, using Spring Cloud Gateway to route and filter API requests. It leverages Bucket4j to enforce rate limiting via the token-bucket algorithm, ensuring your API is protected from excessive requests. The filtering logic is abstracted in a base class to support different types of throttling (for example, by IP or by header).

## Key Features

- **Throttling Filters:**  
  An abstract filter (`AbstractThrottlingFilter`) that provides a blueprint for various limiting strategies.
  
- **IP-Based Rate Limiting:**  
  The `IpThrottlingFilter` class extracts the client IP (supporting `X-Forwarded-For` headers) and applies rate limiting.

- **Header-Based Rate Limiting:**  
  The `HeaderThrottlingFilter` class resolves a specific header value to be used as the key for throttling.

- **Bucket4j Integration:**  
  Uses Bucket4j (v8.14.0) to implement the token bucket algorithm for rate limiting.

- **Caching Support:**  
  Integrates with Caffeine Cache to support rate limiting in distributed or high-load environments.

## Technologies Used

- **Java 21**
- **Spring Boot 3.3.4**
- **Spring Cloud Gateway**
- **Bucket4j** (for rate limiting)
- **Caffeine Cache**
- **Maven** for dependency management

## Project Structure

```
api-gateway-throttling/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── dev/mehdizebhi/gateway/filter/
│   │   │       ├── AbstractThrottlingFilter.java
│   │   │       ├── HeaderThrottlingFilter.java
│   │   │       └── IpThrottlingFilter.java
│   │   └── resources/
│   │       └── application.yml (or application.properties)
└── pom.xml
```

- **AbstractThrottlingFilter:**  
  Provides the common logic to try consuming a token from a rate limit bucket and handling the response if the limit is exceeded.

- **HeaderThrottlingFilter & IpThrottlingFilter:**  
  Extend the abstract filter to use different request properties (headers or IP addresses) as keys for rate limiting.

## Running the Project

1. **Clone the repository:**

   ```bash
   git clone https://your.repo.url/api-gateway-throttling.git
   cd api-gateway-throttling
   ```

2. **Build the project using Maven:**

   ```bash
   mvn clean install
   ```

3. **Run the application:**

   ```bash
   mvn spring-boot:run
   ```

   Alternatively, run the packaged jar:

   ```bash
   java -jar target/gateway-0.0.1-SNAPSHOT.jar
   ```
