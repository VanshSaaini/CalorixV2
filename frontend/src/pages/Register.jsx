import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Leaf, Loader2 } from 'lucide-react';
import toast from 'react-hot-toast';
import { useAuth } from '../context/AuthContext';

const GENDERS = ['MALE', 'FEMALE', 'OTHER'];

export default function Register() {
  const { register } = useAuth();
  const navigate = useNavigate();
  const [busy, setBusy] = useState(false);
  const [form, setForm] = useState({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    age: 25,
    height: 170,
    gender: 'MALE',
  });

  const update = (k) => (e) => setForm({ ...form, [k]: e.target.value });

  const submit = async (e) => {
    e.preventDefault();
    setBusy(true);
    try {
      await register({
        ...form,
        age: Number(form.age),
        height: Number(form.height),
      });
      toast.success('Welcome to Calorix.');
      navigate('/dashboard');
    } catch (err) {
      toast.error(err?.response?.data?.message || 'Could not create account');
    } finally {
      setBusy(false);
    }
  };

  return (
    <div className="grid min-h-screen w-full md:grid-cols-2">
      <div className="hidden bg-ink-900 bg-grain px-12 py-16 text-cream-50 md:flex md:flex-col md:justify-between">
        <Link to="/" className="flex items-center gap-2.5">
          <span className="grid h-10 w-10 place-items-center rounded-2xl bg-sage-500 text-cream-50 shadow-soft">
            <Leaf className="h-5 w-5" />
          </span>
          <p className="h-serif text-2xl font-semibold">Calorix</p>
        </Link>
        <div>
          <p className="h-serif text-5xl font-semibold leading-tight">
            A softer path
            <br />
            <em className="not-italic text-sage-200">to a stronger self.</em>
          </p>
          <p className="mt-5 max-w-md text-sm opacity-80">
            Setup takes two minutes. You'll get a personal dashboard, nine trackers, and a goal system that respects your pace.
          </p>
        </div>
        <p className="text-xs uppercase tracking-[0.3em] opacity-70">CalorixV2 · Wellness OS</p>
      </div>

      <div className="flex items-center justify-center bg-cream-50 px-6 py-12 md:px-16">
        <form onSubmit={submit} className="w-full max-w-md reveal" data-testid="register-form">
          <p className="text-xs uppercase tracking-[0.3em] text-sage-500">Create account</p>
          <h1 className="mt-2 h-serif text-4xl font-semibold text-ink-900">Begin your practice</h1>

          <div className="mt-8 grid grid-cols-2 gap-4">
            <div>
              <label className="label">First name</label>
              <input required data-testid="reg-firstname" className="input" value={form.firstName} onChange={update('firstName')} />
            </div>
            <div>
              <label className="label">Last name</label>
              <input required data-testid="reg-lastname" className="input" value={form.lastName} onChange={update('lastName')} />
            </div>
            <div className="col-span-2">
              <label className="label">Email</label>
              <input required type="email" data-testid="reg-email" className="input" value={form.email} onChange={update('email')} />
            </div>
            <div className="col-span-2">
              <label className="label">Password (min 8)</label>
              <input required minLength={8} type="password" data-testid="reg-password" className="input" value={form.password} onChange={update('password')} />
            </div>
            <div>
              <label className="label">Age</label>
              <input required type="number" min={10} max={120} data-testid="reg-age" className="input" value={form.age} onChange={update('age')} />
            </div>
            <div>
              <label className="label">Height (cm)</label>
              <input required type="number" step="0.1" data-testid="reg-height" className="input" value={form.height} onChange={update('height')} />
            </div>
            <div className="col-span-2">
              <label className="label">Gender</label>
              <div className="flex gap-2">
                {GENDERS.map((g) => (
                  <button
                    key={g}
                    type="button"
                    data-testid={`reg-gender-${g.toLowerCase()}`}
                    onClick={() => setForm({ ...form, gender: g })}
                    className={
                      'flex-1 rounded-2xl border px-3 py-2.5 text-sm font-semibold transition ' +
                      (form.gender === g
                        ? 'border-sage-500 bg-sage-500 text-cream-50'
                        : 'border-cream-200 bg-cream-50 text-ink-700 hover:bg-cream-100')
                    }
                  >
                    {g}
                  </button>
                ))}
              </div>
            </div>
          </div>

          <button type="submit" disabled={busy} className="btn-primary mt-6 w-full" data-testid="register-submit">
            {busy ? <Loader2 className="h-4 w-4 animate-spin" /> : 'Create account'}
          </button>

          <p className="mt-6 text-center text-sm text-ink-500">
            Already a member?{' '}
            <Link to="/login" className="font-semibold text-sage-500 underline-offset-4 hover:underline">
              Log in
            </Link>
          </p>
        </form>
      </div>
    </div>
  );
}
