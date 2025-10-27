# Advertising Campaign

A Spring Boot microservice to manage advertising campaigns — create, update, track impressions, and delete campaigns.
It uses PostgreSQL for persistence, Kafka for events, and Flyway for database migrations.

## Tech Stack

- Java 21 · Spring Boot 3
- PostgreSQL · Flyway
- Apache Kafka
- Docker & Docker Compose
- Maven · JUnit 5

**Run Locally with Docker Compose**

1️⃣ Build the JAR
```
mvn clean package -DskipTests
```

2️⃣ Start all services
```
docker compose up --build
```

Services started:

1. Service	Port
2. App (API)	8080
3. PostgreSQL	5432
4. Kafka	9092

3️⃣ Access

API: http://localhost:8080

Swagger: http://localhost:8080/swagger-ui/index.html

**Run Tests**
```
mvn test
```

**Stop & Clean**
```
docker compose down -v
```

# Arithmetic Expression Calculator

This application also includes a Calculator API that evaluates mathematical expressions with nested parentheses and multiple bracket types.

How It Works : 

The calculator is implemented using a variation of the Shunting Yard Algorithm, which ensures:

- Operator precedence is respected (* and / before + and -)
- Nested and mixed brackets are supported () [] {}
- Invalid inputs and division by zero are safely handled
- Efficient computation using internal stacks (Deque)

Example : 
Valid expression 

```
{
  "expression": "{[(1*2+(3/4)/(7*3))]+[5*(6-2)]}"
}
```
Response
```
{
  "result": 20.0143
}
```

Invalid expression 
```
{
  "expression": "5 + (3 / (2 - 2))"
}
```
Response
```
{
  "error": "Math error: Division by zero"
}
```

API Endpoint 
| Method | Endpoint                 | Description                       |
| ------ | ------------------------ | --------------------------------- |
| POST   | /api/calculator/evaluate | Evaluate an arithmetic expression |

Example cURL
```
curl -X POST "http://localhost:8080/api/calculator/evaluate" \
  -H "Content-Type: application/json" \
  -d "\"{[(1*2+(3/4)/(7*3))]+[5*(6-2)]}\""
```


Run Tests
```
mvn test
```


👩‍💻 Author

Faten MAALEM — Backend Developer
(Spring Boot • Kafka • PostgreSQL • Clean Architecture)
