# Revie Apartments
Revie Apartments is a web application that allows users to manage their apartment listings, including adding, updating, and deleting listings. It also provides a user-friendly interface for browsing available apartments.

## Features
- User authentication
- Apartment listing management
- Review and rating system
- Helpful votes for reviews
- Sorting of reviews by votes or more recent

## Technologies Used
- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Heroku
- PostgreSQL
- Hibernate
- Swagger

## Getting Started
To run the application locally, follow these steps:
```bash
# Clone the repository: git clone git@github.com:General-Workspace/revie-apartments.git
# Change directory to the project directory: cd revie-apartments
# Build the application: mvn clean package
# Run the application: mvn spring-boot:run
```

## Environment Variables
The application requires the following environment variables to be set:
- `DATABASE_URL`: The URL of the PostgreSQL database.
- `DATABASE_USERNAME`: The username for the PostgreSQL database.
- `DATABASE_PASSWORD`: The password for the PostgreSQL database.
- `JWT_SECRET`: The secret key used for JWT token generation and validation. Uses sha256 hashing algorithm. Run `openssl rand -hex 32` to generate a random key.

## API Documentation
The API documentation is available at `/swagger-ui.html` after running the application. It provides detailed information about the available endpoints, request/response formats, and authentication methods.

## Database Setup
1. Create a PostgreSQL database.
2. Update the `application.properties` file with your database credentials.
3. Use the provided SQL scripts in the `src/main/resources/db/migration` directory to set up the database schema and initial data.
4. Run the application to apply the migrations and create the necessary tables.
5. (Optional) Use the `application-test.properties` file for testing purposes. This file contains a different database configuration for running tests.
6. Local Postgres datasource url
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/revie-apartments
```


## Project Structure
```bash
revie-apartments/
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── revie
│   │   │           └── apartments
│   │   │               ├── apartment
│   │   │               │   ├── controller
│   │   │               │   ├── entity
│   │   │               │   ├── repository
│   │   │               │   └── service
│   │   │               ├── auth
│   │   │               │   ├── controller
│   │   │               │   ├── entity
│   │   │               │   ├── dto
│   │   │               │   ├── repository
│   │   │               │   └── service
│   │   │               ├── config
│   │   │               ├── exception
│   │   │               ├── helpfulvotes
│   │   │               │   ├── controller
│   │   │               │   ├── entity
│   │   │               │   ├── repository
│   │   │               │   └── service
│   │   │               ├── reviews
│   │   │               │   ├── controller
│   │   │               │   ├── entity
│   │   │               │   ├── repository
│   │   │               │   └── service
│   │   │               └── user
│   │   │                   ├── controller
│   │   │                   ├── entity
│   │   │                   ├── repository
│   │   │                   └── service
│   │   └── resources
│   │       ├── application.properties
│   │       ├── db
│   │       │   └── migration
│   │       │       ├── V1__Create_user_table.sql
│   │       │       ├── V2__Create_apartment_table.sql
│   │       │       ├── V3__Create_review_table.sql
│   │       │       ├── V4__Create_helpful_vote_table.sql
│   │       │       └── V5__Create_auth_table.sql
│   │       └── static
│   │           └── swagger-ui
│   │               ├── swagger-ui.css
│   │               ├── swagger-ui-bundle.js
│   │               ├── swagger-ui-standalone-preset.js
│   │               └── index.html
│   └── test
│       ├── java
│       │   └── com
│       │       └── revie
│       │           └── apartments
│       │               ├── apartment
│       │               │   └── controller
│       │               ├── auth
│       │               │   └── controller
│       │               ├── helpfulvotes
│       │               │   └── controller
│       │               ├── reviews
│       │               │   └── controller
│       │               └── user
│       │                   └── controller
│       └── resources
│           └── application-test.properties
└── pom.xml
```