# optimal-api

Swagger URL: http://localhost:8090/swagger-ui/index.html#/
![Project Logo](images/swagger.png)

Spring Boot User Management API
This is a Spring Boot project following SOLID principles, CQRS, and best coding practices. It provides RESTful APIs for managing users, including features like pagination, Swagger documentation, error handling, logging, unit tests, random user generation, and hierarchical user structures.

Getting Started
Prerequisites
Java 17+
Maven
Running the Application
Clone the repository:
sh
Copy
Edit
git clone <your-repo-url>
cd <your-project-directory>
Build and run the project using Maven:
sh
Copy
Edit
mvn spring-boot:run
Database
This project uses H2 in-memory database, so no external database setup is required. The database schema is automatically created at runtime.

API Documentation
After starting the application, access the Swagger API documentation at:

bash
Copy
Edit
http://localhost:8080/swagger-ui.html
