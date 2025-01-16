# STAGE 1: build
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY customer-service/ .
RUN mvn clean package -DskipTests

# STAGE 2: run
FROM openjdk:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar ./app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]