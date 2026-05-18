# Award Intervals API

REST API to query producers with the longest and shortest interval between two consecutive Worst Picture awards from the Golden Raspberry Awards.

## Requirements

- Java 21
- Maven Wrapper included in the project

## How to Run Tests

```bash
MAVEN_USER_HOME=.m2 ./mvnw test
```

## How to Run the Application

```bash
MAVEN_USER_HOME=.m2 ./mvnw spring-boot:run
```

The application runs on:

```text
http://localhost:8080
```

## Endpoints

### Get Award Intervals

```http
GET /api/awards/intervals
```

Response example:

```json
{
  "min": [
    {
      "producer": "Joel Silver",
      "interval": 1,
      "previousWin": 1990,
      "followingWin": 1991
    }
  ],
  "max": [
    {
      "producer": "Matthew Vaughn",
      "interval": 13,
      "previousWin": 2002,
      "followingWin": 2015
    }
  ]
}
```

## Database

The project uses H2 in-memory database. The `Movielist.csv` file in `src/main/resources` is automatically imported when the application starts.

H2 Console:

```text
http://localhost:8080/h2-console
```

JDBC URL:

```text
jdbc:h2:mem:awardintervals
```

## Documentation

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## Postman Collection

A Postman collection is available in `postman/award-intervals.postman_collection.json`.

To import:
1. Open Postman
2. Click "Import" button
3. Select the JSON file from the `postman` folder
4. Update the `baseUrl` variable if needed (default: http://localhost:8080)