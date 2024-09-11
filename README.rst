Demo Spring Boot Application
============================

This is a demo Spring Boot application that covers several key features of modern Java development. Below is a detailed breakdown of the project's structure and its main components.

- REST API development with Spring Data JPA and Hibernate
- Unit testing with JUnit and Mockito
- Spring Security with JWT authentication
- Database integration with MySQL using Docker Compose

Project Setup
-------------

1. Clone the repository:

.. code-block:: bash

   git clone https://github.com/emrekndl/demo-spring-app.git

   cd demo-spring-app

2. **compose-mysql.yaml**:
    - This file contains the Docker Compose configuration to set up a MySQL database container.

3. **db-data/product_table.sql**:
    - SQL script for initializing the database schema and inserting sample data.
    - The SQL queries in this file should be executed manually to set up the necessary tables and insert data into the database. You can run this script by connecting to your MySQL instance and executing the commands in the correct order based on the relationships (e.g., creating tables before inserting data).

Database Setup Instructions
---------------------------

To set up the database for this project, follow these steps:

1. Start your MySQL instance using Docker Compose or your preferred method:

.. code-block:: bash

   docker-compose -f compose-mysql.yaml up

2. Connect to the running MySQL container:

.. code-block:: bash

   docker exec -it <mysql_container_name> mysql -u <username> -p

   # Replace `<mysql_container_name>` with the actual name of the MySQL container. You can find this name using:
   docker ps

3. Once connected, create the necessary tables and insert sample data manually. Use the SQL commands found in the `db-data/product_table.sql` file. Run each command in the correct order (e.g., create tables before inserting data).

   - To manually create the tables, copy and paste the SQL queries for table creation from `product_table.sql` into the MySQL terminal.
   - Then, insert the sample data into the tables by running the corresponding `INSERT INTO` queries.
   
   The script includes:

    - **Table creation**: Creates tables such as `product`, `customer`, `address`, and sets up relationships between them (e.g., One-to-One, One-to-Many, Many-to-Many).
    - **Sample data insertion**: Inserts example data into the `product`, `customer`, and `address` tables.

Main Components
---------------

1. **src/main/java/com/example/demospringapp**:
    - This is the main Java source directory containing the business logic of the application.
    
    - **DemoSpringAppApplication.java**: 
        - The main class that starts the Spring Boot application.

    - **catfact/**: 
        - Implements an external API integration for fetching random cat facts.
        - `CatFactController.java`: REST controller for the API.
        - `CatFactService.java`: Service layer for handling API requests.
        - `CatFactDTO.java`, `CatFactResponse.java`: Data Transfer Objects for API responses.

    - **exceptions/**:
        - Contains custom exception classes for handling application errors.
        - `GlobalExceptionHandler.java`: Centralized exception handler using `@ControllerAdvice`.

    - **mappings/**:
        - Contains relational mappings for entities such as `Customer` and `Address`.
        - `ICustomerRepository.java`: Repository interface for customer-related operations.

    - **product/**:
        - This package handles all product-related functionality, including headers and service classes.
        - `ProductController.java`: REST controller for product management.
        - `Product.java`, `ProductDTO.java`: Entity and DTO classes for the `Product`.
        - `services/`: Business logic for creating, updating, deleting, and searching products.
        - `validators/`: Contains `ProductValidator.java` to ensure valid product data.

    - **security/**:
        - This package implements Spring Security, including JWT-based authentication.
        - `SecurityConfiguration.java`: Configures security settings for the application.
        - `CustomUserDetailsService.java`, `JwtUtil.java`: Manages user details and JWT token generation.

    - **transaction/**:
        - Handles bank account operations with transaction management.
        - `TransferService.java`: Service for managing bank account transfers.

2. **src/main/resources**:
    - Contains application configuration files.
    - `application.properties`: Configuration settings for database connections and other properties.

Testing
-------

1. **src/test/java/com/example/demospringapp**:
    - Contains unit and integration tests for the application's services and validation logic.
    - `product/services/`: Test cases for product-related services like creation, update, and deletion.
    - `validators/ProductValidatorTest.java`: Test cases for the product validation logic.

Logging
-------

1. **mylog.log**:
    - Custom log file to track application activity.

Build and Run Instructions
--------------------------

1. Build the project using Maven:

.. code-block:: bash

   ./mvnw clean install

2. Start the application with Docker Compose for the MySQL setup:

.. code-block:: bash

   docker-compose -f compose-mysql.yaml up


HTTPie API Endpoint Test Examples
==================================

HTTPie is a user-friendly tool for making HTTP requests from the terminal. For more information, visit the [official HTTPie website](https://httpie.io/).

Product Search
--------------
**Endpoint:** `GET /product/search`

.. code-block:: bash

    http GET http://localhost:8080/product/search name==example

Cat Facts
---------
**Endpoint:** `GET /catfact`

.. code-block:: bash

    http GET http://localhost:8080/catfact

Login
------
**Endpoint:** `POST /login`

.. code-block:: bash

    http POST http://localhost:8080/login username=example password=example

Get Customer Information
------------------------
**Endpoint:** `GET /customer/{id}`

.. code-block:: bash

    http GET http://localhost:8080/customer/1

Product Headers
---------------
**Endpoint:** `GET /header/product`

.. code-block:: bash

    http GET http://localhost:8080/header/product

Update Product
--------------
**Endpoint:** `PUT /product/{id}`

.. code-block:: bash

    http PUT http://localhost:8080/product/1 name==UpdatedProduct price==199.99

Closed Endpoint
---------------
**Endpoint:** `GET /closed`

.. code-block:: bash

    http GET http://localhost:8080/closed

Regional Response
-----------------
**Endpoint:** `GET /header`

.. code-block:: bash

    http GET http://localhost:8080/header region==us

All Products
------------
**Endpoint:** `GET /products`

.. code-block:: bash

    http GET http://localhost:8080/products

Create New User
---------------
**Endpoint:** `POST /createnewuser`

.. code-block:: bash

    http POST http://localhost:8080/createnewuser username=example password=example

Create New Product
------------------
**Endpoint:** `POST /product`

.. code-block:: bash

    http POST http://localhost:8080/product name==NewProduct price==99.99

Special Endpoint
----------------
**Endpoint:** `GET /special`

.. code-block:: bash

    http GET http://localhost:8080/special

Open Endpoint
-------------
**Endpoint:** `GET /open`

.. code-block:: bash

    http GET http://localhost:8080/open

Delete Product
--------------
**Endpoint:** `DELETE /product/{id}`

.. code-block:: bash

    http DELETE http://localhost:8080/product/1

Money Transfer
--------------
**Endpoint:** `POST /transfer`

.. code-block:: bash

    http POST http://localhost:8080/transfer amount==100.00 fromAccount==123456 toAccount==654321

Basic Endpoint
--------------
**Endpoint:** `GET /basic`

.. code-block:: bash

    http GET http://localhost:8080/basic

Get Product Information
-----------------------
**Endpoint:** `GET /product/{id}`

.. code-block:: bash

    http GET http://localhost:8080/product/1


Key Features and Concepts
-------------------------

This Spring Boot application covers many core concepts and advanced topics, which can be learned through the following key components:

1. **Controllers**:
    - REST APIs to handle requests and responses.
    - Implemented in `ProductController.java`, `CatFactController.java`, etc.

2. **Exception Handling**:
    - The application handles errors using Spring's `@ControllerAdvice` and custom exceptions.
    - Implemented in `GlobalExceptionHandler.java`.

3. **Validation**:
    - Ensures that the data coming into the application is valid.
    - `ProductValidator.java` is used for validating product data.

4. **Relational Mappings**:
    - Demonstrates One-to-One, One-to-Many, and Many-to-Many relationships using JPA annotations.
    - Implemented in `Customer`, `Address` classes with relational repositories.

5. **Query String Params & Custom Queries**:
    - Shows how to work with query parameters and custom repository methods using `@Query` in Spring Data JPA.
    - Available in `ICustomerRepository.java`.

6. **Authentication & Security (JWT)**:
    - The application uses Spring Security with JWT for securing endpoints.
    - Configured in `SecurityConfiguration.java`, `JwtUtil.java`.

7. **Unit Testing**:
    - Unit and integration tests are written for various components such as services and validators.
    - Tests ensure the correctness of business logic and validation.

8. **Transactions**:
    - Demonstrates the use of `@Transactional` for handling transactions in database operations.
    - Managed in the `TransferService.java` class.

9. **Headers & Logging**:
    - Custom headers can be passed to API requests, and logging is integrated to track application activity.
    - Logging is written to `mylog.log`.

10. **External API Integration**:
    - Demonstrates how to integrate external APIs (such as the Cat Facts API) into the application.
    - Handled in the `CatFactService.java`.

Design Patterns and Structures
------------------------------

This project demonstrates the use of various design patterns and architectural structures for modern Java application development. Below are explanations of these patterns and how they are utilized in the project:

Repository Pattern
------------------

**Description**: The Repository Pattern is used to manage data access and operations from a centralized place. This pattern separates data access code from business logic, making the application more organized and easier to maintain.

**Used In**:
- `ICustomerRepository.java` - Manages customer data.
- `IProductRepository.java` - Manages product data.
- `IBankAccountRepository.java` - Manages bank account data.

**When to Use**: When you want to abstract data access operations and separate them from the business logic of your application.

Dependency Injection (DI)
-------------------------

**Description**: Dependency Injection is a technique used to provide dependencies from the outside rather than hard-coding them within classes. This makes the application more flexible and testable. Spring Framework handles this automatically through configuration files or annotations.

**Used In**:
- `CatFactService.java` - Injected into `CatFactController.java`.
- `CreateProductService.java`, `DeleteProductService.java`, etc. - Injected into `ProductController.java`.
- `TransferService.java` - Injected into `BankController.java`.

**When to Use**: When you want to manage dependencies between classes and enhance the flexibility of your application.

Command-Query Separation (CQS)
-------------------------------

**Description**: The Command-Query Separation (CQS) principle suggests separating operations that modify data (commands) from operations that query data (queries). This makes the code more readable and maintainable. Commands are typically found in `Command` classes, and queries in `Service` classes.

**Used In**:
- `UpdateProductCommand.java` - Represents commands to modify data.
- `CreateProductService.java`, `DeleteProductService.java`, etc. - Services that modify data.
- `GetProductService.java`, `GetProductsService.java` - Services that query data.

**When to Use**: When you want to separate data modification and querying operations to make the code more manageable.

MVC (Model-View-Controller) Pattern
-------------------------------------

**Description**: The MVC Pattern separates the user interface from business logic. The Model represents data and business logic; the View represents the user interface; and the Controller handles user interactions. This pattern provides a well-structured and decoupled application design.

**Used In**:
- `ProductController.java`, `CatFactController.java`, `BankController.java` - Controllers that handle user requests.
- `Product.java`, `CatFactDTO.java`, `TransferDTO.java` - Model classes that represent data.
- `ProductService.java`, `CatFactService.java` - Services that handle business logic.

**When to Use**: When you want to achieve a clear separation between user interface and business logic in your application.

Exception Handling
-------------------

**Description**: Exception Handling is a technique used to manage and process errors and exceptions in an application. Using custom exception classes and a centralized error handler, you can manage errors systematically.

**Used In**:
- `GlobalExceptionHandler.java` - Handles exceptions globally across the application.
- `ProductNotFoundException.java`, `ProductNotValidException.java` - Custom exception classes.

**When to Use**: When you want to manage and handle application errors and exceptions in a centralized manner.

Caching
--------

**Description**: Caching is a technique used to speed up data access by storing frequently accessed or computed data in memory. This improves performance by reducing the need for repeated data retrieval or computation.

**Used In**:
- `CacheConfiguration.java` - Manages caching configuration.

**When to Use**: When you want to enhance performance by caching frequently accessed or computed data.


Learn More
----------

This project and its configurations are based on the training series:

**Java Spring Boot [Mid 2024]** by Peachez Programming.

For more details, visit the [YouTube Playlist](https://youtube.com/playlist?list=PL7TZZ2ip0DRCmJ57pzkc3EChRTJ6pm_bH&si=cnoMoUZSg8hxAW7V).
