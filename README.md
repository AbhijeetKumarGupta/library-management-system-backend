# Library Management System

A Spring Boot based REST API backend for managing students, books, library cards, and book issue/return transactions.

## Tech Stack

- Java 17
- Spring Boot 3.5.16
- Spring Web
- Spring Data JPA
- MySQL (runtime)
- H2 (test)
- Lombok
- Maven

## Project Structure

```text
src/main/java/com/sojoteki/library_management_system
├── controller/          # REST API endpoints
│   ├── BookController.java
│   ├── CardController.java
│   ├── StudentController.java
│   └── TransactionController.java
├── service/             # Business logic layer
│   ├── BookService.java
│   ├── CardService.java
│   ├── StudentService.java
│   └── TransactionService.java
├── repository/          # Spring Data JPA repositories
│   ├── BookRepository.java
│   ├── CardRepository.java
│   ├── StudentRepository.java
│   └── TransactionRepository.java
├── model/               # JPA entity classes
│   ├── Book.java
│   ├── Card.java
│   ├── Student.java
│   └── Transaction.java
├── request_dto/         # Request validation DTOs
│   ├── BookRequestDto.java
│   ├── CardRequestDto.java
│   ├── StudentRequestDto.java
│   └── TransactionRequestDto.java
├── response/            # API response models
│   └── ApiError.java
├── exception/           # Exception handling
│   ├── BadRequestException.java
│   ├── ResourceNotFoundException.java
│   └── GlobalExceptionHandler.java
└── enums/               # Enum types
    ├── CardStatus.java
    ├── Gender.java
    └── TransactionType.java
```

## Data Model

### Entity Relationships

```
Student (1) --- (1) Card (1) --- (*) Book
                      |
                  (*) Transaction (*) --- Book
```

- **Student** -- One-to-One -- **Card**: Each student is linked to one library card.
- **Card** -- One-to-Many -- **Book**: A card can have multiple books issued against it.
- **Card** -- One-to-Many -- **Transaction**: All borrow/return operations for a card.
- **Book** -- One-to-Many -- **Transaction**: All transactions involving a specific book.

### Entities

#### Student
| Field | Type | Constraints |
|---|---|---|
| id | int | Primary key, auto-increment |
| name | String | Not null |
| email | String | Not null, unique |
| mobile | String | Not null |
| department | String | Not null |
| semester | String | Not null |
| gender | Gender (MALE, FEMALE) | Not null, enumerated as string |
| address | String | Not null |
| dob | String | Not null |
| card | Card | One-to-one mapped by `student` on Card side |

#### Card
| Field | Type | Constraints |
|---|---|---|
| id | int | Primary key, auto-increment |
| cardStatus | CardStatus (ACTIVE, INACTIVE, BLOCKED) | Not null |
| expiryDate | String | Not null |
| createdDate | Date | Auto-set on creation |
| updatedDate | Date | Auto-set on update |
| student | Student | One-to-one join on `student_id` (unique) |
| books | List<Book> | One-to-many mapped by `card` |
| transactions | List<Transaction> | One-to-many mapped by `card` (cascade all) |

#### Book
| Field | Type | Constraints |
|---|---|---|
| id | int | Primary key, auto-increment |
| title | String | Not null |
| publisherName | String | Not null |
| publishedDate | String | Not null |
| pages | int | Not null |
| availability | boolean | Not null |
| category | String | Not null |
| rackNo | int | Not null |
| card | Card | Many-to-one join on `card_id` (lazy, nullable) |
| transactions | List<Transaction> | One-to-many mapped by `book` (cascade all) |

#### Transaction
| Field | Type | Constraints |
|---|---|---|
| id | int | Primary key, auto-increment |
| transactionDate | Date | Auto-set on creation |
| dueDate | String | Not null |
| transactionType | TransactionType (BORROW, RETURN) | Not null |
| card | Card | Many-to-one join on `card_id` (lazy) |
| book | Book | Many-to-one join on `book_id` (lazy) |

### Enums

- **Gender**: `MALE`, `FEMALE`
- **CardStatus**: `ACTIVE`, `INACTIVE`, `BLOCKED`
- **TransactionType**: `BORROW`, `RETURN`

## API Reference

All endpoints are prefixed with `/api/v1`.

### Book Endpoints

| Method | Path | Description | Request Body / Params | Response |
|---|---|---|---|---|
| POST | `/book/save` | Create a new book | `BookRequestDto` (JSON) | 201 Created |
| GET | `/book` | Get all books | none | `List<Book>` |
| GET | `/book/{id}` | Get book by ID | Path variable `id` | `Book` |
| GET | `/book/getByTitle?title=` | Get book by title | Query param `title` | `Book` |
| PUT | `/book/update/{id}` | Update a book | Path variable `id` + `BookRequestDto` | Success message |
| DELETE | `/book/delete/{id}` | Delete a book | Path variable `id` | Success message |

### Student Endpoints

| Method | Path | Description | Request Body / Params | Response |
|---|---|---|---|---|
| POST | `/student/save` | Create a new student with a card | `StudentRequestDto` (JSON) | 201 Created |
| GET | `/student` | Get paginated students | `sortBy` (default: id), `sortOrder` (asc/desc), `pageNo`, `pageSize` | `Page<Student>` |
| GET | `/student/{id}` | Get student by ID | Path variable `id` | `Student` |
| GET | `/student/getByEmail?email=` | Get student by email | Query param `email` | `Student` |
| PUT | `/student/update/{id}` | Update a student (can reassign card) | Path variable `id` + `StudentRequestDto` | Success message |
| DELETE | `/student/delete/{id}` | Delete a student | Path variable `id` | Success message |

### Card Endpoints

| Method | Path | Description | Request Body / Params | Response |
|---|---|---|---|---|
| POST | `/card/save` | Create a new library card | `CardRequestDto` (JSON) | 201 Created |
| GET | `/card` | Get all cards | none | `List<Card>` |
| GET | `/card/{id}` | Get card by ID | Path variable `id` | `Card` |
| PUT | `/card/update/{id}` | Update a card | Path variable `id` + `CardRequestDto` | Success message |
| DELETE | `/card/delete/{id}` | Delete a card | Path variable `id` | Success message |

### Transaction Endpoints

| Method | Path | Description | Request Body / Params | Response |
|---|---|---|---|---|
| POST | `/transaction/save` | Issue or return a book | `TransactionRequestDto` (JSON) | 201 Created |
| GET | `/transaction` | Get all transactions | none | `List<Transaction>` |
| GET | `/transaction/{id}` | Get transaction by ID | Path variable `id` | `Transaction` |

### Request DTOs

#### BookRequestDto
```json
{
  "title": "string (required)",
  "publisherName": "string (required)",
  "publishedDate": "string (required)",
  "pages": "integer (min 1, required)",
  "availability": "boolean",
  "category": "string (required)",
  "rackNo": "integer (min 1, required)"
}
```

#### StudentRequestDto
```json
{
  "name": "string (required)",
  "email": "string (required, valid email)",
  "mobile": "string (required)",
  "department": "string (required)",
  "semester": "string (required)",
  "gender": "MALE | FEMALE (required)",
  "address": "string (required)",
  "dob": "string (required)",
  "cardId": "integer (min 1, required)"
}
```

#### CardRequestDto
```json
{
  "cardStatus": "ACTIVE | INACTIVE | BLOCKED (required)",
  "expiryDate": "string (required)"
}
```

#### TransactionRequestDto
```json
{
  "dueDate": "string (required)",
  "transactionType": "BORROW | RETURN (required)",
  "cardId": "integer (min 1, required)",
  "bookId": "integer (min 1, required)"
}
```

### Error Response Format

All errors return a standardized `ApiError` response:

```json
{
  "timestamp": "2026-01-01T00:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/v1/book/save",
  "validationErrors": {
    "title": "must not be blank"
  }
}
```

| HTTP Status | Condition |
|---|---|
| 400 | Validation errors or bad request (e.g., borrowing an unavailable book) |
| 404 | Resource not found |
| 500 | Unexpected server error |

## Business Logic

### Transaction Rules

- **Borrow**: The book must be available (`availability = true`). On borrow, the book is marked unavailable and assigned to the card.
- **Return**: The book must be currently issued to the same card. On return, the book is marked available and unassigned from the card.
- Invalid state transitions (e.g., borrowing an already-borrowed book, returning a book not issued to the card) result in a 400 Bad Request.

### Student-Card Assignment

- When creating a student, the `cardId` must reference an existing unassigned card (a card not already linked to another student). If the card is already assigned, a 400 Bad Request is returned.
- Updating a student can reassign a different card, which detaches the old card from the student.

## Database Configuration

The application uses MySQL with all configuration externalized via environment variables (loaded from `.env` file if present).

### Environment Variables

| Variable | Default | Description |
|---|---|---|
| `SERVER_PORT` | `8080` | Application server port |
| `DB_URL` | `jdbc:mysql://localhost:3306/library_management_system` | JDBC connection URL |
| `DB_USERNAME` | `root` | Database username |
| `DB_PASSWORD` | (empty) | Database password |
| `DDL_AUTO` | `update` | Hibernate DDL auto mode |
| `SHOW_SQL` | `false` | Log SQL statements |

## How to Run

### Prerequisites

- Java 17+
- MySQL server
- Maven (or use the bundled Maven wrapper)

### Steps

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd library-management-system
   ```

2. Create the database:
   ```sql
   CREATE DATABASE library_management_system;
   ```

3. Configure credentials via environment variables (recommended) or edit `src/main/resources/application.properties`.

4. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

   The application starts at `http://localhost:8080` (or the port configured via `SERVER_PORT`).

### Docker

A multi-stage Dockerfile is provided:

```bash
docker build -t library-management-system .
docker run -p 8080:8080 --env-file .env library-management-system
```

The build stage uses `eclipse-temurin:17-jdk` and compiles via `./mvnw clean package -DskipTests`. The runtime stage uses `eclipse-temurin:17-jre`.

## Tests

Tests use an H2 in-memory database through the `test` profile and do not require MySQL.

```bash
./mvnw test
```

The test suite includes a context-loading smoke test that verifies the application starts successfully.

## Postman

A Postman collection is available at `postman/collections/library-management-system`. All requests use the global variable `{{baseUrl}}`, which defaults to `http://localhost:7777`.
