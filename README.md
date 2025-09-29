# Microservices Java Spring - _Full Stack Project_

A distributed Quiz application built with Java Spring Boot microservices, PostgreSQL data base and HTML. Users can browse a catalog of quizzes, answer questions, receive grades, and even create their own quizzes. Implementation of Create, read, update and delete (CRUD) — all through a resilient, Eureka‑backed microservices architecture.

## Table of Contents

- [About the Project](#about-the-project)
- [Technologies Used](#technologies-used)
- [Architecture Overview](#architecture-overview)
- [Microservice Details](#microservice-details)
- [Key Features](#key-features)
- [Example](#example)
- [Screenshots](#screenshots)

---

## About the Project

This project demonstrates a Spring Boot microservices ecosystem powering a dynamic Quiz App. It integrates multiple independent services—questions service, quiz service, service registry, and API gateway—to handle all quiz operations, data persistence in two postgreSQL databases “Questions DB” and “Quizzes DB”, and inter‑service communication.

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

- Manages all quiz questions: CREATE, READ, UPDATE, DELETE (CRUD) operations for **questions**, categories, difficulty levels, possible answers, correct answer.
- Persists data to a PostgreSQL “questions” database.

2. Quiz-service

- Orchestrates quizzes: CREATE, READ, UPDATE, DELETE (CRUD) operations for **quizzes**, creates new quizzes, retrieves quiz metadata and questions via Feign client from question-service.
- Provides quiz catalog and grading logic.

3. service-registry

- Runs a Eureka server for dynamic discovery of quiz-service and question-service.

4. api-gateway

- Uses Spring Cloud Gateway to route and load‑balance incoming requests across microservices.
- Manages ports and handles CORS, security, and rate limiting (if extended).

## Key Features

1. Quiz Catalog
- Browse all available quizzes along with metadata (respective questions).

2. Dynamic Quiz Taking
- Fetch quizzes, load and answer questions, and receive instant grading.

3. Quiz Creation
- Build and persist your own quizzes (questions, options, correct answers).

4. Resilient Microservices
- Discovery via Eureka; centralized routing via API Gateway.

## Example

_Flow of a Typical Request (e.g., Creating a Quiz)_

1.  A user (via an app) sends a request to create a quiz to the **API-GATEWAY**.
2.  The **API-GATEWAY** sees the request's URL (e.g., `/quiz/create`) and knows it must forward it to the **QUIZ-SERVICE**.
3.  The **QUIZ-SERVICE** receives the request. To create the quiz, it needs 10 questions from the "Java" category.
4.  The **QUIZ-SERVICE** asks the **EUREKA-SERVER**: "Where is the **QUESTION-SERVICE**?"
5.  **Eureka** responds with the address.
6.  The **QUIZ-SERVICE** makes a call (using OpenFeign) to the **QUESTION-SERVICE** to request the questions.
7.  The **QUESTION-SERVICE** queries its database (PostgreSQL), retrieves the questions, and returns them to the **QUIZ-SERVICE**.
8.  The **QUIZ-SERVICE** creates the quiz with the received questions and returns the final response to the **API-GATEWAY**.
9.  The **API-GATEWAY** finally sends the response back to the user.

## Screenshots

- Simple user interfaces to be able to interact with the system at web level and not through Tomcat with manual GET and POST messages.
- The content of the Webseite is dynamic, the HTML is in charge of representing the data obtained from the database..

#### Home
![image_alt](https://github.com/jorgepiconjr/spring-quiz-microservices/blob/master/documentation/Home.png)

#### Execute a Quiz 
![image_alt](https://github.com/jorgepiconjr/spring-quiz-microservices/blob/master/documentation/ExecuteRandomQuiz.png)

#### Quiz Catalog
![image_alt](https://github.com/jorgepiconjr/spring-quiz-microservices/blob/master/documentation/QuizCatalog.png)


