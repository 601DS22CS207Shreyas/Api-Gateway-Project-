# API Gateway Analytics Platform — Phase 1

Auth + API key service. Java 21, Spring Boot 3.3, MySQL, JWT.

## 1. Start MySQL

You don't need to install MySQL locally — use Docker:

```bash
docker compose up -d
```

This starts MySQL 8.4 on `localhost:3306` with db `gateway_db`, user `gateway_user`, password `gateway_pass` (matches `application.yml`).

If you'd rather install MySQL natively instead, run this in the `mysql` client:

```sql
CREATE DATABASE gateway_db;
CREATE USER 'gateway_user'@'%' IDENTIFIED BY 'gateway_pass';
GRANT ALL PRIVILEGES ON gateway_db.* TO 'gateway_user'@'%';
FLUSH PRIVILEGES;
```

## 2. Set a real JWT secret

Open `src/main/resources/application.yml` and replace `jwt.secret` with a real random value:

```bash
openssl rand -base64 64
```

Paste the output in as the secret value.

## 3. Run the app

```bash
mvn clean install
mvn spring-boot:run
```

App starts on `http://localhost:8080`.

## 4. Test the endpoints

**Register:**
```bash
curl -X POST http://localhost:8080/register \
  -H "Content-Type: application/json" \
  -d '{"username":"alice","email":"alice@example.com","password":"password123"}'
```

**Login:**
```bash
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{"username":"alice","password":"password123"}'
```

Copy the `token` from the response.

**Create an API key (requires the JWT from login):**
```bash
curl -X POST http://localhost:8080/apikey/create \
  -H "Authorization: Bearer <PASTE_TOKEN_HERE>"
```

Expected response:
```json
{ "apiKey": "sk_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" }
```

## What's implemented

- `POST /register` — creates a user, BCrypt-hashes the password, defaults to `FREE` plan
- `POST /login` — authenticates against Spring Security, issues a JWT
- `POST /apikey/create` — protected by `JwtAuthFilter`; generates a `sk_`-prefixed key tied to the authenticated user
- MySQL via Spring Data JPA, `ddl-auto: update` (tables auto-created on first run)

## Known simplifications (intentional, for Phase 1 scope)

- `ddl-auto: update` is fine for dev, but you'll want Flyway/Liquibase migrations before this goes anywhere near production.
- No refresh tokens yet — JWT just expires after 24h and the user has to log in again.
- No endpoint yet to *list* a user's existing API keys or revoke one — that's a natural Phase 1.5 addition before moving to Phase 2 (gateway routing).
