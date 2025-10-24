FROM oven/bun:latest AS frontend-builder
WORKDIR /app
COPY frontend/ ./
RUN bun install
RUN bun run build

FROM gradle:8.14.3-jdk17-alpine AS builder
WORKDIR /usr/src/

COPY backend/settings.gradle.kts ./

COPY backend/app app

COPY --from=frontend-builder /app/out/ backend/app/src/main/webapp/

RUN gradle war --no-daemon

FROM payara/micro:6.2025.10-jdk17

COPY --from=builder /usr/src/app/build/libs/*.war /opt/payara/app/app.war


EXPOSE 8080

# Deploy the application on startup
CMD ["--deploy", "/opt/payara/app/app.war", "--port", "8080", "--contextroot", "/"]
