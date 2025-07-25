# 1. Build stage
FROM eclipse-temurin:21-jdk-alpine as builder

WORKDIR /app

COPY . .

# Make the Maven wrapper script executable
RUN chmod +x ./mvnw

# Now run the build
RUN ./mvnw clean package -DskipTests

# 2. Run stage
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=builder /app/target/FleetManagement-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
