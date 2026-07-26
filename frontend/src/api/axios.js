import axios from 'axios';

// During development Vite proxies /api to Spring Boot. Set VITE_API_BASE_URL
// (for example https://api.example.com) when the frontend and API are deployed separately.
const baseURL = import.meta.env.VITE_API_BASE_URL || '';

export const api = axios.create({
  baseURL,
  headers: { 'Content-Type': 'application/json' },
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('calorix_access_token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

let isRefreshing = false;
let queue = [];

api.interceptors.response.use(
  (res) => res,
  async (error) => {
    const original = error.config;
    if (
      error.response?.status === 401 &&
      !original._retry &&
      !original.url.includes('/api/auth/')
    ) {
      original._retry = true;
      const refresh = localStorage.getItem('calorix_refresh_token');
      if (!refresh) {
        localStorage.clear();
        window.location.href = '/login';
        return Promise.reject(error);
      }

      if (isRefreshing) {
        return new Promise((resolve, reject) => {
          queue.push({ resolve, reject, original });
        });
      }

      isRefreshing = true;
      try {
        const { data } = await axios.post(
          `${baseURL}/api/auth/refresh-token`,
          { refreshToken: refresh },
          { headers: { 'Content-Type': 'application/json' } }
        );
        localStorage.setItem('calorix_access_token', data.accessToken);
        localStorage.setItem('calorix_refresh_token', data.refreshToken);
        queue.forEach(({ resolve, original }) => {
          original.headers.Authorization = `Bearer ${data.accessToken}`;
          resolve(api(original));
        });
        queue = [];
        original.headers.Authorization = `Bearer ${data.accessToken}`;
        return api(original);
      } catch (e) {
        queue.forEach(({ reject }) => reject(e));
        queue = [];
        localStorage.clear();
        window.location.href = '/login';
        return Promise.reject(e);
      } finally {
        isRefreshing = false;
      }
    }
    return Promise.reject(error);
  }
);

export default api;
