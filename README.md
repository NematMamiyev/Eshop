**About the Project**
The E-Shop project is a modern online shopping platform that offers users a convenient shopping experience across various product categories.
The project is built using Spring Boot technology, ensuring users can easily search for, select, and order products.
Each product has different size and color variants, allowing customers to easily choose the product according to their preferences.
There are two main types of users in the E-Shop project: Customer and Employee. Customer users can only shop, and simple registration
and login APIs are available for them. Employee users have different roles: SUPERADMIN, ADMIN, and OPERATOR, providing them with various
rights and privileges in the system. Security for users is ensured, and there are separate authentication and authorization systems for
Customers and Employees. This project is built as a monolithic application, but there are plans to migrate to microservices for a more
flexible and scalable architecture in the future. The project leverages the power of Spring Boot to provide a high-performance and scalable platform.

**Functionalities**
1.Product Management
Add, update, and delete products.
Manage size and color variants of products.
2.Order Management
Customers can add products to the cart and place orders.
Track the status of orders.
3.User Management
Customer registration and login.
Different roles for Employees (SUPERADMIN, ADMIN, OPERATOR) with different rights.
4.Security and Authentication
JWT token-based authentication.
Separate APIs for Customers and Employees.
5.Global Exception Handling
Centralized management of errors in the system.

**Technologies and Libraries**
Java 17: Main programming language.
Spring Boot 3.3.2: Java-based framework for rapid application development and easy configuration.
Spring Data JPA: ORM and database operations.
Spring Security: For security and authentication.
JWT (io.jsonwebtoken): JSON Web Token-based authentication.
Redis: Database for caching.
MapStruct: For automatic mapping of DTOs.
Lombok: To optimize code and reduce repetition.
SpringDoc OpenAPI: API documentation.
Log4j2: Logging.
Oracle JDBC (ojdbc11): For database connectivity with Oracle.

**Module Design**
```
src
├── main
│   ├── java
│   │   └── az.orient.eshop
│   │       ├── config             # Configuration classes (e.g., database, security, etc.)
│   │       ├── controller         # Classes to handle HTTP requests and route to service methods.
│   │       ├── dto                # Data Transfer Object (DTO) for incoming and outgoing data.
│   │       │   ├── request        # DTOs for incoming data (e.g., POST/PUT request bodies).
│   │       │   └── response       # DTOs for outgoing data (e.g., GET/POST response).
│   │       ├── entity             # Database entities (tables).
│   │       ├── enums              # Enum types used in the project.
│   │       ├── exception          # Custom exception classes for error handling.
│   │       ├── mapper             # Classes for object transformation logic (e.g., MapStruct).
│   │       ├── repository         # Interfaces for database operations, extending JPA repositories.
│   │       ├── security           # Security configuration, authentication, and authorization logic.
│   │       ├── service            # Service classes managing business logic.
│   │       └── validation         # Custom validation logic and annotations for input data.
│   └── resources
│       ├── application.properties # Application configuration file (e.g., database, security, etc.)
│       └── log4j2.xml             # Configuration for logging with log4j.
└── test
└── java
└── az.orient.eshop        # Unit and integration test classes.

Entities:
Employee, Customer, Product, Order, ProductImage, Brand, Cart, Category, Color, OrderStatus, Payment, ProductDetails,
ProductVideo, Shelf, ShelfProductDetails, Size, Subcategory, Warehouse, WarehouseWork, Wishlist.

Repositories:
Used for database operations.

Service and ServiceImpl:
Contains business logic.

Controllers:
Defines the REST API interface.

GlobalException:
Centralized exception handling for the system.

Swagger UI:
APIs are documented using OpenAPI. The Swagger interface allows testing of REST APIs.

# **APIs**

- **BrandController**
    - POST /brands: Add a new brand.
    - GET /brands: Get the list of all brands.
    - GET /brands/{id}: Get information about a specific brand.
    - PUT /brands/{id}: Update an existing brand.
    - DELETE /brands/{id}: Delete a brand.

- **CartController**
    - GET /carts: Get products in the customer's cart.
    - POST /carts/add/{productDetailsId}: Add a product to the cart.
    - DELETE /carts/delete/{productDetailsId}: Remove a product from the cart.

- **CategoryController**
    - POST /categories: Add a new category.
    - GET /categories: Get the list of all categories.
    - GET /categories/{id}: Get information about a specific category.
    - PUT /categories/{id}: Update an existing category.
    - DELETE /categories/{id}: Delete a category.

- **ColorController**
    - POST /colors: Add a new color.
    - GET /colors: Get the list of all colors.
    - GET /colors/{id}: Get information about a specific color.
    - PUT /colors/{id}: Update an existing color.
    - DELETE /colors/{id}: Delete a color.

- **CustomerAuthController**
    - POST /auth/customer/login: Customer login.
    - POST /auth/customer/logout: Customer logout (with authorization token).

- **CustomerController**
    - POST /customers/register: Register a new customer.
    - GET /customers: Get the list of all customers.
    - GET /customers/{id}: Get information about a specific customer.
    - PUT /customers/{id}: Update customer details.
    - DELETE /customers/{id}: Delete a customer.

- **EmployeeAuthController**
    - POST /auth/employee/login: Employee login.
    - POST /auth/employee/logout: Employee logout (with authorization token).

- **EmployeeController**
    - POST /employees: Add a new employee.
    - GET /employees: Get the list of all employees.
    - GET /employees/{id}: Get information about a specific employee.
    - PUT /employees/{id}: Update employee details.
    - DELETE /employees/{id}: Delete an employee.

- **ImageController**
    - POST /images/{productDetailsId}: Add an image to a product.
    - DELETE /images/list/{productDetailsId}: Delete product images.
    - DELETE /images/{imageId}: Delete a specific image.
    - GET /images/list/{productDetailsId}: Get product images.
    - GET /images/{imageId}: Get a specific image.

- **OrderController**
    - GET /orders: Get the customer's orders.

- **OrderStatusController**
    - GET /orderstatus/{orderId}: Get the status of an order.

- **PaymentController**
    - POST /payment/{paymentMethod}: Make a payment.

- **ProductController**
    - POST /products: Add a new product.
    - GET /products: Get the list of all products.
    - GET /products/{id}: Get information about a specific product.
    - PUT /products/{id}: Update a product.
    - DELETE /products/{id}: Delete a product.

- **ProductDetailsController**
    - POST /productdetails: Add product details.
    - PUT /productdetails/{id}: Update product details.
    - GET /productdetails: Get the list of all product details.
    - GET /productdetails/{id}: Get information about a specific product detail.
    - DELETE /productdetails/{id}: Delete product details.

- **ShelfController**
    - POST /shelfs: Add a new shelf.
    - GET /shelfs: Get the list of all shelves.
    - GET /shelfs/{id}: Get information about a specific shelf.
    - PUT /shelfs/{id}: Update a shelf.
    - DELETE /shelfs/{id}: Delete a shelf.

- **ShelfProductDetailsController**
    - POST /shelfproducts: Add a product to a shelf.
    - DELETE /shelfproducts: Remove a product from a shelf.

- **SizeController**
    - POST /size: Add a new size.
    - GET /size: Get the list of all sizes.
    - GET /size/{id}: Get information about a specific size.
    - PUT /size/{id}: Update a size.
    - DELETE /size/{id}: Delete a size.

- **SubcategoryController**
    - POST /subcategorys: Add a new subcategory.
    - GET /subcategorys: Get the list of all subcategories.
    - GET /subcategorys/{id}: Get information about a specific subcategory.
    - PUT /subcategorys/{id}: Update a subcategory.
    - DELETE /subcategorys/{id}: Delete a subcategory.

- **VideoController**
    - POST /videos/{productDetailsId}: Add videos for product details.
    - DELETE /videos/list/{productDetailsId}: Delete videos for product details.
    - DELETE /videos/{videoId}: Delete a specific video.
    - GET /videos/list/{productDetailsId}: Get videos for product details.
    - GET /videos/{videoId}: Get a specific video.

- **WarehouseController**
    - POST /warehouses: Add a new warehouse.
    - GET /warehouses: Get the list of all warehouses.
    - GET /warehouses/{id}: Get information about a specific warehouse.
    - PUT /warehouses/{id}: Update a warehouse.
    - DELETE /warehouses/{id}: Delete a warehouse.

- **WarehouseWorkController**
    - GET /warehouseworks: Get the list of all warehouse works.
    - PUT /warehouseworks/handlework/{id}: Manage a warehouse task.

- **WishlistController**
    - GET /wishlists: Get all wishlisted products for a customer.
    - POST /wishlists: Add a product to the wishlist.
    - DELETE /wishlists: Remove a product from the wishlist.

# **Running the Project**

**Requirements:**
- JDK 17
- Maven 3.8+
- Oracle Database

**Steps:**
1. **Configure**  
   Configure the `application.properties` file in the project to provide necessary details for the database, Redis, and JWT.

2. **Clone the Project**
   ```bash
   git clone https://github.com/NematMamiyev/Eshop.git  
   cd eshop
3.**Install Maven Dependencies**
mvn clean install
4.**Run the Project**
mvn spring-boot:run
Access Swagger and test the APIs.
5.**Swagger URL:**
http://localhost:8082/swagger-ui/index.html

**Possible Future Enhancements**
Complete unit test coverage.
Full migration to microservices.
Flexible filter systems in product catalogs.
Contact
If you have any questions, feel free to reach out:

Email: nemet.memiyev1@gmail.com
GitHub: https://github.com/NematMamiyev