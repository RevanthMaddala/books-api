# Books API

Spring Boot REST API for managing a book library, built with Java 21, Spring Data JPA and PostgreSQL. It covers the full CRUD flow, including bulk inserts and partial (PATCH) updates. Every endpoint returns the same response envelope.

## Book model

| Field | Type | Notes |
|---|---|---|
| `id` | Integer | Auto-generated |
| `title` | String | |
| `author` | String | |
| `genre` | String | |
| `price` | double | |
| `publishedYear` | Integer | |
| `availability` | Boolean | In stock or not |

## Response format

Every endpoint wraps its result in a `ResponseStructure`:

```json
{
  "statusCode": 200,
  "message": "All the Book records",
  "data": [ ... ]
}
```

## Endpoints

| Method | Path | Description |
|---|---|---|
| POST | `/book` | Save one book |
| POST | `/book/all` | Save a list of books |
| GET | `/book` | Get all books |
| GET | `/book/{id}` | Get a book by id |
| PUT | `/book` | Replace a book. The body must include `id` |
| PATCH | `/book/{id}` | Update only the fields you send, e.g. `{ "price": 299.0, "availability": false }` |
| DELETE | `/book/{id}` | Delete a book |

## Running locally

Requirements: Java 21 and PostgreSQL.

1. Create the database:
   ```sql
   CREATE DATABASE "libraryDB";
   ```
2. Set your database credentials as environment variables:
   ```bash
   export DB_USERNAME=postgres
   export DB_PASSWORD=your_password   # defaults to "postgres" if not set
   ```
3. Start the app:
   ```bash
   ./mvnw spring-boot:run
   ```
   The API runs on http://localhost:8080.

## Example

```bash
curl -X POST localhost:8080/book -H "Content-Type: application/json" -d '{
  "title": "Clean Code", "author": "Robert C. Martin", "genre": "Programming",
  "price": 450.0, "publishedYear": 2008, "availability": true
}'
```

## Tech stack

Java 21 · Spring Boot · Spring Data JPA / Hibernate · PostgreSQL · Maven
