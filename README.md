# mega-fintrade-backend-java

Java Spring Boot backend for the Mega Fintrade Platform. This service stores portfolio positions, market data, backtest results, and risk reports, and exposes REST APIs for reporting and monitoring.

This project demonstrates a production-style backend architecture with REST APIs, database integration, containerization, and CI automation.

---

## Overview

The system allows users to:

* Store stock positions
* Retrieve portfolio data
* Calculate profit and loss (PnL)
* Perform batch operations
* Validate input data

Designed as a **portfolio-ready backend project** showcasing real-world engineering practices.

---

## Tech Stack

* Java 21
* Spring Boot
* Spring Web (REST API)
* Spring Data JPA
* PostgreSQL (production database)
* H2 (test database)
* Maven
* Docker & Docker Compose
* GitHub Actions (CI)

---

## Project Structure

```
src/main/java/com/ao/portfolio
├── controller        # REST endpoints
├── service           # business logic
├── repository        # database access
├── entity            # JPA entities
├── dto               # request/response models
├── exception         # custom exceptions
```

---

## API Endpoints

### 1. Get all positions

```
GET /positions
```

---

### 2. Create a position

```
POST /positions
```

Request body:

```json
{
  "symbol": "AAPL",
  "quantity": 10,
  "avgPrice": 150
}
```

---

### 3. Batch create positions

```
POST /positions/batch
```

```json
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
```

---

### 4. Get position by ID

```
GET /positions/{id}
```

---

### 5. Calculate PnL

```
GET /positions/{id}/pnl/{price}
```

Example:

```
GET /positions/1/pnl/160
```

---

## Running the Application

### Option 1 — Run locally (without Docker)

1. Start PostgreSQL
2. Configure database in `application.properties`
3. Run:

```bash
mvn spring-boot:run
```

---

### Option 2 — Run with Docker Compose (recommended)

```bash
docker compose up --build
```

Then access:

```
http://localhost:8080/positions
```

---

## Running Tests

```bash
mvn test
```

Tests use **H2 in-memory database**, so no PostgreSQL setup is required.

---

## CI (Continuous Integration)

This project uses GitHub Actions.

On every push:

* Project builds automatically
* Tests run automatically

CI status is visible under the **Actions** tab.

---

## Key Features

* RESTful API design
* Layered architecture (Controller → Service → Repository)
* DTO pattern (separating API and database models)
* Input validation
* Exception handling
* Database persistence (PostgreSQL)
* In-memory testing (H2)
* Dockerized deployment
* Multi-container setup (Docker Compose)
* Automated CI pipeline

---

# mega-fintrade-backend-java