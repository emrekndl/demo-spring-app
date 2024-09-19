========================================
Final Exam Project - Product Management
========================================

Overview
========
This is a Spring Boot-based REST API for managing products. The application supports basic CRUD (Create, Read, Update, Delete) operations for products, including category management, search functionalities, validation, external API integration for profanity filtering, and robust security with JWT authentication.


Features
========
- **Product Management**: Create, update, read, and delete products.
- **Category Management**: Products are associated with unique categories (Many Products : 1 Category).
- **Search Products**: 
  - Query products by `name`, `description`, and `category`.
  - Order by `price` or `alphabetical` order.
  - Limit search results to the first 10 items.
  - Cache product search results for 5 minutes to enhance performance.
- **Security**: 
  - Only authenticated users can create, update, or delete products.
  - All users, including non-authenticated users, can retrieve products.
  - Only the superuser can delete products.
- **Profanity Filter**: Integrated with an external Profanity Filter API to ensure that product names and descriptions do not contain offensive language.
- **Logging**: All actions and exceptions are logged for tracking and debugging.
- **Thorough Testing**: Includes unit tests for each logical flow, adhering to TDD.

Setup
=====
To get started with the project, follow these steps:

1. **Clone the repository**:

.. code-block:: bash

   git clone <repository-url>
   cd final-exam

2. **Build the project**:

.. code-block:: bash

    ./mvnw clean install

3. **Run the project**:

.. code-block:: bash

    ./mvnw spring-boot:run

Database Setup:
===============

- The project uses MySQL as the database.
- Update the database connection details in the ``application.properties`` file.
- Use Liquibase for database migrations (configured via ``liquibase.properties`` and ``db.changelog-master.xml``).

API Endpoints:
==============

Create Product:
---------------
- Endpoint: ``POST /products``
- Authenticated users only.
- Uses ``ProductValidator`` to ensure all required fields are valid.
- The product name and description are validated against the Profanity Filter API.
- Request Body:
- http -vv get 0.0.0.0:8080/api/products

.. code-block:: json

    {
        "_embedded": {
            "productDTOList": [
                {
                    "category": {
                        "name": "Automotive"
                    },
                    "description": "High-quality a car",
                    "id": "ce981265-f48b-4c8e-a424-320bd3891499",
                    "manufacturer": "AutoTech",
                    "name": "Tesla Model Y DAMN",
                    "price": 19.99,
                    "region": "US"
                },
                {
                    "category": {
                        "name": "Electronics"
                    },
                    "description": "High-quality wireless headphones",
                    "id": "e58b3ea9-08cb-4be8-9d46-ba49bef95049",
                    "manufacturer": "AudioTech",
                    "name": "wireless headphones",
                    "price": 19.99,
                    "region": "US"
                }
            ]
        },
        "_links": {
            "self": {
                "href": "http://0.0.0.0:8080/api/products?page=0&size=10"
            }
        },
        "page": {
            "number": 0,
            "size": 10,
            "totalElements": 2,
            "totalPages": 1
        }
    }

Read Product:
-------------

- Get a specific product by ID:
  - Endpoint: ``GET /products/{id}``
  - Publicly accessible.
  
- Search products with optional filters:
- Endpoint: ``GET /products/search``
- Query Parameters:
- ``name``, ``description``, ``category``, ``orderBy=price|abc``, ``page=0``, ``size=10``, ``direction=asc|desc``.

Update Product:
---------------

- Endpoint: ``PUT /products/{id}``
- Authenticated users only.
- Profanity Filter is used for validation.

Delete Product:
---------------

- Endpoint: ``DELETE /products/{id}``
- Only the superuser can delete products.

Get Categories:
---------------

- Endpoint: ``GET /categories``
- Publicly accessible.

Project Structure:
==================

.. code-block::

    ./final-exam
    ├── compose-mysql.yaml
    ├── db-data
    │   └── mysql
    ├── LiquibaseGuide.rst
    ├── liquibase.properties
    ├── mvnw
    ├── mvnw.cmd
    ├── pom.xml
    ├── src
    │   ├── main
    │   │   ├── java
    │   │   │   └── com
    │   │   │       └── example
    │   │   │           └── demo
    │   │   │               └── finalexam
    │   │   │                   ├── CacheConfiguration.java
    │   │   │                   ├── Configuration.java
    │   │   │                   ├── exceptions
    │   │   │                   │   ├── CategoryNotFoundException.java
    │   │   │                   │   ├── ErrorMessages.java
    │   │   │                   │   ├── ErrorResponse.java
    │   │   │                   │   ├── GlobalExceptionHandler.java
    │   │   │                   │   ├── ProductNotFoundException.java
    │   │   │                   │   └── ProductNotValidException.java
    │   │   │                   ├── FinalExamApplication.java
    │   │   │                   ├── helpers
    │   │   │                   │   └── SortingAndPagingHelper.java
    │   │   │                   ├── ICommand.java
    │   │   │                   ├── IQuery.java
    │   │   │                   ├── product
    │   │   │                   │   ├── controllers
    │   │   │                   │   │   ├── CategoryController.java
    │   │   │                   │   │   └── ProductController.java
    │   │   │                   │   ├── dto
    │   │   │                   │   │   ├── CategoryDTO.java
    │   │   │                   │   │   ├── ProductDTO.java
    │   │   │                   │   │   ├── ProductQueryParams.java
    │   │   │                   │   │   └── ProductSearchParams.java
    │   │   │                   │   ├── model
    │   │   │                   │   │   ├── BaseProduct.java
    │   │   │                   │   │   ├── Category.java
    │   │   │                   │   │   ├── Product.java
    │   │   │                   │   │   ├── Region.java
    │   │   │                   │   │   └── UpdateProductCommand.java
    │   │   │                   │   ├── repository
    │   │   │                   │   │   ├── ICategoryRepository.java
    │   │   │                   │   │   └── IProductRepository.java
    │   │   │                   │   ├── services
    │   │   │                   │   │   ├── CreateCategoryService.java
    │   │   │                   │   │   ├── CreateProductService.java
    │   │   │                   │   │   ├── DeleteProductService.java
    │   │   │                   │   │   ├── GetCategoriesService.java
    │   │   │                   │   │   ├── GetProductService.java
    │   │   │                   │   │   ├── GetProductsService.java
    │   │   │                   │   │   ├── SearchProductService.java
    │   │   │                   │   │   └── UpdateProductService.java
    │   │   │                   │   ├── specification
    │   │   │                   │   │   ├── ProductSpecificationBuilder.java
    │   │   │                   │   │   ├── ProductSpecification.java
    │   │   │                   │   │   ├── SearchCriteria.java
    │   │   │                   │   │   └── SearchOperation.java
    │   │   │                   │   └── validators
    │   │   │                   │       └── ProductValidator.java
    │   │   │                   ├── profanityfilter
    │   │   │                   │   ├── ProfanityFilterResponse.java
    │   │   │                   │   └── ProfanityFilterService.java
    │   │   │                   └── security
    │   │   │                       ├── CreateNewUserController.java
    │   │   │                       ├── CreateNewUserService.java
    │   │   │                       ├── CustomUserDetailsService.java
    │   │   │                       ├── CustomUser.java
    │   │   │                       ├── ICustomUserRepository.java
    │   │   │                       ├── jwt
    │   │   │                       │   ├── JwtAuthenticationFilter.java
    │   │   │                       │   ├── JwtUtil.java
    │   │   │                       │   └── LoginController.java
    │   │   │                       ├── SecurityConfiguration.java
    │   │   │                       └── SecurityController.java
    │   │   └── resources
    │   │       ├── application.properties
    │   │       ├── db
    │   │       │   └── changelog
    │   │       │       └── db.changelog-master.xml
    │   │       ├── static
    │   │       └── templates
    │   └── test
    │       └── java
    │           └── com
    │               └── example
    │                   └── demo
    │                       └── finalexam
    │                           ├── FinalExamApplicationTests.java
    │                           └── product
    │                               └── services
    │                                   ├── CreateProductServiceTest.java
    │                                   ├── DeleteProductServiceTest.java
    │                                   ├── GetProductServiceTest.java
    │                                   ├── GetProductsServiceTest.java
    │                                   ├── SearchProductServiceTest.java
    │                                   └── UpdateProductServiceTest.java
    └── target


Technologies Used:
==================

- Spring Boot
- Spring Data JPA
- MySQL
- Liquibase
- JWT for authentication
- Profanity Filter API for name/description validation
- Caching for enhanced performance
- JUnit for testing

