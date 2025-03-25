# Patient Management System

## Overview
The **Patient Management System** is a web application built using **Spring Boot** to manage patient records efficiently. It provides functionalities for adding, updating, and retrieving patient information while ensuring secure and scalable operations.

## Sonar Cloud and CoverAlls Coverage Badge:
Sonar Cloud: [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Talathussain110_management&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Talathussain110_management)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Talathussain110_management&metric=bugs)](https://sonarcloud.io/summary/new_code?id=Talathussain110_management)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=Talathussain110_management&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=Talathussain110_management)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Talathussain110_management&metric=coverage)](https://sonarcloud.io/summary/new_code?id=Talathussain110_management)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=Talathussain110_management&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=Talathussain110_management)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Talathussain110_management&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=Talathussain110_management)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Talathussain110_management&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=Talathussain110_management)

Code coverall: [![Coverage Status](https://coveralls.io/repos/github/Talathussain110/management/badge.svg?branch=main)](https://coveralls.io/github/Talathussain110/management?branch=main)

GithubAction: ![Java CI with Maven](https://github.com/Talathussain110/management/blob/main/.github/workflows/maven.yml)


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
2. Open Docker Desktop:
   
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


