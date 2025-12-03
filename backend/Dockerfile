# Stage 1: build
FROM maven:3.9.0-eclipse-temurin-17 as builder
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn -B -DskipTests package

# Stage 2: runtime
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /workspace/target/devops-backend-1.0.0.jar /app/app.jar
EXPOSE 3000
ENTRYPOINT ["java","-jar","/app/app.jar"]

