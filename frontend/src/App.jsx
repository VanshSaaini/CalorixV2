import { lazy, Suspense } from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import Loader from './components/Loader';
import Layout from './components/Layout';
import ProtectedRoute from './components/ProtectedRoute';

const Landing = lazy(() => import('./pages/Landing'));
const Login = lazy(() => import('./pages/Login'));
const Register = lazy(() => import('./pages/Register'));
const Dashboard = lazy(() => import('./pages/Dashboard'));
const Weight = lazy(() => import('./pages/Weight'));
const BMI = lazy(() => import('./pages/BMI'));
const BMR = lazy(() => import('./pages/BMR'));
const Macros = lazy(() => import('./pages/Macros'));
const Water = lazy(() => import('./pages/Water'));
const Walking = lazy(() => import('./pages/Walking'));
const Calories = lazy(() => import('./pages/Calories'));
const BodyMeasurements = lazy(() => import('./pages/BodyMeasurements'));
const Goals = lazy(() => import('./pages/Goals'));
const ProgressPhotos = lazy(() => import('./pages/ProgressPhotos'));
const Profile = lazy(() => import('./pages/Profile'));
const Admin = lazy(() => import('./pages/Admin'));

export default function App() {
  return (
    <Suspense fallback={<Loader />}>
    <Routes>
      <Route path="/" element={<Landing />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />

      <Route
        element={
          <ProtectedRoute>
            <Layout />
          </ProtectedRoute>
        }
      >
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/weight" element={<Weight />} />
        <Route path="/bmi" element={<BMI />} />
        <Route path="/bmr" element={<BMR />} />
        <Route path="/macros" element={<Macros />} />
        <Route path="/water" element={<Water />} />
        <Route path="/walking" element={<Walking />} />
        <Route path="/calories" element={<Calories />} />
        <Route path="/body-measurements" element={<BodyMeasurements />} />
        <Route path="/goals" element={<Goals />} />
        <Route path="/photos" element={<ProgressPhotos />} />
        <Route path="/profile" element={<Profile />} />
        <Route
          path="/admin"
          element={
            <ProtectedRoute adminOnly>
              <Admin />
            </ProtectedRoute>
          }
        />
      </Route>

      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
    </Suspense>
  );
}
