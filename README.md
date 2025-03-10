# LabResults
* [About](#about)
* * [Microservices architecture pattern](#microservices-architecture-pattern)
* [Technology stack](#technology-stack)
* * [Backend](#backend)
* * [Database](#database)
* * [Testing](#testing)
* * [Async queue](#async-queue)
* * [Monitoring](#monitoring)
* * [CI/CD](#cicd)
* * [Containerization](#containerization)
* [Starting up](#starting-up)
* [Endpoints](#endpoints)

## About
Application supporting a medical laboratory - get your medical test results online.

### Microservices architecture pattern
![image](https://github.com/user-attachments/assets/a8902caf-1044-489c-a30e-5bd2487d7ba3)

## Technology stack
### Backend
* Java 17
* Spring Boot 3.4.0
* Spring Boot Web
* Spring Boot Actuator
* Spring Boot AMQP RabbitMQ
* Spring Data JPA
* Spring Cloud Config
* Spring Cloud OpenFeign
* Spring Cloud Netflix Eureka
* Spring Cloud Gateway 
* Spring Mail
* Springdoc OpenAPI Swagger UI
* ModelMapper
* Lombok

### Database
* PostgreSQL 16.2

### Testing
* JUnit 5
* Mockito

### Async queue
* RabbitMQ 3

### Monitoring
* Prometheus 2.53.3 – collects and stores metrics from microservices.
```
http://localhost:9090
```
* Grafana 11.5.2 – visualizes metrics and provides customizable dashboard.
* * Default credentials: `login: admin` `password: labresults`
```
http://localhost:3000
```

### CI/CD
This repository uses **GitHub Actions** for automatic building and testing on every `push` and `pull request` to the `master` branch.

### Containerization
* Docker
* Multiple containers are configured in the `docker-compose.yml` file:
  - **prometheus**: Prometheus 2.53.3, port 9090
  - **grafana**: Grafana 11.5.2, port 3000
  - **postgres**: PostgreSQL 16.2 database, port 5432
  - **rabbitmq**: RabbitMQ 3 server, port 5672 and 15672
  - **config-server**: Config server, port 8888
  - **eureka-server**: Service registry, port 8761
  - **api-gateway**: API Gateway, port 8080
  - **auth-service**: Auth service, port 8081
  - **sample-service**: Sample service, port 8082
  - **order-service**: Order service, port 8083
  - **customer-service**: Customer service, port 8084
  - **notification-service**: Notification service, port 8085
  - **result-service**: Result service, port 8086

## Starting up
To launch the application, follow the steps:
1. Clone project
```
git clone https://github.com/jchojdak/labresults.git
```
2. Open cloned directory
```
cd labresults
```
3. Start the application using docker-compose
```
docker-compose up -d
```
4. Application address
```
http://localhost:8080
```

## Endpoints

```
http://localhost:8080
```

| #  | Method | Endpoint                                  | Description                        |
|----|--------|-------------------------------------------|------------------------------------|
| 1  | POST   | `/auth/register`                          | User registration                  |
| 2  | POST   | `/auth/login`                             | User login                         |
| 3  | GET    | `/sample/test`                            | Test endpoint for sample service   |
| 4  | POST   | `/sample`                                 | Create a new sample                |
| 5  | DELETE | `/sample/{sampleId}`                      | Delete sample by ID                |
| 6  | GET    | `/sample/{sampleId}`                      | Get sample by ID                   |
| 7  | GET    | `/sample/order/{orderId}`                 | Get samples by order ID            |
| 8  | GET    | `/order/test`                             | Test endpoint for order service    |
| 9  | POST   | `/order/open`                             | Open a new order                   |
| 10 | GET    | `/order/{orderId}`                        | Get order by ID                    |
| 11 | GET    | `/order`                                  | Get all orders                     |
| 12 | GET    | `/customer/test`                          | Test endpoint for customer service |
| 13 | POST   | `/customer`                               | Create a new customer              |
| 14 | GET    | `/customer/{customerId}`                  | Get customer by ID                 |
| 15 | GET    | `/customer`                               | Get all customers                  |
| 16 | GET    | `/result/{orderId}/collect?pesel={pesel}` | Collect the order result           |
