# Employee Management System

## Project Description

The Employee Management System is a backend application developed using Java and Spring Boot to manage employee information through REST APIs.

The application allows users to register and log in securely using JWT authentication. Authenticated users can perform employee operations such as adding new employees, retrieving employee details, updating records, deleting employees, searching by name, and filtering employees by department and salary.

Employee and user information is stored in a MySQL database. The application follows a layered architecture using Controller, Service, and Repository layers to separate API handling, business logic, and database operations.

## Features

- User registration and login.
- JWT-based authentication to secure employee APIs.
- Create, retrieve, update, and delete employee records.
- Search employees by name.
- Filter employees by department.
- Retrieve employees based on salary.
- Pagination and sorting.
- Input validation and global exception handling.

## Technologies Used

- Java 21
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA
- Hibernate
- MySQL 8
- Spring Security
- JWT
- Jakarta Validation
- Maven
- JUnit 5 and Mockito
- Postman

## How the Application Works

1. A user registers using the registration API.
2. The user logs in with their username and password.
3. Spring Security authenticates the credentials and generates a JWT token.
4. The client includes the JWT token in the Authorization header when accessing employee APIs.
5. The application validates the token and processes the request.
6. Employee information is retrieved from or stored in the MySQL database.

## Project Structure

- **controller** – Handles REST API requests and responses.
- **service** – Contains business logic.
- **repository** – Handles database operations.
- **entity** – Defines Employee and AppUser database entities.
- **dto** – Contains request and response objects.
- **config** – Contains security configuration.
- **exception** – Handles application exceptions.

## API Endpoints

### Authentication

| Method | Endpoint | Description |
|---|---|---|
| POST | `/auth/register` | Register a user |
| POST | `/auth/login` | Login and generate JWT token |

### Employee Management

| Method | Endpoint | Description |
|---|---|---|
| POST | `/employees` | Create employee |
| GET | `/employees` | Retrieve all employees |
| GET | `/employees/{id}` | Retrieve employee by ID |
| PUT | `/employees/{id}` | Update employee |
| DELETE | `/employees/{id}` | Delete employee |
| GET | `/employees/department/{department}` | Filter by department |
| GET | `/employees/salary/{salary}` | Filter by salary |
| GET | `/employees/search?name={name}` | Search by employee name |

All employee endpoints require a valid JWT token.

## How to Run the Project

**Prerequisites:** Java 21, Maven, and MySQL 8.

1. Clone the repository:

   ```bash
   git clone <https://github.com/Vidhya-rani/employee-management>
   ```

2. Create the database:

   ```sql
   CREATE DATABASE employee_db;
   ```

3. Configure the MySQL connection and JWT secret in `application.properties`. Use your own credentials and keep secrets out of GitHub.

4. Run the application:

   ```bash
   mvn spring-boot:run
   ```

5. The application runs at:

   `http://localhost:8003`

## API Testing

The REST APIs were tested using Postman, and automated tests were implemented using JUnit 5 and Mockito.
