# Use a lightweight JDK image
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (cached)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the app
RUN ./mvnw clean package -DskipTests

# Run the JAR
ENTRYPOINT ["java", "-jar", "target/AdvertisingCompagn-0.0.1-SNAPSHOT.jar"]
