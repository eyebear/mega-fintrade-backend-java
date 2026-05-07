# mega-fintrade-backend-java

[![Java CI](https://github.com/eyebear/mega-fintrade-backend-java/actions/workflows/ci.yml/badge.svg)](https://github.com/eyebear/mega-fintrade-backend-java/actions/workflows/ci.yml)

Java Spring Boot backend for the Mega Fintrade Platform. This service provides REST APIs for portfolio positions, profit and loss calculation, batch position operations, and future risk reporting services.

This project is part of the larger Mega Fintrade Platform and will later integrate with the Python quantitative analytics engine, the C++ market data processing engine, and the C# monitoring service.

---

## Overview

The backend currently supports core portfolio position management.

The system allows users to:

- Store stock positions
- Retrieve portfolio positions
- Retrieve a position by ID
- Create positions in batch
- Calculate profit and loss based on a supplied market price
- Validate input data
- Run automated tests through Maven and GitHub Actions

The project is designed as a production-style Java backend with a clean layered architecture.

---

## Tech Stack

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- PostgreSQL for application persistence
- H2 in-memory database for tests
- Maven
- Docker
- Docker Compose
- GitHub Actions CI

---

## Project Structure

    src/main/java/com/ao/portfolio
    ├── PortfolioRiskPlatformApplication.java
    ├── controller
    ├── service
    ├── repository
    ├── entity
    ├── dto
    └── exception

Main responsibility of each layer:

- controller: exposes REST API endpoints
- service: contains business logic
- repository: handles database access through Spring Data JPA
- entity: defines database-backed domain models
- dto: defines request and response objects
- exception: handles application-specific error cases

---

## Current API Endpoints

### Get all positions

    GET /api/positions

### Create one position

    POST /api/positions

Example request body:

    {
      "symbol": "AAPL",
      "quantity": 10,
      "avgPrice": 150
    }

### Create positions in batch

    POST /api/positions/batch

Example request body:

    [
      {
        "symbol": "MSFT",
        "quantity": 5,
        "avgPrice": 300
      },
      {
        "symbol": "GOOGL",
        "quantity": 3,
        "avgPrice": 120
      }
    ]

### Get position by ID

    GET /api/positions/{id}

Example:

    GET /api/positions/1

### Calculate profit and loss

    GET /api/positions/{id}/pnl/{price}

Example:

    GET /api/positions/1/pnl/160

---

## Running the Application Locally

### Option 1: Run with Maven

Start the application:

    ./mvnw spring-boot:run

The application runs on:

    http://localhost:8080

Example endpoint:

    http://localhost:8080/api/positions

### Option 2: Run with Docker Compose

Build and start the application:

    docker compose up --build

Then access:

    http://localhost:8080/api/positions

---

## Running Tests

Run all tests:

    ./mvnw test

Run a full clean build:

    ./mvnw clean package

Run full verification:

    ./mvnw clean verify

Tests use the H2 in-memory database, so PostgreSQL is not required for the test environment.

---

## Continuous Integration

This project uses GitHub Actions.

The CI workflow runs automatically on pushes and pull requests to:

- main
- master

The workflow performs the following checks:

- checks out the repository
- sets up Java 17
- restores Maven dependency cache
- runs Maven verification using:

    mvn clean verify

The CI status is shown by the badge at the top of this README.

---

## Key Features

- RESTful API design
- Layered Spring Boot architecture
- Controller, service, repository separation
- JPA-based database persistence
- DTO-based request and response handling
- Input validation
- Exception handling
- H2-based automated tests
- Dockerized backend setup
- Docker Compose support
- GitHub Actions CI pipeline

---

## Role in the Mega Fintrade Platform

This repository is Project 1 of the Mega Fintrade Platform.

Its long-term purpose is to become the central Java backend service that will:

- store portfolio positions
- store cleaned market data
- store strategy signals
- store backtest results
- expose portfolio summary APIs
- expose risk reporting APIs
- provide data to the C# monitoring service

Future integrations will connect this backend with:

- mega-fintrade-quant-engine
- mega-fintrade-market-engine-cpp
- future Mega Fintrade monitoring service

---

## Repository

Repository URL:

    https://github.com/eyebear/mega-fintrade-backend-java

---

## License

MIT License
