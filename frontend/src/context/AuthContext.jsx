import { createContext, useContext, useEffect, useMemo, useState } from 'react';
import { authApi, userApi } from '../api/endpoints';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  const persist = (data) => {
    localStorage.setItem('calorix_access_token', data.accessToken);
    localStorage.setItem('calorix_refresh_token', data.refreshToken);
    localStorage.setItem(
      'calorix_user',
      JSON.stringify({
        id: data.userId,
        firstName: data.firstName,
        lastName: data.lastName,
        email: data.email,
        role: data.role,
      })
    );
  };

  const login = async (payload) => {
    const data = await authApi.login(payload);
    persist(data);
    const me = await userApi.me().catch(() => null);
    const enriched = me || {
      id: data.userId,
      firstName: data.firstName,
      lastName: data.lastName,
      email: data.email,
      role: data.role,
    };
    localStorage.setItem('calorix_user', JSON.stringify(enriched));
    setUser(enriched);
    return enriched;
  };

  const register = async (payload) => {
    const data = await authApi.register(payload);
    persist(data);
    const me = await userApi.me().catch(() => null);
    const enriched = me || {
      id: data.userId,
      firstName: data.firstName,
      lastName: data.lastName,
      email: data.email,
      role: data.role,
    };
    localStorage.setItem('calorix_user', JSON.stringify(enriched));
    setUser(enriched);
    return enriched;
  };

  const logout = () => {
    localStorage.removeItem('calorix_access_token');
    localStorage.removeItem('calorix_refresh_token');
    localStorage.removeItem('calorix_user');
    setUser(null);
  };

  const refreshUser = async () => {
    try {
      const me = await userApi.me();
      localStorage.setItem('calorix_user', JSON.stringify(me));
      setUser(me);
      return me;
    } catch {
      return null;
    }
  };

  useEffect(() => {
    const token = localStorage.getItem('calorix_access_token');
    const stored = localStorage.getItem('calorix_user');
    if (token && stored) {
      setUser(JSON.parse(stored));
      userApi
        .me()
        .then((me) => {
          localStorage.setItem('calorix_user', JSON.stringify(me));
          setUser(me);
        })
        .catch(() => {})
        .finally(() => setLoading(false));
    } else {
      setLoading(false);
    }
  }, []);

  const value = useMemo(
    () => ({ user, loading, login, register, logout, refreshUser, setUser }),
    [user, loading]
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export const useAuth = () => useContext(AuthContext);
