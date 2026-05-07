# API and Postman Examples

This document provides Postman-ready examples for testing the `mega-fintrade-backend-java` service.

The default local and Docker backend URL is:

    http://localhost:8080

Unless otherwise noted, requests do not require authentication in the current project scope.

---

## 1. Report Summary API

### Purpose

Use this endpoint to confirm that the backend is running and that report summary data can be queried.

### Request

Method:

    GET

URL:

    http://localhost:8080/api/reports/summary

Body:

    none

### Expected Response Example

    {
      "riskMetricRowCount": 4,
      "backtestResultRowCount": 2,
      "strategySignalRowCount": 2,
      "portfolioEquityPointRowCount": 2,
      "latestEquityDate": "2026-01-03",
      "latestPortfolioWealth": 1.0049,
      "latestBenchmarkWealth": 1.0040,
      "portfolioCumulativeReturn": 0.0049,
      "benchmarkCumulativeReturn": 0.0040,
      "portfolioSharpeRatio": 1.2500,
      "portfolioMaxDrawdown": -0.1800
    }

If no CSV data has been imported yet, the row counts may be `0` and calculated fields may be `null`.

---

## 2. Import All Quant CSV Files

### Purpose

Use this endpoint to manually import all default quant output CSV files from:

    data/input

Expected files:

    data/input/risk_metrics.csv
    data/input/backtest_results.csv
    data/input/strategy_signals.csv
    data/input/portfolio_equity_curve.csv

### Request

Method:

    POST

URL:

    http://localhost:8080/api/import/all

Body:

    none

### Expected Successful Response Example

    {
      "riskMetricRowsImported": 4,
      "backtestResultRowsImported": 2,
      "strategySignalRowsImported": 2,
      "portfolioEquityRowsImported": 2
    }

Exact field names may depend on the current DTO implementation, but the response should show how many rows were imported from each file.

### Common Failure

If a CSV file is missing, the import may fail with an error similar to:

    CSV file not found: data/input/risk_metrics.csv

If a CSV header is invalid, the import may fail with an error similar to:

    Invalid CSV header

If a CSV row has the wrong number of columns, the import may fail with an error similar to:

    Invalid column count

Rejected rows can be viewed through:

    GET http://localhost:8080/api/import/rejections

---

## 3. Import Audit History

### Purpose

Use this endpoint to view recent import runs.

The audit table records scheduled import runs and whether they succeeded or failed.

### Request

Method:

    GET

URL:

    http://localhost:8080/api/import/audit

Body:

    none

### Expected Response Example

    [
      {
        "id": 1,
        "jobName": "SCHEDULED_QUANT_IMPORT",
        "status": "SUCCESS",
        "startedAt": "2026-05-07T06:20:55.956299",
        "completedAt": "2026-05-07T06:20:56.000983",
        "riskMetricRows": 4,
        "backtestResultRows": 2,
        "strategySignalRows": 2,
        "portfolioEquityRows": 2,
        "errorMessage": null
      }
    ]

### Notes

If the latest import failed, the response may include:

    "status": "FAILED"

and an error message such as:

    "errorMessage": "Invalid CSV header"

---

## 4. Import Rejections

### Purpose

Use this endpoint to view rejected CSV records.

Rejected rows are created when the CSV validation service detects invalid files, invalid headers, invalid dates, invalid decimals, or invalid column counts.

### Request

Method:

    GET

URL:

    http://localhost:8080/api/import/rejections

Body:

    none

### Expected Response Example

    [
      {
        "id": 1,
        "fileName": "strategy_signals.csv",
        "lineNumber": 1,
        "rawRecord": "date,aapl_close,...",
        "reason": "Invalid CSV header",
        "createdAt": "2026-05-07T06:22:26.897112"
      }
    ]

If there are no rejected records, the response should be:

    []

---

## 5. Run Spring Batch Import

### Purpose

Use this endpoint to trigger the Spring Batch-based import runner.

This is separate from the direct import endpoint.

### Request

Method:

    POST

URL:

    http://localhost:8080/api/batch/run

Body:

    none

### Expected Response Example

    {
      "status": "COMPLETED",
      "startedAt": "2026-05-07T06:30:00.123456",
      "finishedAt": "2026-05-07T06:30:01.123456",
      "riskMetricJobStatus": "COMPLETED",
      "backtestResultJobStatus": "COMPLETED",
      "strategySignalJobStatus": "COMPLETED",
      "portfolioEquityJobStatus": "COMPLETED"
    }

If one of the files is missing or invalid, one or more job statuses may show failure depending on the current batch implementation.

---

## 6. Get All Positions

### Purpose

Use this endpoint to view saved portfolio positions.

### Request

Method:

    GET

URL:

    http://localhost:8080/api/positions

Body:

    none

### Expected Response Example

    [
      {
        "id": 1,
        "symbol": "AAPL",
        "quantity": 10,
        "avgPrice": 150.00
      }
    ]

If no positions exist, the response may be:

    []

---

## 7. Create One Position

### Purpose

Use this endpoint to create a new portfolio position.

### Request

Method:

    POST

URL:

    http://localhost:8080/api/positions

Header:

    Content-Type: application/json

Body:

    {
      "symbol": "AAPL",
      "quantity": 10,
      "avgPrice": 150
    }

### Expected Response Example

    {
      "id": 1,
      "symbol": "AAPL",
      "quantity": 10,
      "avgPrice": 150
    }

---

## 8. Create Positions in Batch

### Purpose

Use this endpoint to create multiple positions in one request.

### Request

Method:

    POST

URL:

    http://localhost:8080/api/positions/batch

Header:

    Content-Type: application/json

Body:

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

### Expected Response Example

    [
      {
        "id": 1,
        "symbol": "MSFT",
        "quantity": 5,
        "avgPrice": 300
      },
      {
        "id": 2,
        "symbol": "GOOGL",
        "quantity": 3,
        "avgPrice": 120
      }
    ]

---

## 9. Get Position by ID

### Purpose

Use this endpoint to retrieve one position by database ID.

### Request

Method:

    GET

URL:

    http://localhost:8080/api/positions/1

Body:

    none

### Expected Response Example

    {
      "id": 1,
      "symbol": "AAPL",
      "quantity": 10,
      "avgPrice": 150
    }

---

## 10. Calculate Position Profit and Loss

### Purpose

Use this endpoint to calculate unrealized profit and loss for a position using a supplied current market price.

### Request

Method:

    GET

URL:

    http://localhost:8080/api/positions/1/pnl/160

Body:

    none

### Expected Response Example

    {
      "positionId": 1,
      "symbol": "AAPL",
      "quantity": 10,
      "avgPrice": 150,
      "currentPrice": 160,
      "profitLoss": 100
    }

Exact response field names may depend on the current DTO implementation.

---

## Recommended Postman Testing Order

Use this order for a clean manual test:

1. Start the backend.
2. Confirm health through report summary:

       GET http://localhost:8080/api/reports/summary

3. Create a sample position:

       POST http://localhost:8080/api/positions

4. Confirm positions:

       GET http://localhost:8080/api/positions

5. Confirm CSV files exist in `data/input`.
6. Import all quant CSV files:

       POST http://localhost:8080/api/import/all

7. Check audit history:

       GET http://localhost:8080/api/import/audit

8. Check rejected rows:

       GET http://localhost:8080/api/import/rejections

9. Check report summary again:

       GET http://localhost:8080/api/reports/summary

---

## Docker Postman Testing Notes

When running with Docker Compose, use the same API base URL:

    http://localhost:8080

The backend container reads CSV files through this Docker Compose volume mount:

    ./data:/app/data

Make sure the local `data/input` folder contains the expected CSV files before triggering imports.