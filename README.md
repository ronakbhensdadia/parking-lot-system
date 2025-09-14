# Parking Lot Management System

This is a comprehensive Parking Lot Management System built with Java and Spring Boot. It provides REST APIs to manage vehicle entry/exit, dynamic slot allocation, fee calculation, and payments. The system is secured using Google OAuth2 and supports role-based access for Users and Admins.

## Features

-   **Role-Based Access Control**: `USER` role for parking/exiting vehicles and `ADMIN` role for system configuration.
-   **Google OAuth2 Login**: Secure authentication for all API endpoints.
-   **Multi-Floor Parking**: Supports multiple floors with different slot configurations.
-   **Vehicle Type Support**: Manages different vehicle types (`CAR`, `BIKE`, `TRUCK`) with type-specific slots.
-   **Dynamic Slot Allocation**: Implements a strategy pattern (default: nearest first) to assign available slots.
-   **Concurrency Handling**: Uses pessimistic locking (`SELECT ... FOR UPDATE`) to prevent double allocation of slots during concurrent entry requests.
-   **Transactional Operations**: Ensures atomicity for critical operations like payment and slot de-allocation using `@Transactional`.
-   **Dynamic Pricing**: Admins can configure pricing rules based on vehicle type.
-   **Full API Documentation**: Integrated Swagger UI for easy API exploration and testing.
-   **In-Memory Database**: Uses H2 for easy setup and development.

## Tech Stack

-   **Java 8**
-   **Spring Boot 2.3.5.RELEASE**
-   **Spring Data JPA**: For database interaction.
-   **Spring Security & OAuth2 Client**: For authentication and authorization.
-   **H2 Database**: In-memory relational database.
-   **Maven**: Build automation tool.
-   **Swagger (Springfox)**: API documentation.
-   **Lombok**: To reduce boilerplate code.

## Setup and Run

### Prerequisites

-   Java 8 JDK
-   Maven
-   A Google Account to create OAuth2 credentials.

### Configuration

1.  **Google OAuth2 Credentials**:
    -   Go to the [Google Cloud Console](https://console.cloud.google.com/).
    -   Create a new project.
    -   Go to "APIs & Services" -> "Credentials".
    -   Create an "OAuth 2.0 Client ID" of type "Web application".
    -   Add `http://localhost:8080/login/oauth2/code/google` to the "Authorized redirect URIs".
    -   Copy the generated **Client ID** and **Client Secret**.

2.  **Application Properties**:
    -   Open `src/main/resources/application.properties`.
    -   Paste your Client ID and Secret:
        ```properties
        spring.security.oauth2.client.registration.google.client-id=YOUR_CLIENT_ID
        spring.security.oauth2.client.registration.google.client-secret=YOUR_CLIENT_SECRET
        ```
    -   Set your admin email address. This user will be granted the `ADMIN` role upon login.
        ```properties
        parking-lot.admin.email=your-admin-email@gmail.com
        ```

### Running the Application

1.  **Clone the repository**:
    ```bash
    git clone <repository-url>
    cd parking-lot-system
    ```

2.  **Build and Run using Maven**:
    ```bash
    ./mvnw spring-boot:run
    ```
    The application will start on `http://localhost:8080`.

## Accessing the System

-   **API Documentation (Swagger)**: `http://localhost:8080/swagger-ui/`
-   **H2 Database Console**: `http://localhost:8080/h2-console`
    -   **JDBC URL**: `jdbc:h2:mem:parkinglotdb`
    -   **Username**: `sa`
    -   **Password**: `password`

## API Authentication Flow

1.  **Browser**: To authenticate, simply navigate to any protected endpoint (e.g., `http://localhost:8080`). You will be redirected to Google's login page. After successful authentication, a session is created.

2.  **Postman/API Client**:
    -   Get JSESSIONID after successful authentication
    -   Pass JSESSIONID in request header for successful authorization

## API Endpoints

### User Endpoints (`/api/v1/parking`)

-   `POST /entry`: Park a vehicle.
-   `POST /exit/{ticketNumber}`: Calculate parking fee.
-   `POST /payment/{ticketNumber}`: Process payment and free the slot.
-.  `GET /ticket/{ticketNumber}`: Get details of a specific ticket.

### Admin Endpoints (`/api/v1/admin`)

-   `PUT /pricing`: Add or update a pricing rule for a vehicle type.
-   `POST /slots`: Add a new parking slot to a specific floor.
-   `POST /floors`: Add a new parking floor.

For detailed request/response models, please refer to the Swagger UI.
