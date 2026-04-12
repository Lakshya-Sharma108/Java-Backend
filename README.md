# 🎓 Student Manager — Spring Boot Backend

A clean, production-ready REST API for managing students, built with **Spring Boot 3**, **JWT authentication**, and **MySQL**.

---

## ✨ Features

| Feature | Details |
|---------|---------|
| 🔐 **JWT Authentication** | Stateless token-based auth with role support (ADMIN / USER) |
| 👥 **Student CRUD** | Create, read, update, and delete student records |
| 📊 **Excel Export** | Download all students as a `.xlsx` spreadsheet |
| 🛡️ **Role-Based Access** | Admin-only routes for management; public registration |
| ✅ **Input Validation** | Bean Validation with clean JSON error responses |
| 🐳 **Docker Ready** | Multi-stage Dockerfile included |
| 🌐 **CORS Enabled** | Works with any frontend origin out of the box |

---

## 🛠️ Tech Stack

- **Java 17** + **Spring Boot 3.5**
- **Spring Security** — JWT filter chain
- **Spring Data JPA** — Hibernate + MySQL
- **JJWT 0.11** — Token generation & validation
- **Apache POI** — Excel export
- **Maven** — Build tool
- **Docker** — Containerization

---

## 📁 Project Structure

```
src/main/java/com/example/studentmanager/
├── config/          # Security config, exception handler, admin seeder
├── controller/      # REST endpoints (Auth, Student, Excel)
├── dto/             # Request & response DTOs
├── model/           # User JPA entity
├── repository/      # Spring Data repository
├── security/        # JWT filter & service
└── service/         # Business logic
```

---

## 🚀 API Endpoints

### Auth

| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| `POST` | `/login` | Public | Login and receive a JWT token |

### Students

| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| `POST` | `/api/register` | Public | Register a new student |
| `GET` | `/api/students` | Admin | List all students |
| `PUT` | `/api/students/{id}` | Admin | Update a student |
| `DELETE` | `/api/students/{id}` | Admin | Delete a student |

### Export

| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| `GET` | `/api/export` | Admin | Download students as Excel file |

---

## ⚙️ Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- MySQL 8+

### 1. Clone the repo

```bash
git clone https://github.com/Lakshya-Sharma108/Java-Backend.git
cd Java-Backend
```

### 2. Configure environment variables

Set the following (or update `application.properties`):

| Variable | Description | Default |
|----------|-------------|---------|
| `SPRING_DATASOURCE_URL` | MySQL JDBC URL | — |
| `SPRING_DATASOURCE_USERNAME` | DB username | — |
| `SPRING_DATASOURCE_PASSWORD` | DB password | — |
| `JWT_SECRET` | Signing key (≥ 32 chars) | dev key |
| `ADMIN_EMAIL` | Seed admin email | `admin@studentmanager.com` |
| `ADMIN_PASSWORD` | Seed admin password | `Admin@123` |

### 3. Run

```bash
./mvnw spring-boot:run
```

The API starts on **http://localhost:8080**.

> On first run, a default **admin account** is created automatically using the seed credentials above.

---

## 🐳 Docker

```bash
docker build -t student-manager .
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host:3306/mydb \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=secret \
  -e JWT_SECRET=your_32_char_secret_key_here_min \
  student-manager
```

---

## 🔑 Auth Flow

```
1. POST /login  →  { email, password }
2. Server validates  →  returns { token, role, email }
3. Client sends token in header:
   Authorization: Bearer <token>
4. Protected routes verify token via JWT filter
```

---

## 📝 Sample Requests

**Login**
```bash
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@studentmanager.com","password":"Admin@123"}'
```

**Register a student**
```bash
curl -X POST http://localhost:8080/api/register \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com"}'
```

**List students (admin)**
```bash
curl http://localhost:8080/api/students \
  -H "Authorization: Bearer <your-token>"
```

---

## 📄 License

This project is open-source and available for personal and educational use.
