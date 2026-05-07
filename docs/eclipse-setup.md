# Eclipse Setup Guide

This document explains how to open and run `mega-fintrade-backend-java` in Eclipse.

The project is a Maven-based Spring Boot application using Java 17.

---

## Requirements

Install the following before opening the project:

- Eclipse IDE for Enterprise Java and Web Developers
- Java 17 JDK
- Git
- PostgreSQL for local runtime
- Docker Desktop if using Docker runtime

Recommended Java version:

    Java 17

---

## 1. Clone the Repository

Clone the repository:

    git clone https://github.com/eyebear/mega-fintrade-backend-java.git

Go into the project folder:

    cd mega-fintrade-backend-java

---

## 2. Import the Project into Eclipse

Open Eclipse.

Choose:

    File → Import

Then choose:

    Maven → Existing Maven Projects

Click:

    Next

For root directory, select the project folder:

    mega-fintrade-backend-java

Eclipse should detect:

    pom.xml

Click:

    Finish

Eclipse will import the Maven project and download dependencies.

---

## 3. Confirm Java 17

In Eclipse, right-click the project.

Choose:

    Properties

Then check:

    Java Build Path
    Java Compiler

Make sure the compiler compliance level is:

    17

If Java 17 is not available, add it through:

    Eclipse → Settings → Java → Installed JREs

or on some systems:

    Eclipse → Preferences → Java → Installed JREs

Add your Java 17 JDK and select it as the active JRE.

---

## 4. Confirm Maven Dependencies

After import, Eclipse should download dependencies automatically.

If there are Maven errors, right-click the project and choose:

    Maven → Update Project

Then select the project and click:

    OK

This refreshes Maven dependencies.

---

## 5. Configure Local PostgreSQL

For local Eclipse runtime, the project uses:

    src/main/resources/application.properties

Make sure PostgreSQL is running locally and the database settings match that file.

Typical local configuration:

    spring.datasource.url=jdbc:postgresql://localhost:5432/portfolio_db
    spring.datasource.username=portfolio_user
    spring.datasource.password=portfolio_password

Create the database if needed:

    portfolio_db

Make sure the database user and password match your local PostgreSQL setup.

---

## 6. Run the Application in Eclipse

Open this file:

    src/main/java/com/ao/portfolio/PortfolioRiskPlatformApplication.java

Right-click the file and choose:

    Run As → Java Application

or if Spring Tools are installed:

    Run As → Spring Boot App

The application should start on:

    http://localhost:8080

Test the backend:

    GET http://localhost:8080/api/reports/summary

---

## 7. Run Tests in Eclipse

Right-click the project.

Choose:

    Run As → Maven test

or run from Eclipse terminal:

    ./mvnw clean test

The test environment uses:

    src/test/resources/application.properties

Tests use H2 in-memory database, not local PostgreSQL.

---

## 8. Maven Wrapper Commands

From the project root, use these commands:

Run tests:

    ./mvnw clean test

Run the application:

    ./mvnw spring-boot:run

Package the application:

    ./mvnw clean package

The project uses Maven Wrapper. Use `./mvnw` instead of relying on a globally installed Maven version.

---

## 9. Docker Runtime from Eclipse Workspace

If you open a terminal in the project root, you can run Docker Compose:

    docker compose up --build

This starts:

- PostgreSQL container
- Spring Boot backend container

Backend URL:

    http://localhost:8080

PostgreSQL host port:

    localhost:5433

Stop containers:

    docker compose down

Reset Docker database:

    docker compose down -v

---

## 10. Common Eclipse Issues

### Issue: Maven dependencies are not resolved

Fix:

    Right-click project → Maven → Update Project

### Issue: Java version mismatch

Fix:

    Configure Java 17 in Eclipse Installed JREs

Then update the project compiler compliance level to 17.

### Issue: Application cannot connect to PostgreSQL

Check:

- PostgreSQL is running.
- Database `portfolio_db` exists.
- Username and password match `application.properties`.
- PostgreSQL port is correct.

### Issue: Tests fail because of database connection

Tests should use H2, not PostgreSQL.

Check that this file exists:

    src/test/resources/application.properties

It should define the H2 test database.

### Issue: Docker backend cannot find CSV files

Check that the local data folder exists:

    data/input

Expected files:

    data/input/risk_metrics.csv
    data/input/backtest_results.csv
    data/input/strategy_signals.csv
    data/input/portfolio_equity_curve.csv

Docker Compose mounts:

    ./data:/app/data

---

## 11. Recommended Eclipse Workflow

Use this workflow:

1. Pull latest code from GitHub.
2. Run Maven update in Eclipse.
3. Run tests:

       ./mvnw clean test

4. Start the app locally:

       ./mvnw spring-boot:run

5. Test with Postman:

       GET http://localhost:8080/api/reports/summary

6. Commit and push changes only after tests pass.

---

## 12. Notes

Although this project can be opened in Eclipse, it can also be developed in VS Code.

The current preferred terminal commands are the same in both IDEs:

    ./mvnw clean test
    ./mvnw spring-boot:run
    docker compose up --build