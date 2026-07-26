# CalorixV2 — Frontend

A classic, clean health-tracking web app built with **React 18 + Vite + TailwindCSS**, designed to pair with the CalorixV2 Spring Boot backend.

## ✨ Features

- JWT authentication (Register / Login / Refresh) with token persistence
- Personal dashboard aggregating weight, BMI, BMR, calories, macros, water, goal & photo
- Trackers with interactive Recharts trends: Weight, BMI, BMR, Macros, Water, Daily Calories, Body Measurements
- Goals — create, activate, complete
- Progress Photos gallery
- User Profile — view & edit
- Admin — user list & role management (visible to `ROLE_ADMIN`)
- Responsive layout, soft-pastel “Apple Fitness meets boutique wellness” aesthetic

## 🔧 Getting Started

```bash
cd frontend-vite
npm install       # or: yarn
npm run dev
```

The dev server starts at **http://localhost:5173**. Your Spring Boot backend must be running at `http://localhost:8080` (the URL in `.env`) — its CORS is already whitelisted for `localhost:5173`.

## 🔌 Backend Contract

All API calls go through `src/api/axios.js`. Baseline URL comes from `VITE_API_BASE_URL` and the JWT is automatically attached to every authenticated request.

| Feature            | Endpoint prefix           |
| ------------------ | ------------------------- |
| Auth               | `/api/auth`               |
| Users              | `/api/users`              |
| Dashboard          | `/api/dashboard`          |
| Weight             | `/api/weights`            |
| BMI                | `/api/bmi`                |
| BMR                | `/api/bmr`                |
| Daily Calories     | `/api/calories`           |
| Macros             | `/api/macros`             |
| Water              | `/api/water`              |
| Body Measurements  | `/api/body-measurements`  |
| Goals              | `/api/goals`              |
| Progress Photos    | `/api/photos`             |
| Roles (admin only) | `/api/roles`              |
| Health             | `/api/health`             |

## 🏗️ Build

```bash
npm run build     # outputs to dist/
npm run preview
```
