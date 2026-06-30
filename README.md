# Library Management System

A Spring Boot based Library Management System for managing students, books, library cards, and book issue/return transactions.

## Features

- Manage students
- Manage books
- Manage library cards
- Issue and return books
- Track transaction details
- REST API based backend
- MySQL database integration
- Spring Data JPA for database operations

## Tech Stack

- Java 17
- Spring Boot 3.5.16
- Spring Web
- Spring Data JPA
- MySQL
- Lombok
- Maven

## Project Structure
```text
src/main/java/com/sojoteki/library_management_system
├── controller
├── service
├── repository
├── model
├── request_dto
└── enums
```
## Database Configuration

The application uses MySQL.

Default configuration:
```properties
server.port=7777
spring.datasource.url=jdbc:mysql://localhost:3306/library_management_system
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```
Update `src/main/resources/application.properties` with your local MySQL username and password.

## How to Run

1. Clone the repository
```bash
git clone <repository-url>
cd library-management-system
```
2. Create a MySQL database
```sql
CREATE DATABASE library_management_system;
```
3. Update database credentials in:
```text
src/main/resources/application.properties
```
4. Run the application
```bash
mvn spring-boot:run
```
The application will start on:
```text
http://localhost:7777
```
## API Modules

- Book APIs
- Student APIs
- Card APIs
- Transaction APIs

## Description

This project is a backend application designed to handle core library operations. It provides service-layer logic and REST endpoints for managing books, students, library cards, and transactions such as issuing and returning books.
