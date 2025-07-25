# 1. Build stage - compile the Spring Boot app
FROM eclipse-temurin:21-jdk-alpine as builder

WORKDIR /app

# Copy everything to the container
COPY . .

# Build the application using Maven wrapper (skip tests for faster build)
RUN ./mvnw clean package -DskipTests

# 2. Run stage - only copy the built JAR to keep image small
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/FleetManagement-0.0.1-SNAPSHOT.jar app.jar

# Expose Spring Boot's default port
EXPOSE 8080

# Command to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
