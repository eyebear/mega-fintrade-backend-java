# mega-fintrade-backend-java

[![Java CI](https://github.com/eyebear/mega-fintrade-backend-java/actions/workflows/ci.yml/badge.svg)](https://github.com/eyebear/mega-fintrade-backend-java/actions/workflows/ci.yml)

Java Spring Boot backend for the Mega Fintrade Platform.

This service is Project 1 of the Mega Fintrade Platform. It acts as the central backend that stores portfolio, import, batch, strategy, risk, backtest, and report data. It exposes REST APIs, imports CSV outputs from the quantitative engine, stores audit and rejection records, and provides report summary endpoints for future monitoring and dashboard services.

---

## Overview

The backend currently supports:

- Portfolio position management
- Position profit and loss calculation
- Batch position operations
- Quant CSV import
- Spring Batch-based import execution
- Scheduled quant import jobs
- Scheduled report recalculation jobs
- Import audit history
- Rejected CSV record logging
- Report summary APIs
- Automated testing with Maven, H2, and GitHub Actions
- Dockerized runtime with PostgreSQL

This project is designed as a production-style Java backend with a clean layered architecture.

---

## Tech Stack

- Java 17
- Spring Boot 4
- Spring Web
- Spring Data JPA
- Hibernate
- Spring Batch
- Spring Scheduling
- PostgreSQL for application persistence
- H2 in-memory database for tests
- Maven Wrapper
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
    ├── config
    ├── scheduler
    └── exception

Main responsibility of each layer:

- `controller`: exposes REST API endpoints
- `service`: contains business logic
- `repository`: handles database access through Spring Data JPA
- `entity`: defines database-backed domain models
- `dto`: defines request and response objects
- `config`: contains application and batch configuration
- `scheduler`: contains scheduled backend jobs
- `exception`: handles application-specific error cases

---

## Current API Endpoints

### Position APIs

Get all positions:

    GET /api/positions

Create one position:

    POST /api/positions

Example request body:

    {
      "symbol": "AAPL",
      "quantity": 10,
      "avgPrice": 150
    }

Create positions in batch:

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

Get position by ID:

    GET /api/positions/{id}

Example:

    GET /api/positions/1

Calculate profit and loss:

    GET /api/positions/{id}/pnl/{price}

Example:

    GET /api/positions/1/pnl/160

---

### Quant Import APIs

Import all default quant CSV files:

    POST /api/import/all

View recent import audit records:

    GET /api/import/audit

View recent rejected CSV records:

    GET /api/import/rejections

---

### Batch APIs

Run all quant import batch jobs:

    POST /api/batch/run

---

### Report APIs

Get portfolio report summary:

    GET /api/reports/summary

---

## Quant CSV Input Files

The backend imports CSV files from:

    data/input

Expected files:

    data/input/risk_metrics.csv
    data/input/backtest_results.csv
    data/input/strategy_signals.csv
    data/input/portfolio_equity_curve.csv

The current strategy signal import is scoped to the configured Project 2 symbol universe. A future version may normalize strategy signals into one row per date and symbol to support a dynamic stock universe.

---

## Running the Application Locally

### Requirements

- Java 17
- Maven Wrapper included in the repository
- PostgreSQL running locally
- Database named `portfolio_db`
- Database user and password matching `src/main/resources/application.properties`

### Start the application

From the project root:

    ./mvnw spring-boot:run

The backend runs on:

    http://localhost:8080

Example endpoint:

    http://localhost:8080/api/reports/summary

---

## Running Tests

Run all tests:

    ./mvnw test

Run a clean test suite:

    ./mvnw clean test

Run a full package build:

    ./mvnw clean package

Tests use the H2 in-memory database through:

    src/test/resources/application.properties

PostgreSQL is not required for the test environment.

The test configuration disables scheduled jobs so background imports do not interfere with automated tests.

---

## Docker Runtime

This project supports Docker-based runtime packaging. Docker Compose starts both the Spring Boot backend and a PostgreSQL database.

### Docker Services

| Service | Description | Host URL / Port |
|---|---|---|
| Backend | Spring Boot Java backend | http://localhost:8080 |
| PostgreSQL | Dockerized PostgreSQL database | localhost:5433 |

Inside Docker Compose, the backend connects to PostgreSQL using the service name `postgres`:

    jdbc:postgresql://postgres:5432/portfolio_db

From the host machine, PostgreSQL is exposed on:

    localhost:5433

---

### Docker Requirements

Install and start Docker Desktop before running the project.

Check Docker:

    docker --version
    docker compose version

---

### Build the Backend Docker Image Only

To build the backend Docker image:

    docker build -t mega-fintrade-backend-java .

This uses the project `Dockerfile` and packages the Spring Boot application into a runnable container image.

---

### Run Backend and PostgreSQL Together

From the project root:

    docker compose up --build

This will:

1. Start PostgreSQL.
2. Wait until PostgreSQL is healthy.
3. Build the Spring Boot backend image.
4. Start the backend container on port `8080`.

---

### Test the Backend API in Docker

After Docker Compose starts successfully, test these endpoints:

    GET http://localhost:8080/api/reports/summary
    GET http://localhost:8080/api/import/audit
    GET http://localhost:8080/api/import/rejections

To manually trigger CSV import:

    POST http://localhost:8080/api/import/all

The import reads CSV files from:

    data/input

The Docker Compose file mounts the local `data` folder into the backend container:

    ./data:/app/data

So the backend container can read:

    /app/data/input/risk_metrics.csv
    /app/data/input/backtest_results.csv
    /app/data/input/strategy_signals.csv
    /app/data/input/portfolio_equity_curve.csv

---

### Stop Docker Containers

To stop the backend and database containers:

    docker compose down

This stops the containers but keeps the PostgreSQL data volume.

---

### Reset the Docker Database

To stop the containers and delete the Docker PostgreSQL volume:

    docker compose down -v

Use this only when you want to reset the Docker database completely.

---

### Docker Configuration

The Docker runtime uses the Spring profile:

    docker

This is set in `docker-compose.yml`:

    SPRING_PROFILES_ACTIVE=docker

The Docker-specific Spring configuration is stored in:

    src/main/resources/application-docker.properties

The local development configuration remains in:

    src/main/resources/application.properties

The test configuration remains in:

    src/test/resources/application.properties

This separates the three environments:

| Environment | Config file | Database |
|---|---|---|
| Local development | `src/main/resources/application.properties` | Local PostgreSQL |
| Test / CI | `src/test/resources/application.properties` | H2 in-memory database |
| Docker runtime | `src/main/resources/application-docker.properties` | Docker PostgreSQL |

---

## Continuous Integration

This project uses GitHub Actions.

The CI workflow runs automatically on:

- Pushes to `main`
- Pull requests targeting `main`

The workflow:

- Checks out the repository
- Sets up Java 17
- Uses the Maven dependency cache
- Runs the full Maven test suite with the Maven Wrapper

CI command:

    ./mvnw clean test

The CI status is shown by the badge at the top of this README.

---

## Key Features

- RESTful API design
- Layered Spring Boot architecture
- Controller, service, repository, DTO, and entity separation
- JPA-based database persistence
- PostgreSQL runtime persistence
- H2-based test persistence
- CSV import services
- Spring Batch import runner
- Scheduled backend jobs
- Import audit logging
- Rejected record logging
- Report summary API
- Dockerized backend setup
- Docker Compose support for backend and database
- GitHub Actions CI pipeline

---

## Role in the Mega Fintrade Platform

This repository is Project 1 of the Mega Fintrade Platform.

Its long-term purpose is to become the central Java backend service that will:

- Store portfolio positions
- Store quant import results
- Store strategy signals
- Store backtest results
- Store portfolio equity curve data
- Store import audit history
- Store rejected import rows
- Expose portfolio summary APIs
- Expose risk reporting APIs
- Provide data to the future C# monitoring service

Future integrations will connect this backend with:

- `mega-fintrade-quant-engine`
- `mega-fintrade-market-engine-cpp`
- future Mega Fintrade monitoring service

---

## Repository

Repository URL:

    https://github.com/eyebear/mega-fintrade-backend-java

---

## License

MIT License