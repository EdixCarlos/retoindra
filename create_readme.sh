#!/bin/bash

# Define the content of the README.md file
readme_content="# Reto Indra

## Description

This project is a Spring Boot application that manages exchange rates. It provides endpoints to apply, update, and retrieve exchange rates.

## Technologies Used

- Java 17
- Spring Boot 3.4.2
- Gradle
- Docker
- Jenkins
- H2 Database
- Lombok
- MapStruct
- OpenAPI
- Reactor

## Getting Started

### Prerequisites

- Java 17
- Gradle
- Docker
- Docker Compose

### Building the Project

To build the project, run the following command:

\`\`\`sh
./gradlew clean build
\`\`\`

### Running the Application

To run the application, use the following command:

\`\`\`sh
./gradlew bootRun
\`\`\`

### Running with Docker

To build and run the Docker container, use the following commands:

\`\`\`sh
docker build -t retoindra:0.0.1-SNAPSHOT .
docker run -p 8080:8080 retoindra:0.0.1-SNAPSHOT
\`\`\`

### Running with Docker Compose

To start the application with Docker Compose, use the following command:

\`\`\`sh
docker-compose up
\`\`\`

### API Documentation

The API documentation is available at \`/swagger-ui.html\` when the application is running.

## Usage

### Apply Exchange Rate

\`\`\`sh
POST /api/exchange-rate/apply
\`\`\`

Request Body:

\`\`\`json
{
  \"amount\": 100.0,
  \"sourceCurrency\": \"USD\",
  \"targetCurrency\": \"EUR\"
}
\`\`\`

### Update Exchange Rate

\`\`\`sh
POST /api/exchange-rate/update
\`\`\`

Request Body:

\`\`\`json
{
  \"sourceCurrency\": \"USD\",
  \"targetCurrency\": \"EUR\",
  \"rate\": 0.85
}
\`\`\`

### Get Exchange Rate

\`\`\`sh
GET /api/exchange-rate?sourceCurrency=USD&targetCurrency=EUR
\`\`\`

### Get All Exchange Rates with Pagination

\`\`\`sh
GET /api/exchange-rate/all?page=0&size=4&sort=rate,asc
\`\`\`

## Running Tests

To run the tests, use the following command:

\`\`\`sh
./gradlew test
\`\`\`

## Code Quality

### SonarQube

To analyze the code with SonarQube, use the following command:

\`\`\`sh
./gradlew sonarqube
\`\`\`

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
"

# Create the README.md file and write the content to it
echo "$readme_content" > README.md

# Print a message indicating that the README.md file has been created
echo "README.md file has been created successfully."