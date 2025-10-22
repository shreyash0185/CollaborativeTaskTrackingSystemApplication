# 🧩 TaskMaster – Collaborative Task Tracking System

## 📘 Overview
TaskMaster is a backend system built with **Spring Boot** and **MongoDB** for managing tasks, teams, and projects.  
It supports **JWT authentication**, **real-time notifications (SSE)**, **caching**, and clean layered architecture following best practices.

---

## ⚙️ Tech Stack
- **Java 17**, **Spring Boot 3**
- **MongoDB** (NoSQL)
- **Spring Security + JWT**
- **Spring Cache** (in-memory / Redis-ready)
- **Server-Sent Events (SSE)** for notifications
- **Maven**, **JUnit5**, **Swagger**

---

## 🚀 Key Features
- ✅ Secure JWT-based Authentication & Authorization
- ✅ CRUD for Tasks, Projects, Teams
- ✅ Real-Time Notifications (SSE)
- ✅ Comments & Attachments per Task
- ✅ Caching for frequently used APIs
- ✅ Centralized Exception Handling
- ✅ Swagger UI Documentation

---

## 🧠 Architecture

Controller → Service → Repository → MongoDB
↓         ↓
(JWT)     (Cache, SSE)

- **Controller** – REST endpoints
- **Service** – Business logic + Caching + Notifications
- **Repository** – MongoDB CRUD operations

---

## 🗂️ Folder Structure
- src/main/java/com/collaborative_task_tracking_system/
- ├── controller/        # All REST controllers
- ├── service/           # Business logic + SSE
- ├── repository/        # Mongo Repos
- ├── entity/            # User, Team, Project, Task, Comment, Attachment
- ├── dto/               # Request/Response objects
- ├── security/          # JWT utilities & filters
- └── config/            # WebSecurity & Swagger

---

## 🔔 Notification Flow (Example)
1️⃣ Shreyash assigns a task →  
2️⃣ `TaskService` saves it →  
3️⃣ `NotificationService` stores + streams via SSE →  
4️⃣ Frontend receives live update instantly.

---

## 💾 Caching Example
```java
@Cacheable("tasks")
public List<TaskResponse> getAllTasks() { ... }

@CacheEvict(value = "tasks", allEntries = true)
public TaskResponse updateTask(...) { ... }

git clone https://github.com/shreyash0185/TaskMaster.git
cd TaskMaster
mvn clean install
mvn spring-boot:run
