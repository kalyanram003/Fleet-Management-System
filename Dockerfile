# 1. Base image with JDK 21
FROM eclipse-temurin:21-jdk-alpine

# 2. Set the working directory inside the container
WORKDIR /app

# 3. Copy the Maven build output (the fat JAR) into the container
COPY target/FleetManagement-0.0.1-SNAPSHOT.jar app.jar

# 4. Expose port 8080 (Spring Boot default)
EXPOSE 8080

# 5. Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]