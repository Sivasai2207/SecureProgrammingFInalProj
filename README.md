# PhoneBook Starter Java Spring Boot

## Overview

This is a secure PhoneBook REST API built with Spring Boot. It supports adding, listing, updating, and deleting phone book entries, with input validation, audit logging, and role-based security.

## Prerequisites

- Docker
- Java 21 (if running without Docker)
- Maven (for local builds)

## Build & Run

### Using Docker

```bash
# Build the Docker image
docker build -t phonebook:latest .

# Run the container (exposes port 8080)
docker run --rm -p 8080:8080 phonebook:latest
```

### Local Build (without Docker)

```bash
# Clone the repo
git clone https://github.com/Sivasai2207/SecureProgrammingFInalProj.git
cd PhoneBook_Starter_Java_SpringBoot

# Build with Maven
./mvnw clean package

# Run
java -jar target/PhoneBook_Starter_Java_SpringBoot-0.0.1-SNAPSHOT.jar
```

## API Authentication

All endpoints require HTTP Basic auth.

| Username | Password    | Role   | Permissions                |
| -------- | ----------- | ------ | -------------------------- |
| reader   | reader123   | READER | GET /phonebook/list        |
| writer   | writer123   | WRITER | All endpoints (list, add, update, delete) |

## Available Endpoints

### List Entries

```
GET /phonebook/list
```

- **Roles:** READER, WRITER  
- **Responses:**  
  - 200 OK: Returns JSON array of entries  
  - 204 No Content: No entries exist  

### Add Entry

```
POST /phonebook/add
Content-Type: application/json
```

**Body:**
```json
{
  "name": "Cher",
  "phoneNumber": "123-4567"
}
```

- **Role:** WRITER  
- **Responses:**  
  - 200 OK: Entry added  
  - 400 Bad Request: Validation errors  

### Update Entry

```
PUT /phonebook/update/{id}
Content-Type: application/json
```

**Body:** same as Add  
- **Role:** WRITER  
- **Responses:**  
  - 200 OK: Updated  
  - 404 Not Found: ID not found  
  - 400 Bad Request: Validation errors  

### Delete by Name

```
PUT /phonebook/delete-by-name?name={name}
```

- **Role:** WRITER  
- **Responses:**  
  - 200 OK: Deleted  
  - 404 Not Found: No matching entry  

### Delete by Number

```
PUT /phonebook/delete-by-number?number={phoneNumber}
```

- **Role:** WRITER  
- **Responses:**  
  - 200 OK: Deleted  
  - 404 Not Found: No matching entry  

## Validation Rules

- **Name**: Must start with uppercase, 1–40 chars, allows commas, dashes, apostrophes, no SQL/XSS keywords.  
- **Phone Number**: Supports:
  - 5-digit pure numbers (e.g., `12345`)
  - North American: `123-4567`, `(703) 123-4567`, `1-703-123-4567`
  - International: `+32 (21) 212-2324`, `011 1 703 111 1234`
  - 4-4 or 5-5 groups with dots/spaces

Refer to `PhoneBookEntry.java` for exact regex.


## Postman Collection is Avaliable in 
  Folder Postman Collection
  

## Example Curl Commands

```bash
# List (reader)
curl -i -u reader:reader123 http://localhost:8080/phonebook/list

# Add (writer)
curl -i -u writer:writer123 -H "Content-Type: application/json" \
  -d '{"name":"Cher","phoneNumber":"123-4567"}' \
  http://localhost:8080/phonebook/add

# Update (writer)
curl -i -u writer:writer123 -X PUT -H "Content-Type: application/json" \
  -d '{"name":"Cher","phoneNumber":"123-4567"}' \
  http://localhost:8080/phonebook/update/1

# Delete by name (writer)
curl -i -u writer:writer123 -X PUT \
  "http://localhost:8080/phonebook/delete-by-name?name=Cher"
```


## License

MIT
