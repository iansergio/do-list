# Do List — Backend API

A RESTful To-Do list API built with **Spring Boot 4**, secured with **JWT authentication** and role-based access control. Supports H2 for local development and PostgreSQL for production.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.0.0 |
| Security | Spring Security + JWT (JJWT 0.12.5) |
| Persistence | Spring Data JPA / Hibernate |
| Database (dev) | H2 (in-memory) |
| Database (prod) | PostgreSQL |
| Documentation | SpringDoc OpenAPI (Swagger UI) |
| Build | Maven |
| Utilities | Lombok |

---

## Getting Started

### Prerequisites

- Java 21+
- Maven (or use the included `./mvnw` wrapper)
- Docker (for production/PostgreSQL profile)

### Run in Development (H2 in-memory)

```bash
./mvnw spring-boot:run
```

The `dev` profile is active by default. The H2 schema is created automatically on startup.

### Run in Production (PostgreSQL)

1. Start the database:

```bash
docker-compose up -d
```

2. Run with the prod profile:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

### Build JAR

```bash
./mvnw clean package
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

---

## Configuration

| Property file | Profile | Database |
|---|---|---|
| `application-dev.properties` | `dev` | H2 in-memory |
| `application-prod.properties` | `prod` | PostgreSQL |

### Key properties

```properties
# JWT
jwt.secret=<min-32-char-secret>
jwt.access.expiration=86400000   # milliseconds
jwt.refresh.expiration=7         # days
```

The active profile is set in `application.properties`:

```properties
spring.profiles.active=dev
```

---

## Default Admin Account

An admin user is seeded automatically on startup if it does not already exist:

| Field | Value |
|---|---|
| Email | `admin@admin.com` |
| Password | `adminadmin` |
| Role | `ADMIN` |

> **Change the default password in production.**

---

## API Documentation

Swagger UI is available at:

```
http://localhost:8080/swagger-ui/index.html
```

OpenAPI JSON spec:

```
http://localhost:8080/v3/api-docs
```

---

## Endpoints

### Auth — `/api/auth`

| Method | Path | Description | Auth required |
|---|---|---|---|
| `POST` | `/api/auth/register` | Register a new user | No |
| `POST` | `/api/auth/login` | Login and receive tokens | No |
| `POST` | `/api/auth/refresh` | Refresh access token | No |
| `POST` | `/api/auth/logout` | Invalidate refresh token | No |

**Register / Login request body:**

```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "secret123"
}
```

**Auth response:**

```json
{
  "accessToken": "<jwt>",
  "refreshToken": "<uuid>",
  "email": "john@example.com",
  "name": "John Doe",
  "message": "User registered"
}
```

---

### Tasks — `/api/tasks`

All task endpoints require a valid `Authorization: Bearer <token>` header.

| Method | Path | Description |
|---|---|---|
| `POST` | `/api/tasks` | Create a new task |
| `GET` | `/api/tasks` | List authenticated user's tasks |
| `GET` | `/api/tasks/{id}` | Get task by ID |
| `PATCH` | `/api/tasks/{id}/infos` | Update task title, description, priority, due date |
| `PATCH` | `/api/tasks/{id}/status` | Update task status |
| `DELETE` | `/api/tasks/{id}` | Delete a task |

**Create task request body:**

```json
{
  "title": "Buy groceries",
  "description": "Milk, eggs, bread",
  "priority": "HIGH",
  "status": "PENDING",
  "dueDate": "2026-03-01T10:00:00"
}
```

**Enums:**

- `priority`: `LOW`, `MEDIUM`, `HIGH`
- `status`: `PENDING`, `IN_PROGRESS`, `COMPLETED`

---

### Users — `/api/users`

| Method | Path | Description | Role required |
|---|---|---|---|
| `POST` | `/api/users` | Create user (admin/internal use) | `ADMIN` |
| `GET` | `/api/users` | List all users | `ADMIN` |
| `GET` | `/api/users/email/{email}` | Find user by email | `ADMIN` |
| `DELETE` | `/api/users/{id}` | Delete user | `ADMIN` |
| `PATCH` | `/api/users/{id}/password` | Update password | Authenticated |

> **Note:** For user self-registration, use `POST /api/auth/register` which returns tokens. `POST /api/users` is intended for admin or internal user creation.

---

## Security

- **Stateless** — no HTTP sessions; every request must carry a JWT.
- **Access token** — short-lived JWT signed with HMAC-SHA, carries `email` and `role` claims.
- **Refresh token** — long-lived UUID stored in the database; used to issue new access tokens without re-login.
- Role enforcement via `@PreAuthorize` and `SecurityFilterChain` rules.

### Authentication flow

```
POST /api/auth/login
  → returns accessToken (JWT) + refreshToken (UUID)

Subsequent requests:
  Authorization: Bearer <accessToken>

On token expiry:
POST /api/auth/refresh  { "refreshToken": "<uuid>" }
  → returns new accessToken
```

---

## Project Structure

```
src/main/java/com/backend/
├── config/          # Security, OpenAPI, admin seed
├── controller/      # REST controllers
├── dto/
│   ├── request/     # Incoming request records
│   └── response/    # Outgoing response records
├── exception/       # Custom exceptions + global handler
├── model/
│   ├── core/        # BaseEntity (audit fields)
│   ├── entity/      # JPA entities (User, Task, RefreshToken)
│   └── enums/       # Role, Priority, Status
├── repository/      # Spring Data JPA repositories
├── security/        # JWT filter
└── service/         # Service interfaces + implementations
```

---

## H2 Console (dev only)

```
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:test_db
Username: sa
Password: (empty)
```

---

## Testing

Run all tests:

```bash
./mvnw test
```

Test reports are generated in `target/surefire-reports/`.

---

## Docker

`docker-compose.yml` starts a PostgreSQL 18 container:

```yaml
services:
  postgres:
    image: postgres:18-alpine
    environment:
      POSTGRES_USER: docker
      POSTGRES_PASSWORD: docker
      POSTGRES_DB: db
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

volumes:
  postgres_data:
```

```bash
docker-compose up -d    # start
docker-compose down     # stop
```
