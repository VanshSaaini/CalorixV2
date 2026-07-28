import api from './axios';

/* ============ AUTH ============ */
export const authApi = {
  register: (payload) => api.post('/api/auth/register', payload).then((r) => r.data),
  login: (payload) => api.post('/api/auth/login', payload).then((r) => r.data),
  refresh: (refreshToken) =>
    api.post('/api/auth/refresh-token', { refreshToken }).then((r) => r.data),
};

/* ============ USERS ============ */
export const userApi = {
  me: () => api.get('/api/users/me').then((r) => r.data),
  updateMe: (payload) => api.put('/api/users/me', payload).then((r) => r.data),
  deleteMe: () => api.delete('/api/users/me').then((r) => r.data),
  getById: (id) => api.get(`/api/users/${id}`).then((r) => r.data),
  all: () => api.get('/api/users').then((r) => r.data),
};

/* ============ DASHBOARD ============ */
export const dashboardApi = {
  get: () =>
    api.get("/api/dashboard").then((r) => r.data),

  summary: () =>
    api.get("/api/dashboard/summary").then((r) => r.data),

  today: () =>
    api.get("/api/dashboard/today").then((r) => r.data),
};

/* Generic factory for record endpoints */
const recordApi = (base) => ({
  save: (userId, payload) => api.post(`${base}/user/${userId}`, payload).then((r) => r.data),
  update: (id, payload) => api.put(`${base}/${id}`, payload).then((r) => r.data),
  get: (id) => api.get(`${base}/${id}`).then((r) => r.data),
  list: (userId) => api.get(`${base}/user/${userId}`).then((r) => r.data),
  latest: (userId) => api.get(`${base}/latest/${userId}`).then((r) => r.data),
  delete: (id) => api.delete(`${base}/${id}`).then((r) => r.data),
});

export const weightApi = recordApi('/api/weights');
export const bmiApi = recordApi('/api/bmi');
export const bmrApi = recordApi('/api/bmr');
export const macroApi = recordApi('/api/macros');
export const waterApi = recordApi('/api/water');
export const caloriesApi = recordApi('/api/calories');
export const bodyApi = recordApi('/api/body-measurements');
export const photoApi = {
  save: (userId, formData) =>
    api
      .post(`/api/photos/${userId}`, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
      .then((r) => r.data),

  update: (id, formData) =>
    api
      .put(`/api/photos/${id}`, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
      .then((r) => r.data),

  get: (id) =>
    api.get(`/api/photos/${id}`).then((r) => r.data),

  list: (userId) =>
    api.get(`/api/photos/user/${userId}`).then((r) => r.data),

  latest: (userId) =>
    api.get(`/api/photos/latest/${userId}`).then((r) => r.data),

  delete: (id) =>
    api.delete(`/api/photos/${id}`).then((r) => r.data),
};

/* ============ WALKING SESSIONS ============ */
export const walkingApi = {
  save: (userId, payload) =>
    api.post(`/api/walking-sessions/user/${userId}`, payload).then((r) => r.data),
  get: (id) => api.get(`/api/walking-sessions/${id}`).then((r) => r.data),
  list: (userId) => api.get(`/api/walking-sessions/user/${userId}`).then((r) => r.data),
  latest: (userId) => api.get(`/api/walking-sessions/latest/${userId}`).then((r) => r.data),
  delete: (id) => api.delete(`/api/walking-sessions/${id}`).then((r) => r.data),
};

/* ============ GOALS ============ */
export const goalApi = {
  create: (userId, payload) => api.post(`/api/goals/user/${userId}`, payload).then((r) => r.data),
  update: (id, payload) => api.put(`/api/goals/${id}`, payload).then((r) => r.data),
  get: (id) => api.get(`/api/goals/${id}`).then((r) => r.data),
  list: (userId) => api.get(`/api/goals/user/${userId}`).then((r) => r.data),
  active: (userId) => api.get(`/api/goals/active/${userId}`).then((r) => r.data),
  complete: (id) => api.put(`/api/goals/${id}/complete`).then((r) => r.data),
  delete: (id) => api.delete(`/api/goals/${id}`).then((r) => r.data),
};

/* ============ ROLES (ADMIN) ============ */
export const roleApi = {
  list: () => api.get('/api/roles').then((r) => r.data),
  get: (id) => api.get(`/api/roles/${id}`).then((r) => r.data),
  create: (payload) => api.post('/api/roles', payload).then((r) => r.data),
  update: (id, payload) => api.put(`/api/roles/${id}`, payload).then((r) => r.data),
  delete: (id) => api.delete(`/api/roles/${id}`).then((r) => r.data),
};

/* ============ HEALTH ============ */
export const healthApi = {
  ping: () => api.get('/api/health').then((r) => r.data),
};
