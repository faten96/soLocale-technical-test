**Advertising Campaign Service**

A Spring Boot microservice to manage advertising campaigns — create, update, track impressions, and delete campaigns.
It uses PostgreSQL for persistence, Kafka for events, and Flyway for database migrations.

**Tech Stack**

- Java 21 · Spring Boot 3
- PostgreSQL · Flyway
- Apache Kafka
- Docker & Docker Compose
- Maven · JUnit 5

**Run Locally with Docker Compose**

1️⃣ Build the JAR

mvn clean package -DskipTests

2️⃣ Start all services

docker compose up --build


Services started:

1. Service	Port
2. App (API)	8080
3. PostgreSQL	5432
4. Kafka	9092

3️⃣ Access

API: http://localhost:8080

Swagger: http://localhost:8080/swagger-ui/index.html

**Run Tests**
mvn test

**Stop & Clean**
docker compose down -v
