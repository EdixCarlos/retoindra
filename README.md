# Reto Indra

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

### Building the Project

To build the project, run the following command:

```sh
./gradlew clean build
```

### Running the Application

To run the application, use the following command:

```sh
./gradlew bootRun
```

### Running with Docker

To build and run the Docker container, use the following commands:

```sh
docker build -t retoindra:0.0.1-SNAPSHOT .
docker run -p 8080:8080 retoindra:0.0.1-SNAPSHOT
```

### API Documentation

The API documentation is available at `http://localhost:8080/swagger-ui/index.html` when the application is running.

