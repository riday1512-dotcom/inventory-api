# Inventory API (Spring Boot)

REST API for small-shop inventory: products, sales, restocking, search, and low-stock alerts.
Built as the third version of my Inventory project (console app, then JDBC, then REST API).

## Tech
Java 21, Spring Boot, Spring Data JPA (Hibernate), MySQL, Bean Validation, Maven

## Endpoints
| Method | Path | Purpose |
|---|---|---|
| POST | /api/products | Create a product |
| GET | /api/products | List all products |
| GET | /api/products/{id} | Get one product |
| GET | /api/products/search?name=par | Search by name |
| GET | /api/products/low-stock?threshold=10 | Low-stock report |
| POST | /api/products/{id}/sell?quantity=5 | Record a sale |
| POST | /api/products/{id}/restock?quantity=20 | Add stock |
| DELETE | /api/products/{id} | Delete a product |

Errors return clear JSON: 400 for invalid input, 404 for a missing product,
409 when a sale exceeds available stock.

## Architecture
Controller (HTTP) -> Service (business rules, transactions) -> Repository (Spring Data JPA) -> MySQL

## Run locally
1. Create a database: `CREATE DATABASE inventory_api;`
2. Set the password (PowerShell): `$env:DB_PASSWORD = "your_password"`
3. Start: `.\mvnw.cmd spring-boot:run`
4. Try: `curl.exe http://localhost:8080/api/products`

The password is read from the `DB_PASSWORD` environment variable and is never stored in the code.

## How I built this
I used Claude as a mentor for explanations and code review, and I wrote, ran, tested, and debugged the project myself.

Interactive API docs (Swagger UI): http://localhost:8080/swagger-ui.html
