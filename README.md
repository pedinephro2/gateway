# Gateway — API Gateway

Central entry point for all client requests. Routes traffic to the appropriate backend microservice, handles CORS, and integrates with Eureka for service discovery.

## Tech Stack

- Java 17, Spring Boot 3.4.2
- Spring Cloud Gateway
- Spring Cloud Netflix Eureka Client
- Spring Cloud Config Client

## Port

`8085`

## Routes

| Path Pattern | Target Service | Port |
|---|---|---|
| `/api/auth/**`, `/api/users/**`, `/api/admin/**` | microservice1 | 8081 |
| `/api/medical-records/**`, `/api/patients/**`, `/api/ml/**` | microservice2 | 8082 |
| `/api/schedules/**` | microservice4 | 8084 |
| `/vital-signs/**` | vital-signs-service | 8086 |
| `/lab-results/**` | lab-results-service | 9003 |
| `/medical-media/**` | medical-media-service | 8090 |
| `/transplant-mongo/**` | transplant-service | 3001 |
| `/ml-rejet/**` | ml-rejet | 8001 |
| `/hospitalisation/**` | hospitalisation | 8089 |

Routes are configured via `application-docker.properties` and can be overridden with environment variables at runtime (e.g. `SPRING_CLOUD_GATEWAY_ROUTES_0_URI`).

## CORS

CORS is configured in `CorsConfig.java` to allow all origins with credentials. This covers all routes via a `CorsWebFilter`.

## Environment Variables

| Variable | Description |
|---|---|
| `SPRING_PROFILES_ACTIVE` | Set to `docker` to use Docker-specific routes |
| `SPRING_CLOUD_GATEWAY_ROUTES_N_*` | Override any route at runtime without rebuilding |

## Running Locally

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=docker
```

## Docker

```bash
docker build -t azizos07/gateway:latest .
docker run -p 8085:8085 \
  -e SPRING_PROFILES_ACTIVE=docker \
  azizos07/gateway:latest
```
