# Stage 1: Build
FROM maven:3.9.0-eclipse-temurin-17 AS builder
WORKDIR /workspace
COPY pom.xml .
COPY src ./src

# IMPORTANT: repackage to create Spring Boot executable JAR
RUN mvn -B -DskipTests package spring-boot:repackage

# Stage 2: Runtime
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy any jar from target (your jar name may differ)
COPY --from=builder /workspace/target/*.jar app.jar

EXPOSE 3000
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
