import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Leaf, Loader2 } from 'lucide-react';
import toast from 'react-hot-toast';
import { useAuth } from '../context/AuthContext';

export default function Login() {
  const { login } = useAuth();
  const navigate = useNavigate();
  const [form, setForm] = useState({ email: '', password: '' });
  const [busy, setBusy] = useState(false);

  const submit = async (e) => {
    e.preventDefault();
    setBusy(true);
    try {
      await login(form);
      toast.success('Welcome back.');
      navigate('/dashboard');
    } catch (err) {
      toast.error(err?.response?.data?.message || 'Invalid credentials');
    } finally {
      setBusy(false);
    }
  };

  return (
    <div className="grid min-h-screen w-full md:grid-cols-2">
      <div className="hidden bg-sage-500 bg-grain px-12 py-16 text-cream-50 md:flex md:flex-col md:justify-between">
        <Link to="/" className="flex items-center gap-2.5">
          <span className="grid h-10 w-10 place-items-center rounded-2xl bg-cream-50 text-sage-500 shadow-soft">
            <Leaf className="h-5 w-5" />
          </span>
          <p className="h-serif text-2xl font-semibold">Calorix</p>
        </Link>
        <div>
          <p className="h-serif text-5xl font-semibold leading-tight">
            Track your body,
            <br />
            <em className="not-italic opacity-80">balance your life.</em>
          </p>
          <p className="mt-5 max-w-md text-sm opacity-80">
            Every data point is a moment of intention. Welcome back to your calmer wellness practice.
          </p>
        </div>
        <p className="text-xs uppercase tracking-[0.3em] opacity-70">CalorixV2 · Wellness OS</p>
      </div>

      <div className="flex items-center justify-center bg-cream-50 px-6 py-12 md:px-16">
        <form onSubmit={submit} className="w-full max-w-sm reveal" data-testid="login-form">
          <p className="text-xs uppercase tracking-[0.3em] text-sage-500">Welcome back</p>
          <h1 className="mt-2 h-serif text-4xl font-semibold text-ink-900">Log in to Calorix</h1>
          <p className="mt-2 text-sm text-ink-500">Continue where you left off.</p>

          <div className="mt-8 space-y-4">
            <div>
              <label className="label">Email</label>
              <input
                type="email"
                required
                data-testid="login-email"
                className="input"
                value={form.email}
                onChange={(e) => setForm({ ...form, email: e.target.value })}
                placeholder="you@calorix.app"
              />
            </div>
            <div>
              <label className="label">Password</label>
              <input
                type="password"
                required
                data-testid="login-password"
                className="input"
                value={form.password}
                onChange={(e) => setForm({ ...form, password: e.target.value })}
                placeholder="Your password"
              />
            </div>
          </div>

          <button type="submit" className="btn-primary mt-6 w-full" disabled={busy} data-testid="login-submit">
            {busy ? <Loader2 className="h-4 w-4 animate-spin" /> : 'Log in'}
          </button>

          <p className="mt-6 text-center text-sm text-ink-500">
            New to Calorix?{' '}
            <Link to="/register" className="font-semibold text-sage-500 underline-offset-4 hover:underline">
              Create an account
            </Link>
          </p>
        </form>
      </div>
    </div>
  );
}
