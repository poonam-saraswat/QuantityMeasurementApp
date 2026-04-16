# Quantity Measurement App

This log documents the daily progress of tasks completed during the Quantity Measurement App development.

---

## Folder Structure

```
QuantityMeasurementApp/
|
+-- src/
|   +-- main/
|   |   +-- java/com/app/quantitymeasurement/
|   |   |   +-- controller/        (REST Controllers)
|   |   |   +-- core/              (Quantity<U> generic class)
|   |   |   +-- dto/               (Request & Response DTOs)
|   |   |   +-- entity/            (JPA Entities)
|   |   |   +-- exeption/          (Global Exception Handler)
|   |   |   +-- model/             (Unit Enums & IMeasurable)
|   |   |   +-- repository/        (Spring Data JPA Repository)
|   |   |   +-- security/          (JWT & Security Config)
|   |   |   +-- service/           (Service Interface & Impl)
|   |   |   +-- Application.java   (Spring Boot Entry Point)
|   |   +-- resources/
|   |       +-- application.properties
|   +-- test/
|       +-- java/com/app/quantitymeasurement/
|           +-- controller/        (MockMvc Controller Tests)
|           +-- ApplicationTests.java  (Full Integration Tests)
|
+-- data/
|   +-- quantitydb.mv.db           (H2 File-Based Database)
|
+-- pom.xml                        (Maven Configuration)
+-- README.md
```

---

###  Feet & Inches Equality — UC1 & UC2

Created the repository and set up the project structure. Started implementing the Quantity Measurement System using Test Driven Development (TDD). Addressed **UC1** (Feet Equality) to handle feet measurement comparisons using a dedicated `Feet` class with equality logic. Extended functionality to support **UC2** (Inches Equality), allowing comparison of inch values. Both units achieved 100% test coverage at this stage.

---

###  Generic Quantity Class, Yards & Centimeters — UC3 & UC4

Refactored the code to eliminate duplication by introducing a generic `Quantity` class and a `LengthUnit` enum (**UC3**), applying the DRY principle. The enum encapsulates each unit's conversion factor to the base unit (inches). Extended the system to support `YARDS` and `CENTIMETERS` (**UC4**) with comprehensive test coverage for cross-unit comparisons such as 1 YARD == 3 FEET and 2 INCHES == 5.08 CENTIMETERS.

---

###  Unit-to-Unit Conversion — UC5

Implemented unit-to-unit conversions between different length units (**UC5**). Added `convert()` and `convertTo()` methods to the `Quantity` class. Conversion logic centralizes on converting the source value to the base unit (inches) first and then converting out to the target unit. Ensured robust input validation (null unit, non-finite value) and floating-point precision compatibility across comprehensive test cases.

---


###  Addition, Weight Category & Generic Bounds — UC6 to UC10

Expanded system capabilities significantly across five use cases:

- **UC6** — Added `add()` method to `Quantity` to sum two length quantities, returning the result in the unit of the first operand.
- **UC7** — Extended `add()` to accept an explicit `targetUnit` parameter, allowing the result to be expressed in any compatible unit.
- **UC8** — Refactored `LengthUnit` to implement the `IMeasurable` interface, improving entity cohesion and preparing for multi-category support.
- **UC9** — Introduced the `WeightUnit` category (`MILLIGRAM`, `GRAM`, `KILOGRAM`, `POUND`, `TONNE`) with gram as the base unit, following the same `IMeasurable` contract.
- **UC10** — Refactored the entire architecture to a fully generic `Quantity<U extends IMeasurable>` class, eliminating categorical duplication and enabling any unit type to participate in the same arithmetic and conversion pipeline.

---

###  Volume, Subtraction, Division & Temperature — UC11 to UC14

Extended capabilities across four further use cases:

- **UC11** — Added the `VolumeUnit` category (`LITRE`, `MILLILITRE`, `GALLON`) with litre as the base unit.
- **UC12** — Added `subtract()` and `divide()` methods. Subtraction returns a `Quantity<U>` in the target unit; division returns a raw `double` ratio.
- **UC13** — Refactored all arithmetic logic into a centralized `ArithmeticOperation` private enum inside `Quantity`, with `ADD`, `SUBTRACT`, and `Divide` variants each implementing a shared `compute(double, double)` method. This maintains the DRY principle across all operations.
- **UC14** — Added `TemperatureUnit` (`CELSIUS`, `FAHRENHEIT`, `KELVIN`) with non-linear conversion formulas (not a simple multiplication factor). Introduced a `supportArithmetic()` method inside `IMeasurable` (default: `true`) overridden to `false` in `TemperatureUnit`, cleanly disabling add/subtract/divide for temperature while keeping compare and convert fully functional.

---

###  N-Tier Architecture — UC15

Refactored the application to a proper **N-Tier Architecture** by introducing distinct Controller, Service, Repository, and DTO layers:

- `QuantityMeasurementController` — drives application flow and delegates to the service.
- `IQuantityMeasurementService` — defines the service contract (compare, convert, add, subtract, divide).
- `QuantityDTO` — acts as the data transfer object between layers, decoupling the API surface from internal models.
- An in-memory `List`-based repository was introduced at this stage to simulate persistence.

This separation of concerns significantly improves maintainability and scalability.

---

### JDBC Database Persistence — UC16

Integrated a **JDBC Database Repository** to persist all quantity measurement operations. Replaced the in-memory repository with a `QuantityMeasurementDatabaseRepository` backed by a real SQL database. Introduced a `ConnectionPool` for efficient connection management and an `ApplicationConfig` class for centralized JDBC configuration (driver, URL, credentials). All five operations — compare, convert, add, subtract, divide — are now saved to and retrieved from the database, enabling operation history to survive application restarts.

---


### Spring Boot REST Service & Spring Data JPA — UC17

Migrated the application to a full **Spring Boot REST Service** by leveraging the Spring Framework ecosystem:

- Replaced raw JDBC with **Spring Data JPA** backed by an **H2 file-based database** (`./data/quantitydb`). The `IQuantityMeasurementRepository` extends `JpaRepository` and provides custom query methods (`findByOperation`, `findByThisMeasurementType`, `findByIsError`, `countByOperationAndIsErrorFalse`).
- Exposed all operations as **RESTful HTTP endpoints** via `QuantityMeasurementController` mapped to `/api/measurements/`.
- Added three additional history and reporting GET endpoints: `/history/type/{type}`, `/history/errored`, and `/count/{operation}`.
- Introduced `QuantityMeasurementDTO` and `QuantityMeasurementEntity` to cleanly decouple the REST layer from the JPA persistence layer. Every operation — including failures — is saved as a `QuantityMeasurementEntity` row with timestamps (`createdAt`, `updatedAt`) auto-managed via `@PrePersist` and `@PreUpdate`.
- Configured **Spring Security** with a `SecurityFilterChain` (CSRF disabled, stateless session).
- Added centralized exception handling via `@RestControllerAdvice` (`GlobalExceptionHandler`) covering `@Valid` failures (400), domain errors (400), unsupported operations (400), unmapped paths (404), and all unhandled exceptions (500).
- Integrated **Swagger / OpenAPI** (`springdoc-openapi 2.5.0`) for interactive API documentation, accessible at `/swagger-ui.html` without authentication.
- Wrote **MockMvc controller-layer unit tests** (`@WebMvcTest` + `@MockBean`) and **full integration tests** (`@SpringBootTest(RANDOM_PORT)` + `TestRestTemplate`) covering all endpoints. All tests pass.

---

###  JWT Authentication — UC18

Integrated **JWT (JSON Web Token)** authentication to secure all REST API measurement endpoints:

- Implemented `AuthController` with `/auth/signup` and `/auth/login` endpoints. Signup encodes the password with `BCryptPasswordEncoder` and saves the user to an H2-backed `users` table via `UserRepository`. Login validates credentials and returns a signed JWT token.
- Added a `JwtFilter` (extends `OncePerRequestFilter`) that intercepts every request, extracts the `Authorization: Bearer <token>` header, validates the token using `JwtUtil`, and sets the `SecurityContext` when valid.
- Configured `SecurityConfig` (`@Profile("!test")`) to permit `/auth/**`, `/swagger-ui/**`, `/api-docs/**`, and `/h2-console/**` while requiring authentication for all `/api/measurements/**` endpoints.
- Configured full **CORS support** allowing all origins, standard HTTP methods (`GET`, `POST`, `PUT`, `DELETE`, `OPTIONS`, `PATCH`), and required headers including `Authorization`, with a 1-hour preflight cache.
- Fixed a duplicate username bug on signup: previously caused an unhandled DB constraint violation (HTTP 500); now returns a descriptive HTTP 400 response before attempting to save.
- Fixed invalid login handling: previously threw a `RuntimeException` that surfaced as HTTP 500; now returns HTTP 401 with a clear error message.
- JWT tokens are signed with **HMAC-SHA256** and expire after **1 hour**.

---

## API Reference

### Authentication Endpoints (Public)

| Method | Endpoint | Description |
|---|---|---|
| POST | `/auth/signup` | Register a new user |
| POST | `/auth/login` | Login and receive a JWT token |

### Measurement Endpoints (Require `Authorization: Bearer <token>`)

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/measurements/compare` | Compare two quantities |
| POST | `/api/measurements/convert` | Convert a quantity to another unit |
| POST | `/api/measurements/add` | Add two quantities |
| POST | `/api/measurements/subtract` | Subtract two quantities |
| POST | `/api/measurements/divide` | Divide two quantities (returns scalar) |
| GET | `/api/measurements/history` | All operation records |
| GET | `/api/measurements/history/{operation}` | Filter by operation type |
| GET | `/api/measurements/history/type/{type}` | Filter by measurement type |
| GET | `/api/measurements/history/errored` | All error records |
| GET | `/api/measurements/count/{operation}` | Count of successful operations |

### Supported Units

| Category | Units | Base Unit | Arithmetic |
|---|---|---|---|
| `LengthUnit` | `FEET`, `INCHES`, `YARDS`, `CENTIMETERS` | Inches | Supported |
| `WeightUnit` | `MILLIGRAM`, `GRAM`, `KILOGRAM`, `POUND`, `TONNE` | Gram | Supported |
| `VolumeUnit` | `LITRE`, `MILLILITRE`, `GALLON` | Litre | Supported |
| `TemperatureUnit` | `CELSIUS`, `FAHRENHEIT`, `KELVIN` | Celsius | Not Supported |

---

## Running the Application

**Prerequisites:** Java 17, Maven 3.6+

```bash
cd quantity-measurement-app
./mvnw spring-boot:run
```

### Running Tests

```bash
./mvnw test
```

---

## Useful URLs

| URL | Description |
|---|---|
| `http://localhost:8080/swagger-ui.html` | Swagger UI — interactive API docs |
| `http://localhost:8080/api-docs` | Raw OpenAPI JSON |
| `http://localhost:8080/h2-console` | H2 database browser |
| `http://localhost:8080/actuator/health` | Application health check |

> H2 Console — JDBC URL: `jdbc:h2:file:./data/quantitydb` · User: `sa` · Password: *(blank)*
