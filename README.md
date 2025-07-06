# Microservices Java Spring

A distributed Quiz application built with Java Spring Boot microservices. Users can browse a catalog of quizzes, answer questions, receive grades, and even create their own quizzes — all through a resilient, Eureka‑backed microservices architecture.

---

## Table of Contents

- [About the Project](#about-the-project)
- [Architecture Overview](#architecture-overview)
- [Microservice Details](#microservice-details)
- [Key Features](#key-features)
- [Technologies Used](#technologies-used)
- [Screenshots](#screenshots)

---

## About the Project

This project demonstrates a Spring Boot microservices ecosystem powering a dynamic Quiz App. It integrates multiple independent services—questions service, quiz service, service registry, and API gateway—to handle all quiz operations, data persistence in two postgreSQL databases “Questions DB” and “Quizzes DB”, and inter‑service communication.

---

## Architecture Overview
```plaintext
                            ┌────────────────────┐
                            │    Client / UI     │
                            └─────────┬──────────┘
                                      │
                                      ▼
                         ┌────────────────────────────┐
                         │        API Gateway         │
                         │ (spring-cloud-gateway)     │
                         └────────────┬───────────────┘
                                      │        
                   Service Discovery  ▼        
                            ┌───────────────────┐
                            │       Eureka      │
                            │ (service-registry)│
                            └────────┬──┬───────┘
                                     │  │
                                     ▼  ▼
                            ┌────────┴──┴─────────────┐
                            │                         │
                  ┌─────────▼────────┐       ┌────────▼─────────┐
                  │   Quiz Service   │ ──▶  │ Question Service │
                  │ (quiz-service)   │ Feign │(question-service)│
                  └────────┬─────────┘       └────────┬─────────┘
                           │                          │
                           ▼                          ▼
                   ┌──────────────┐           ┌─────────────────┐
                   │PostgreSQL DB │           │  PostgreSQL DB  │
                   │   (Quiz)     │           │  (Questions)    │
                   └──────────────┘           └─────────────────┘

```

## Microservice Details

1. Question-service

- Manages all quiz questions: CRUD operations, categories, difficulty levels, possible answers, correct answer.
- Persists data to a PostgreSQL “questions” database.

2. Quiz-service

- Orchestrates quizzes: creates new quizzes, retrieves quiz metadata and questions via Feign client from question-service.
- Provides quiz catalog and grading logic.

3. service-registry

- Runs a Eureka server for dynamic discovery of quiz-service and question-service.

4. api-gateway

- Uses Spring Cloud Gateway to route and load‑balance incoming requests across microservices.
- Manages ports and handles CORS, security, and rate limiting (if extended).

## Key Features

1. Quiz Catalog
- Browse all available quizzes along with metadata.

2. Dynamic Quiz Taking
- Fetch quizzes, answer questions, and receive instant grading.

3. Quiz Creation
- Build and persist your own quizzes (questions, options, correct answers).

4. Resilient Microservices
- Discovery via Eureka; centralized routing via API Gateway.

## Technologies Used
- Java
- Spring Boot
- Spring Data JPA
- Spring Cloud Netflix Eureka (Server/Client)
- Spring Cloud OpenFeign
- Spring Cloud Gateway
- Thymeleaf for server‑side HTML rendering
- HTML & CSS
- Apache Tomcat
- Data base: PostgreSQL

## Screenshots

- Simple user interfaces to be able to interact with the system at web level and not through Tomcat with manual GET and POST messages.
- The content of the Webseite is dynamic, the HTML is in charge of representing the data obtained from the database..

#### Home
![image_alt](https://github.com/jorgepiconjr/spring-quiz-microservices/blob/master/documentation/Home.png)

#### Execute a Quiz 
![image_alt](https://github.com/jorgepiconjr/spring-quiz-microservices/blob/master/documentation/ExecuteRandomQuiz.png)

#### Quiz Catalog
![image_alt](https://github.com/jorgepiconjr/spring-quiz-microservices/blob/master/documentation/QuizCatalog.png)


