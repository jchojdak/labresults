# 💉 LabResults

> [!NOTE]
> This application is not `business oriented` and my focus is mostly on technical part, I just want to implement a sample with using microservices architecture pattern.

> [!WARNING]  
> This project is in progress.


* [About](#-about)
* * [Microservices architecture pattern](#-microservices-architecture-pattern)
* [Technology stack](#-technology-stack)
* * [Backend](#-backend)
* * [Database](#-database)
* * [Testing](#-testing)
* * [Async queue](#-async-queue)
* * [Auth](#-auth)
* * [Monitoring](#-monitoring)
* * [CI/CD](#-cicd)
* * [Containerization](#-containerization)
* * [Orchestration](#-orchestration)
* [API documentation](#-api-documentation)
* * [Swagger UI](#-swagger-ui)
* * [Postman](#-postman)
* [Starting up](#-starting-up)
* [Endpoints](#-endpoints)

## 📌 About
LabResults is a REST API application that supports private medical laboratory. Laboratory employees can open orders, enter customer data into the system, add new samples and share results online. Customers can collect their results by providing a valid order ID, PESEL number or date of birth.

### 🔗 Microservices architecture pattern
![image](https://github.com/user-attachments/assets/a8902caf-1044-489c-a30e-5bd2487d7ba3)

## 🔨 Technology stack
### 💻 Backend
* Java 17
* Spring Boot 3.4.0
* * Spring Boot Actuator
* * Spring Boot AMQP (RabbitMQ)
* Spring Web
* Spring Data JPA
* Spring Cloud 2024.0.0
* * Spring Cloud Config
* * Spring Cloud OpenFeign
* * Spring Cloud Netflix Eureka
* * Spring Cloud Gateway 
* Spring Security 6.4
* * Spring Security OAuth2 Resource Server
* Spring Mail
* Springdoc OpenAPI Swagger UI
* ModelMapper
* Lombok

### 🐘 Database
* PostgreSQL 16.2

### 🧪 Testing
* JUnit 5
* Mockito

### 🐇 Async queue
* RabbitMQ 3

```
http://localhost:5672
```

* Default management credentials: `username: user` `password: password`

```
http://localhost:15672
```

### 🔑 Auth
* Keycloak 26.1.3 - manages user authentication and authorization.
* Groups and global realm roles:
  * ADMIN - has access to all endpoints
  * LAB_TECHNICIAN
  * RECEPTIONIST
* Default admin console credentials: `username: admin` `password: password`

```
http://localhost:8081/admin/labresults/console/
```

### 📊 Monitoring
* Prometheus 2.53.3 – collects and stores metrics from microservices.
```
http://localhost:9090
```
* Grafana 11.5.2 – visualizes metrics and provides customizable dashboard.
* * Default credentials: `username: admin` `password: labresults`
```
http://localhost:3000
```

### 🔁 CI/CD
This repository uses **GitHub Actions** for automatic building and testing on every `push` and `pull request` to the `master` branch.

### 📦 Containerization
* Docker
* Multiple containers are configured in the `infra/docker/docker-compose.yml` file:
  - **prometheus**: Prometheus 2.53.3, port 9090
  - **grafana**: Grafana 11.5.2, port 3000
  - **postgres**: PostgreSQL 16.2 database, port 5432
  - **rabbitmq**: RabbitMQ 3 server, port 5672 and 15672
  - **config-server**: Config server, port 8888
  - **eureka-server**: Service registry, port 8761
  - **api-gateway**: API Gateway, port 8080
  - **keycloak**: Keycloak 26.1.3 server, port 8081
  - **sample-service**: Sample service, port 8082
  - **order-service**: Order service, port 8083
  - **customer-service**: Customer service, port 8084
  - **notification-service**: Notification service, port 8085
  - **result-service**: Result service, port 8086

### 📦 Orchestration
* Kubernetes (Minikube) - orchestrates the deployment of microservices in a local environment.

## 📄 API documentation
### 📍 Swagger UI

The application generates interactive API documentation Swagger UI (SpringDoc OpenAPI).
```
http://localhost:8080/swagger-ui.html
```

### 📍 Postman

Collection to import:
```
https://github.com/jchojdak/labresults/blob/master/postman/labresults.postman_collection.json
```

## 🚀 Starting up
To launch the application, follow the steps:
1. Clone project
```
git clone https://github.com/jchojdak/labresults.git
```
2. Open cloned directory
```
cd labresults
```
3. Start the application using bash script and Docker Compose:
```
./start.sh docker
```
or Kubernetes (Minikube):
```
./start.sh k8s

minikube service api-gateway -n labresults
```
4. Application address
```
http://localhost:8080
```

## 📜 Endpoints

```
http://localhost:8080
```

| #  | Method | Endpoint                                  | Description                        | Roles allowed                       |
|----|--------|-------------------------------------------|------------------------------------|-------------------------------------|
| 1  | POST   | `/auth/register`                          | User registration                  | Public                              |
| 2  | POST   | `/auth/login`                             | User login                         | Public                              |
| 3  | GET    | `/sample/test`                            | Test endpoint for sample service   | Public                              |
| 4  | POST   | `/sample`                                 | Create a new sample                | ADMIN, RECEPTIONIST                 |
| 5  | DELETE | `/sample/{sampleId}`                      | Delete sample by ID                | ADMIN                               |
| 6  | GET    | `/sample/{sampleId}`                      | Get sample by ID                   | ADMIN, RECEPTIONIST, LAB_TECHNICIAN |
| 7  | GET    | `/sample/order/{orderId}`                 | Get samples by order ID            | ADMIN, RECEPTIONIST, LAB_TECHNICIAN | 
| 8  | GET    | `/order/test`                             | Test endpoint for order service    | Public                              |
| 9  | POST   | `/order/open`                             | Open a new order                   | ADMIN, RECEPTIONIST                 |
| 10 | GET    | `/order/{orderId}`                        | Get order by ID                    | ADMIN, RECEPTIONIST                 |
| 11 | GET    | `/order/{orderId}/status`                 | Get order status by ID             | ADMIN, RECEPTIONIST, LAB_TECHNICIAN |
| 12 | PATCH  | `/order/{orderId}/status`                 | Update order status by ID          | ADMIN, RECEPTIONIST, LAB_TECHNICIAN |
| 13 | GET    | `/order`                                  | Get all orders                     | ADMIN, RECEPTIONIST                 |
| 14 | GET    | `/customer/test`                          | Test endpoint for customer service | Public                              |
| 15 | POST   | `/customer`                               | Create a new customer              | ADMIN, RECEPTIONIST                 |
| 16 | GET    | `/customer/{customerId}`                  | Get customer by ID                 | ADMIN, RECEPTIONIST                 |
| 17 | GET    | `/customer`                               | Get all customers                  | ADMIN, RECEPTIONIST                 |
| 18 | GET    | `/result/{orderId}/collect?pesel={pesel}` | Collect the order result           | Public                              |
