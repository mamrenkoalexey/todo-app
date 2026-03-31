# todo-app

Simple REST API for managing tasks. Built with Spring Boot + H2 in-memory database.

## Stack

- Java 17
- Spring Boot 4.0.5
- Spring Data JPA / H2
- Maven
- SpringDoc OpenAPI
- JUnit 5 + Mockito

## Run

```bash
./mvnw spring-boot:run
```

App starts on `http://localhost:8080`.

## API Documentation 

[Swagger](http://localhost:8080/swagger-ui/index.html)

[OpenAPI JSON](http://localhost:8080/v3/api-docs)


## Endpoints

| Method | URL | What it does |
|--------|-----|--------------|
| POST | `/api/tasks` | create task |
| GET | `/api/tasks` | get all tasks |
| GET | `/api/tasks/{id}` | get task by id |
| PUT | `/api/tasks/{id}` | update task |
| DELETE | `/api/tasks/{id}` | delete task |

Task status can be: `NEW`, `IN_PROGRESS`, `DONE`

## Examples

Create:
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title": "create test task", "status": "NEW"}'
```

Update status:
```bash
curl -X PUT http://localhost:8080/api/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{"title": "update test task", "status": "DONE"}'
```

## Tests
The project now includes unit tests for the Service layer.