# Employee Management System

A RESTful backend application built using **Java, Spring Boot, Spring Security, JWT, Spring Data JPA, Hibernate, and MySQL**. This project provides employee management operations, secure user authentication, database integration, and REST API endpoints.

## Project Overview

The Employee Management System is designed to manage employee information through REST APIs. It supports creating, retrieving, updating, deleting, searching, and filtering employee records.

The application also implements JWT-based authentication to secure employee-related endpoints.

## Technologies Used

| Technology | Purpose |
|---|---|
| Java 21 | Core programming language used to implement application logic and OOP concepts |
| Spring Boot | Simplifies application configuration and development |
| Spring Web | Creates REST APIs and handles HTTP requests and responses |
| Spring Security | Secures endpoints and manages authentication |
| JWT | Provides token-based authentication for protected APIs |
| Spring Data JPA | Simplifies database operations using repository interfaces |
| Hibernate | Maps Java objects to database tables |
| MySQL 8 | Stores employee and user account information |
| Maven | Manages project dependencies and builds |
| JUnit 5 | Supports unit and application testing |
| Mockito | Mocks dependencies during unit testing |
| AssertJ | Provides readable test assertions |
| Postman | Tests REST API endpoints |
| Git & GitHub | Version control and source code hosting |

## Features

- **Employee CRUD Operations:** Create, retrieve, update, and delete employee records.
- **JWT Authentication:** Register and log in users to obtain authentication tokens.
- **Spring Security:** Protect employee APIs using token-based authentication.
- **Employee Search:** Search employees by name.
- **Salary Filtering:** Retrieve employees whose salary is greater than a specified amount.
- **Department Filtering:** Retrieve employees based on department.
- **Pagination and Sorting:** Retrieve employee records in pages and sort the results.
- **Input Validation:** Validate incoming employee request data.
- **Global Exception Handling:** Handle application exceptions consistently.
- **DTO Pattern:** Separate API request and response objects from entity classes.

## Application Architecture

The application follows a layered architecture to separate responsibilities and make the code easier to maintain.

```text
Client (Postman)
      |
      v
Controller Layer
      |
      v
Service Layer
      |
      v
Repository Layer
      |
      v
MySQL Database
```

### Main Packages

| Package | Responsibility |
|---|---|
| config | Security and application configuration |
| controller | Handles REST API requests |
| dto | Contains request and response objects |
| entity | Defines database entities |
| exception | Handles application exceptions |
| repository | Provides database access |
| service | Contains business logic |

## REST API Endpoints

### Authentication APIs

| Method | Endpoint | Description |
|---|---|---|
| POST | `/auth/register` | Register a new user |
| POST | `/auth/login` | Authenticate a user and generate a JWT |

### Employee APIs

| Method | Endpoint | Description |
|---|---|---|
| GET | `/employees` | Retrieve all employees |
| GET | `/employees/{id}` | Retrieve an employee by ID |
| POST | `/employees` | Create a new employee |
| PUT | `/employees/{id}` | Update employee details |
| DELETE | `/employees/{id}` | Delete an employee |
| GET | `/employees/search?name=Arjun` | Search employees by name |
| GET | `/employees/salary/50000` | Find employees with salary greater than the specified amount |
| GET | `/employees/department/IT` | Filter employees by department |
| GET | `/employees?page=0&size=5&sortBy=id&direction=asc` | Retrieve employees using pagination and sorting |

## Database Design

**Database:** `employee_db`

### Employee Table

| Column | Description |
|---|---|
| id | Employee primary key |
| name | Employee name |
| email | Employee email address |
| department | Employee department |
| salary | Employee salary |

### Users Table

| Column | Description |
|---|---|
| id | User primary key |
| username | Unique username |
| password | BCrypt-hashed password |
| role | User role |

## Authentication Flow

1. A user registers through `/auth/register`.
2. The user logs in through `/auth/login`.
3. The application authenticates the credentials.
4. A JWT is generated after successful authentication.
5. The client sends the token in the Authorization header when accessing protected APIs.

Example header:

```http
Authorization: Bearer <your-jwt-token>
```

## Testing

The application was tested using Postman and automated tests.

- REST API testing using Postman.
- Unit testing of service-layer functionality.
- Controller testing.
- JUnit 5, Mockito, and AssertJ.

**Test result:** 18 automated tests passed (9 controller tests and 9 service tests).

## Prerequisites

Make sure the following are installed:

- Java 21 or later
- MySQL 8
- Maven
- Postman (for API testing)

## How to Run the Project

**1. Clone the repository**

```bash
git clone https://github.com/Vidhya-rani/employee-management.git
```

**2. Navigate to the project directory**

```bash
cd employee-management
```

**3. Create the database**

```sql
CREATE DATABASE employee_db;
```

**4. Configure environment variables**

Set the following environment variables before starting the application:

```text
DB_USERNAME=your_mysql_username
DB_PASSWORD=your_mysql_password
JWT_SECRET=your_secure_jwt_secret
```

Do not commit actual passwords, JWT secrets, or access tokens to GitHub.

**5. Run the application**

Using Maven:

```bash
mvn spring-boot:run
```

The application runs at:

```text
http://localhost:8003
```

GitHub: [Vidhya-rani](https://github.com/Vidhya-rani)

Project Repository: [Employee Management System](https://github.com/Vidhya-rani/employee-management)
