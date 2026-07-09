FROM eclipse-temurin:17-jdk-alpine

VOLUME /tmp

COPY target/socketserver-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]


## Build Java
#mvn clean package -DskipTest
#
## Build Docker Image
#docker build -t socketserver:1.0 .
#
## Run
# kubectl apply -f socketserver.yaml
#
## Push
# kubectl apply -f socketserver-service.yaml