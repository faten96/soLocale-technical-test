🧠 Advertising Campaign API

A Spring Boot 3 + Java 21 REST API to manage advertising campaigns and handle high-concurrency impression updates asynchronously using Kafka.

⚙️ Tech Stack

Java 21, Spring Boot 3 (Web MVC)

PostgreSQL / H2 (Flyway migrations)

Kafka (Publisher/Subscriber for async updates)

MapStruct, Lombok, Maven

Swagger (API docs)

📦 Features

CRUD Endpoints: /api/campaigns

Async Impression Update:
POST /api/campaigns/{id}/impression → increments impressions & updates cost via Kafka

🧩 Run with Docker
docker compose up --build


Services:

Zookeeper

Kafka

PostgreSQL

Advertising API (on http://localhost:8080/swagger-ui.html
)

🧰 Run Locally
./mvnw spring-boot:run


(Default: H2 in-memory database, Kafka on localhost:9092)

🧾 Example Request
POST /api/campaigns
{
"name": "Black Friday",
"targetImpression": 100000,
"targetBudget": 5000,
"cpm": 2.5
}

👩‍💻 Author

Faten MAALEM — Backend Developer
(Spring Boot • Kafka • PostgreSQL • Clean Architecture)