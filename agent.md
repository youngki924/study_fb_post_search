# REST API Project Guidance

## Goal

Build a production-ready REST API with Spring Boot. Clarify the domain model,
authentication requirements, persistence choice, and external integrations before
adding feature-specific code.

## Platform Baseline

- Use the latest stable Spring Boot release: `4.1.1`.
- Use OpenJDK `26.0.2.1` installed at `C:\\Users\\y\\.jdks\\openjdk-26.0.2.1`.
  Spring Boot 4.1.1 requires Java 17 or later and supports Java 26.
- Use Maven unless the repository already uses Gradle.
- Let Spring Boot manage dependency versions; do not pin versions for managed
  dependencies unless there is a documented compatibility or security reason.
- Prefer Spring MVC with `spring-boot-starter-web` for conventional REST endpoints.

## API Conventions

- Use resource-oriented, plural paths under `/api/v1`.
- Use the appropriate HTTP status codes and include `Location` for successful
  resource creation.
- Keep controllers thin. Put use-case logic in services and persistence logic in
  repositories.
- Do not expose JPA entities directly; use request and response DTOs.
- Validate request DTOs with Jakarta Bean Validation and return a consistent,
  RFC 9457 Problem Details error response.
- Make pagination, sorting, and filtering explicit for collection endpoints.
- Document public endpoints with OpenAPI and keep examples current.

## Security and Configuration

- Treat every endpoint as authenticated by default; explicitly allow only intended
  public endpoints.
- Store secrets in environment variables or an approved secret manager. Never
  commit secrets, tokens, private keys, or production connection strings.
- Keep environment-specific values outside code using configuration properties and
  profile-specific configuration.
- Add authorization checks at the service boundary, not only in controllers.

## Data and Quality

- Use database migrations (Flyway preferred) for every schema change; never rely
  on automatic production schema updates.
- Define transaction boundaries in the service layer.
- Add unit tests for business logic and integration tests for controllers,
  persistence, validation, and security behavior.
- Run the project test suite and formatter/linter, when configured, before
  considering a change complete.
- Enable Actuator health probes and use structured, secret-safe logging.

## Workflow

- Inspect the existing code and build setup before making changes; preserve
  established conventions unless a deliberate migration is requested.
- Keep changes focused and avoid unrelated refactors.
- Update this file when platform choices or project conventions change.
