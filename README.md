# Patient Management System

## Overview
The **Patient Management System** is a web application built using **Spring Boot** to manage patient records efficiently. It provides functionalities for adding, updating, and retrieving patient information while ensuring secure and scalable operations.

## Features
- **Spring Boot Web**: RESTful APIs for handling patient data.
- **Spring Data JPA**: Integration with MySQL database.
- **Thymeleaf**: UI rendering for web application views.
- **H2 Database**: In-memory testing.
- **Selenium**: E2E testing.
- **TestContainers**: Containerized testing support.
- **SonarCloud Integration**: Code quality and coverage analysis.
- **Jacoco & Pitest**: Code coverage and mutation testing.

## Technologies Used
- **Java 17**
- **Spring Boot 3.1.3**
- **MySQL 8.0.33**
- **Thymeleaf**
- **Selenium 4.12.0**
- **TestContainers 1.19.0**
- **Jacoco, Pitest, Coveralls**

## Setup and Installation
### Prerequisites
- Java 17 or later
- Maven
- MySQL Database

### Steps
1. Clone the repository:
   ```sh
   git clone <repository_url>
   cd management
   ```
2. Configure MySQL in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/patient_db
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```
3. Build and run the application:
   ```sh
   mvn spring-boot:run
   ```
4. Access the application at `http://localhost:8080`

## Running Tests
- **Unit Tests**: `mvn test`
- **Integration Tests**: `mvn verify`
- **Mutation Testing**: `mvn verify -Pmutation-testing`

## Code Quality
- **SonarCloud Analysis**: `mvn sonar:sonar`
- **Code Coverage Report**: `mvn verify -Pjacoco`


