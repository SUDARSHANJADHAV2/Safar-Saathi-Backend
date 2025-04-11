# Safar Saathi - Backend

Safar Saathi is a full-stack travel booking platform backend developed using Spring Boot and Java 21.  
It provides secure REST APIs for authentication, trip booking, package management, and payment processing.

The backend follows a clean layered architecture with Controllers, Services, Repositories, and Security modules.

---

## 🚀 Tech Stack

- Java 21
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA (Hibernate)
- MySQL
- Maven
- REST APIs
- Docker (Optional)

---

## 🔐 Key Features

- User Registration & Login
- JWT-based Authentication
- Role-Based Authorization (Admin / Customer / Vendor)
- Trip & Destination Management
- Package Management
- Booking System
- Payment Integration
- Email Notification Service
- PDF Invoice Generation
- Secure REST API endpoints

---

## 🏗 Project Architecture

src/main/java/com/travel

- controllers → REST API endpoints  
- services → Business logic  
- repositories → Database interaction  
- entities → JPA entities  
- security → JWT & Spring Security configuration  
- utils → Utility classes (PDF, JWT, etc.)

---

## ⚙️ How To Run

### 1️⃣ Clone Repository

git clone https://github.com/Patilshubham08/SafarSaathi-Backend.git

### 2️⃣ Configure Database

Update src/main/resources/application.properties:

spring.datasource.username=your_username  
spring.datasource.password=your_password  

Make sure MySQL is running.

### 3️⃣ Build Project

mvn clean install

### 4️⃣ Run Application

mvn spring-boot:run

Application will start at:

http://localhost:8080

---

## 🔒 Security Implementation

- Password encryption using BCrypt
- JWT Token Authentication
- Stateless session management
- Role-based API authorization

---

## 🧾 Payment & Invoice Module

- Integrated payment service
- Stores payment status
- Generates PDF invoice
- Sends email notification after successful payment

---

## 📌 API Base URL

http://localhost:8080/api

---

## 👨‍💻 Developed By

Shubham Patil  
CDAC Student | Java Backend Developer
