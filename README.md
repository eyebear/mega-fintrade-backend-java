# mega-fintrade-backend-java

[![Java CI](https://github.com/eyebear/mega-fintrade-backend-java/actions/workflows/ci.yml/badge.svg)](https://github.com/eyebear/mega-fintrade-backend-java/actions/workflows/ci.yml)

Java Spring Boot backend for the Mega Fintrade Platform.

This service is Project 1 of the Mega Fintrade Platform. It acts as the central backend that stores portfolio positions, quant import results, strategy signals, risk metrics, backtest results, portfolio equity curve data, import audit history, rejected CSV rows, and report summary data.

The backend exposes REST APIs, imports CSV outputs from the Python quantitative engine, supports Spring Batch import execution, runs scheduled backend jobs, records import audit information, logs invalid CSV records, and provides reporting endpoints for future monitoring and dashboard services.

---

## Project Purpose

The purpose of this project is to provide a production-style Java backend for a multi-project financial trading and reporting platform.

In the Mega Fintrade Platform, this backend is responsible for:

- Receiving processed quant output files from the Python quant engine
- Persisting imported risk, strategy, backtest, and equity curve data
- Providing REST APIs for portfolio data and financial reports
- Running scheduled import and report refresh jobs
- Recording audit history for import runs
- Recording rejected CSV rows for debugging and data quality checks
- Providing a stable backend foundation for future dashboard and monitoring services

This project demonstrates backend engineering skills using Java, Spring Boot, REST APIs, JPA, PostgreSQL, Spring Batch, scheduling, testing, CI, Docker, and Docker Compose.

---

## Role in the Mega Fintrade Platform

Mega Fintrade is designed as a multi-language, multi-service financial system.

This repository is:

    Project 1 — Java Portfolio Risk Reporting and ETL Platform

Its role is to act as the central backend.

Related projects:

| Project | Repository / Purpose |
|---|---|
| Project 1 | Java backend for storage, import, reporting, scheduling, and APIs |
| Project 2 | Python quantitative backtesting and analytics engine |
| Project 3 | C++ market data processing engine |
| Project 4 | C#/.NET monitoring and alerting service |
| Project 5 | Future AI advisor and decision-support service |

Intended data flow:

    Project 3 C++ market engine
      ↓
    Project 2 Python quant engine
      ↓
    Project 1 Java backend
      ↓
    Project 4 monitoring / dashboard service
      ↓
    Future Project 5 AI advisor

At the current stage, Project 1 consumes CSV files produced by Project 2.

---

## Tech Stack

- Java 17
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- Hibernate
- Spring Batch
- Spring Scheduling
- PostgreSQL
- H2 in-memory database for tests
- Maven Wrapper
- JUnit
- AssertJ
- MockMvc
- Docker
- Docker Compose
- GitHub Actions CI

---

## Project Structure

    src/main/java/com/ao/portfolio
    ├── PortfolioRiskPlatformApplication.java
    ├── config
    ├── controller
    ├── dto
    ├── entity
    ├── exception
    ├── repository
    ├── scheduler
    └── service

Main responsibility of each package:

| Package | Purpose |
|---|---|
| `controller` | Exposes REST API endpoints |
| `service` | Contains business logic and import logic |
| `repository` | Handles database access through Spring Data JPA |
| `entity` | Defines database-backed domain models |
| `dto` | Defines API request and response objects |
| `config` | Contains Spring and batch configuration |
| `scheduler` | Contains scheduled backend jobs |
| `exception` | Contains application-specific exception handling |

---

## Main Features

The backend currently supports:

- Portfolio position management
- Position profit and loss calculation
- Batch position operations
- Quant CSV import
- Spring Batch import execution
- Scheduled quant import job
- Scheduled report recalculation job
- Import audit history
- Rejected CSV record logging
- Report summary API
- Repository, service, controller, and batch tests
- H2-based CI test configuration
- Docker runtime packaging
- Docker Compose setup with PostgreSQL

---

## Quant CSV Input Files

The backend imports CSV files from:

    data/input

Expected files:

    data/input/risk_metrics.csv
    data/input/backtest_results.csv
    data/input/strategy_signals.csv
    data/input/portfolio_equity_curve.csv

These files are expected to come from:

    mega-fintrade-quant-engine

The current strategy signal import is scoped to the configured Project 2 symbol universe.

Dynamic symbol support is intentionally left for a future version.

For detailed CSV format documentation, see:

    docs/csv-input-contract.md

---

## REST API Documentation

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

## Postman Testing

For detailed Postman examples, see:

    docs/api-postman-examples.md

Common test requests:

| Purpose | Method | URL |
|---|---|---|
| View report summary | GET | `http://localhost:8080/api/reports/summary` |
| Import all quant CSV files | POST | `http://localhost:8080/api/import/all` |
| View import audit history | GET | `http://localhost:8080/api/import/audit` |
| View rejected CSV records | GET | `http://localhost:8080/api/import/rejections` |
| Run Spring Batch import | POST | `http://localhost:8080/api/batch/run` |
| View positions | GET | `http://localhost:8080/api/positions` |
| Create position | POST | `http://localhost:8080/api/positions` |

For import endpoints, use:

    Body: none

---

## Running the Application Locally with Maven Wrapper

### Requirements

- Java 17
- PostgreSQL running locally
- Database named `portfolio_db`
- Database username and password matching `src/main/resources/application.properties`

### Start the application

From the project root:

    ./mvnw spring-boot:run

The backend runs on:

    http://localhost:8080

Example endpoint:

    http://localhost:8080/api/reports/summary

---

## Maven Wrapper Commands

Run all tests:

    ./mvnw test

Run a clean test suite:

    ./mvnw clean test

Package the application:

    ./mvnw clean package

Run the application:

    ./mvnw spring-boot:run

The project uses Maven Wrapper.

Use `./mvnw` instead of relying on a globally installed Maven version.

---

## Testing

The project includes tests for:

- Repository and entity mappings
- CSV import services
- CSV validation and rejection logging
- Report summary service
- REST controllers
- Batch runner service
- Spring Boot application context

Tests use H2 in-memory database through:

    src/test/resources/application.properties

The test configuration disables scheduled jobs to avoid background imports interfering with automated tests.

Run tests:

    ./mvnw clean test

---

## Docker Runtime

This project supports Docker-based runtime packaging.

Docker Compose starts both the Spring Boot backend and a PostgreSQL database.

### Docker Services

| Service | Description | Host URL / Port |
|---|---|---|
| Backend | Spring Boot Java backend | `http://localhost:8080` |
| PostgreSQL | Dockerized PostgreSQL database | `localhost:5433` |

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

## Eclipse Setup

For Eclipse setup instructions, see:

    docs/eclipse-setup.md

Summary:

1. Open Eclipse.
2. Import the project as an existing Maven project.
3. Confirm that `pom.xml` is detected.
4. Make sure Java 17 is configured.
5. Run `PortfolioRiskPlatformApplication.java` as a Spring Boot application.

---

## VS Code Setup

The project can also be opened directly in VS Code.

Recommended VS Code extensions:

- Extension Pack for Java
- Spring Boot Extension Pack
- Docker
- GitLens

Open the project:

    code .

Run tests:

    ./mvnw clean test

Run the application:

    ./mvnw spring-boot:run

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

## Future Improvements

Planned future improvements:

- Normalize strategy signals into one row per date and symbol
- Support dynamic stock universes from Project 2
- Add pagination to audit and rejection endpoints
- Add cleaner API error responses for failed imports
- Add authentication and role-based access control
- Add OpenAPI / Swagger documentation
- Add production deployment configuration
- Add monitoring service integration
- Add frontend dashboard integration

---

## Repository

Repository URL:

    https://github.com/eyebear/mega-fintrade-backend-java

---

## Author

Developed by Ao Ao Feng.

This project is part of the Mega Fintrade Platform portfolio, a multi-service financial data engineering, quantitative analytics, backend ETL, and risk reporting system built with Java, Python, C++, and C#/.NET.

Repository:

    https://github.com/eyebear/mega-fintrade-backend-java

---

## License

MIT License