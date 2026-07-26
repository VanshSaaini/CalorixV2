# CalorixV2

<div align="center">

# 🏋️ CalorixV2

### Modern Fitness, Nutrition & Health Tracking Platform

*A modern full-stack fitness management web application built with **Java Spring Boot**, **ReactJS**, **Vite**, **PostgreSQL**, and **JWT Authentication**.*

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge\&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.5-green?style=for-the-badge\&logo=springboot)
![React](https://img.shields.io/badge/React-19-blue?style=for-the-badge\&logo=react)
![Vite](https://img.shields.io/badge/Vite-7-purple?style=for-the-badge\&logo=vite)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Neon-336791?style=for-the-badge\&logo=postgresql)
![JWT](https://img.shields.io/badge/JWT-Authentication-red?style=for-the-badge)
![Cloudinary](https://img.shields.io/badge/Cloudinary-Image_Storage-blue?style=for-the-badge)

</div>

---

# 📖 Overview

**CalorixV2** is a modern fitness and nutrition tracking platform that helps users monitor their complete health journey from a single dashboard.

The application allows users to:

* Create a secure account
* Track body weight
* Calculate BMI & BMR
* Monitor daily calorie intake
* Track water consumption
* Set personalized fitness goals
* Upload progress photos
* Store body measurements
* View fitness statistics
* Maintain long-term fitness history

Unlike traditional calorie tracking applications, CalorixV2 combines multiple health modules into one centralized dashboard, allowing users to make data-driven decisions about their fitness journey.

---

# 🎯 Why CalorixV2?

Most beginners struggle because their fitness data is scattered across different applications.

For example:

* Weight stored in one app
* Calories in another
* Water tracking separately
* Progress photos in gallery
* BMI calculator on Google

CalorixV2 solves this problem by bringing everything together into one secure platform.

Users can easily monitor their physical progress while keeping all health-related information synchronized.

---

# 👨‍💻 Technologies Used

## Backend

* Java 21
* Spring Boot 3.5
* Spring Security
* Spring Data JPA
* JWT Authentication
* MapStruct
* Lombok
* Maven
* PostgreSQL (Neon)
* Cloudinary
* Docker
* Render

---

## Frontend

* ReactJS
* Vite
* React Router DOM
* Axios
* Context API
* CSS3
* Responsive Design

---

## Database

* PostgreSQL (Neon Database)

---

# 🏗 System Architecture

```
                     User
                      │
                      ▼
              ReactJS + Vite
                (Frontend)
                      │
         HTTPS + REST API + JWT
                      │
                      ▼
          Spring Boot Backend
          (Authentication Layer)
                      │
          Spring Security + JWT
                      │
                      ▼
              Service Layer
                      │
                      ▼
           Repository Layer (JPA)
                      │
                      ▼
            PostgreSQL Database
                      │
                      ▼
             Cloudinary Storage
          (Progress Images Only)
```

---

# 🏛 Backend Architecture

```
Controller Layer
        │
        ▼
Service Layer
        │
        ▼
Repository Layer
        │
        ▼
Database
```

Project Structure

```
backend
│
├── controller
├── dto
├── entity
├── exception
├── mapper
├── repository
├── security
│     ├── jwt
│     ├── config
│     └── service
├── service
├── util
└── BackendApplication
```

---

# 🔐 Authentication Flow

```
User Login
      │
      ▼
Spring Security
      │
      ▼
Authenticate User
      │
      ▼
Generate JWT Token
      │
      ▼
Return Access Token
      │
      ▼
React Stores Token
(Local Storage)
      │
      ▼
Future Requests
Authorization: Bearer Token
```

---

# 🎨 Frontend Architecture

```
src
│
├── assets
├── components
│
├── layouts
│
├── pages
│
├── hooks
│
├── services
│
├── context
│
├── api
│
├── utils
│
├── routes
│
└── App.jsx
```

---

# 🔄 Application Workflow

```
User
 │
 ▼
Register
 │
 ▼
Email Validation
 │
 ▼
Login
 │
 ▼
JWT Authentication
 │
 ▼
Dashboard
 │
 ├── Weight
 ├── BMI
 ├── BMR
 ├── Goals
 ├── Calories
 ├── Water Intake
 ├── Progress Photos
 └── Measurements
```

---

# 📊 Features

## Authentication

* User Registration
* Secure Login
* JWT Authentication
* Refresh Token
* Password Encryption (BCrypt)
* Role Based Authentication

---

## Dashboard

* Fitness Overview
* Latest Statistics
* Daily Progress
* Personalized Dashboard

---

## Weight Tracking

Users can

* Add weight records
* View previous records
* Monitor weight loss/gain
* Track progress over time

---

## BMI Calculator

Users can

* Calculate BMI
* Save BMI History
* Track BMI changes

---

## BMR Calculator

Users can

* Calculate BMR
* Estimate calorie requirements
* Store historical BMR records

---

## Daily Calories

Users can

* Record calories
* Monitor daily intake
* Compare against goals

---

## Water Intake

Users can

* Track daily water consumption
* Maintain hydration history

---

## Body Measurements

Track

* Chest
* Waist
* Neck
* Arms
* Thighs
* Hips

---

## Fitness Goals

Users can

* Set weight goals
* Set calorie goals
* Monitor progress

---

## Progress Photos

* Upload photos
* Secure Cloudinary storage
* Compare transformation over time

---

# 📂 Database Design

Major Entities

```
User
Role
WeightRecord
BMIRecord
BMRRecord
MacroRecord
DailyCalories
Goal
WaterIntake
BodyMeasurement
ProgressPhoto
```

---

# 🔒 Security

CalorixV2 implements enterprise-level authentication practices.

Features include:

* JWT Authentication
* BCrypt Password Hashing
* Spring Security
* Stateless Authentication
* CORS Configuration
* Secure REST APIs
* Role-Based Authorization
* Refresh Token Mechanism

---

# 🌐 REST API

Example

```
POST /api/auth/register

POST /api/auth/login

POST /api/auth/refresh-token

GET /api/users/me

GET /api/dashboard

POST /api/weights

GET /api/weights

POST /api/calories

POST /api/bmi

POST /api/bmr

POST /api/goals

POST /api/water

POST /api/progress-photo
```

---

# ☁ Deployment Architecture

```
                     User
                       │
                       ▼
               Vercel (React)
                       │
         HTTPS REST API Requests
                       │
                       ▼
          Render (Spring Boot API)
                       │
                       ▼
             Neon PostgreSQL
                       │
                       ▼
               Cloudinary CDN
```

---

# 🚀 Local Installation

## Backend

```bash
git clone https://github.com/yourusername/CalorixV2.git

cd CalorixV2/backend

./mvnw spring-boot:run
```

---

## Frontend

```bash
cd frontend

npm install

npm run dev
```

---

# 🌍 Production

Frontend

```
Vercel
```

Backend

```
Render
```

Database

```
Neon PostgreSQL
```

Images

```
Cloudinary
```

---

# 👤 User Journey

### Step 1

Create an account.

↓

### Step 2

Login securely.

↓

### Step 3

Complete profile.

↓

### Step 4

Record body weight.

↓

### Step 5

Calculate BMI & BMR.

↓

### Step 6

Set daily calorie target.

↓

### Step 7

Track water intake.

↓

### Step 8

Upload weekly progress photos.

↓

### Step 9

Review dashboard analytics.

↓

### Step 10

Achieve fitness goals through continuous monitoring.

---

# 🎯 Future Improvements

* AI Diet Recommendation
* AI Workout Recommendation
* Food Barcode Scanner
* OCR Nutrition Scanner
* Google Authentication
* Apple Authentication
* Email Verification
* Password Reset via Email
* Dark Mode
* Fitness Challenges
* Social Community
* Smart Notifications
* Mobile Application (Android & iOS)
* Wearable Device Integration
* Weekly & Monthly Progress Reports
* Admin Dashboard
* Trainer Dashboard

---

# 📚 Learning Outcomes

This project demonstrates practical experience with:

* Java Enterprise Application Development
* Spring Boot REST APIs
* Spring Security & JWT
* PostgreSQL Database Design
* JPA & Hibernate
* DTO and Entity Mapping
* MapStruct
* Layered Architecture
* RESTful API Design
* ReactJS Development
* State Management
* Axios Integration
* Authentication Flow
* Docker
* Cloud Deployment (Render, Vercel)
* Cloudinary Integration
* Git & GitHub
* Full-Stack Application Development

---

# 🤝 Contributing

Contributions, suggestions, and feature requests are welcome.

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to your branch
5. Open a Pull Request

---

# 📜 License

This project is intended for educational purposes, portfolio demonstrations, and learning full-stack web application development.

---

# 👨‍💻 Author

**Vansh Saini**

**CalorixV2** showcases a complete full-stack fitness management system built with modern Java and React technologies. The project demonstrates secure authentication, scalable backend architecture, responsive frontend development, cloud deployment, and best practices for building production-ready web applications.
