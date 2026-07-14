# ✈️ SkyReserve - Airline Reservation & Booking Platform

A secure and scalable airline reservation backend built using **Spring Boot**, implementing authentication, role-based authorization, flight management, and booking workflows. The project follows clean software architecture and modern backend engineering practices to provide a production-ready REST API.

---

## 🚀 Features

### Authentication & Authorization
- User Registration & Login
- JWT Authentication
- Refresh Token Support
- Role-Based Authorization (Admin/User)
- Secure Password Encryption using BCrypt

### Flight Management
- Add, Update and Delete Flights (Admin)
- Search Flights
- Flight Availability Management

### Booking Management
- Book Flights
- Cancel Bookings
- View Booking History
- Transactional Booking Workflow

### API Features
- RESTful API Design
- Global Exception Handling
- Request Validation
- Pagination & Filtering
- Swagger/OpenAPI Documentation
- Logging

---

## 🏗️ Architecture

The project follows a **Layered Architecture**.

```
Client
   │
   ▼
Controllers
   │
   ▼
Services
   │
   ▼
Repositories
   │
   ▼
MySQL Database
```

Each layer has a single responsibility, making the application modular, maintainable, and easy to extend.

---

## 🔐 Security

- Spring Security
- JWT Access Tokens
- Refresh Tokens
- Role-Based Access Control (RBAC)
- BCrypt Password Hashing

---

## 🗄️ Database Design

Main Entities

- User
- Flight
- Booking
- RefreshToken

Relationships

```
User
 ├── Books ─────► Booking
 │
Flight
 ├── Has Many ─► Booking
```

Hibernate and Spring Data JPA are used for ORM and relationship mapping.

---

## 🛠️ Tech Stack

### Backend
- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate

### Database
- MySQL

### Authentication
- JWT
- Refresh Tokens

### API
- RESTful APIs
- Swagger/OpenAPI

### Tools
- Maven
- Git
- Postman
- IntelliJ IDEA

---

## 📂 Project Structure

```
src
├── config
├── controller
├── dto
├── entity
├── exception
├── repository
├── security
├── service
├── util
└── FlightBookingApplication
```

---

## 📌 REST API Overview

### Authentication

- POST /auth/register
- POST /auth/login
- POST /auth/refresh-token

### Flights

- GET /flights
- GET /flights/{id}
- POST /flights
- PUT /flights/{id}
- DELETE /flights/{id}

### Bookings

- POST /bookings
- GET /bookings
- DELETE /bookings/{id}

---

## 📖 Design Principles

- Layered Architecture
- SOLID Principles
- Dependency Injection
- Separation of Concerns
- RESTful API Design

---

## 🚧 Future Enhancements

- Docker Support
- Redis Caching
- Payment Gateway Integration
- Email Notifications
- Microservices Architecture
- Kafka Event Streaming
- API Gateway
- AWS Deployment

---

## 👨‍💻 Author

Robin Singh Khural

GitHub: https://github.com/robinrb7

LinkedIn: https://linkedin.com/in/robinrb7
