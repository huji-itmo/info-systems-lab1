# Use Gradle builder image
FROM gradle:8.14.3-jdk17-alpine AS builder
WORKDIR /usr/src/

# Copy Gradle files
COPY settings.gradle.kts ./

# Copy source code
COPY app app

# Build the application
RUN gradle war --no-daemon

FROM payara/micro:6.2025.10-jdk17

# Copy the built WAR file from builder stage
COPY --from=builder /usr/src/app/build/libs/*.war /opt/payara/app/app.war

EXPOSE 8080

# Deploy the application on startup
CMD ["--deploy", "/opt/payara/app/app.war", "--port", "8080", "--contextroot", "/"]
