# Stage 1: Build the application
FROM maven:3.8.8-eclipse-temurin-17-alpine AS builder
WORKDIR /app
COPY pom.xml .
# Pre-download dependencies to cache them
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime container
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY --from=builder /app/target/socketserver-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]


## Build Docker Image
#docker build -t socketserver:1.0 .
#
## Run
# kubectl apply -f socketserver.yaml
#
## Push
# kubectl apply -f socketserver-service.yaml