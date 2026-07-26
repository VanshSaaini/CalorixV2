# Calorix V2

Calorix is a full-stack wellness tracker. The React application includes dashboard, profile, admin, goals, and tracking pages for weight, BMI, BMR, calories, macros, water, body measurements, and progress photos. It communicates with the Spring Boot REST API through JWT authentication.

## Run locally

1. Start the backend (it uses an embedded H2 database by default, so no database installation is required):

   ```powershell
   cd backend
   .\mvnw.cmd spring-boot:run
   ```

2. In another terminal, install and start the frontend:

   ```powershell
   cd frontend
   npm.cmd install
   npm.cmd run dev
   ```

Open `http://localhost:5173`. Vite forwards `/api` requests to the backend at `http://localhost:8080`, so no frontend environment file is required for local development.

## Deployment configuration

To use PostgreSQL instead of the embedded local database, activate the `prod` profile and set the following backend environment variables:

- `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`
- `JWT_SECRET` (a long Base64-encoded secret)
- `CORS_ALLOWED_ORIGINS` (comma-separated frontend origins)
- `SERVER_PORT` (optional; defaults to `8080`)

For Windows Command Prompt:

```bat
set SPRING_PROFILES_ACTIVE=prod
set DB_URL=jdbc:postgresql://localhost:5432/calorix
set DB_USERNAME=postgres
set DB_PASSWORD=your-password
mvn spring-boot:run
```

For a separately deployed frontend, copy `frontend/.env.example` to `frontend/.env` and set `VITE_API_BASE_URL` to the backend origin.
