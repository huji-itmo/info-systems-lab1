# Use Gradle builder image
FROM gradle:8.7-jdk17 AS builder
WORKDIR /app

# Copy Gradle files
COPY gradle gradle
COPY gradlew .
COPY build.gradle.kts settings.gradle.kts .

# Copy source code
COPY src src

# Build the application
RUN gradle build -x test --no-daemon

# Use JRE runtime image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy built JAR from builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose port (adjust to your app's port)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
