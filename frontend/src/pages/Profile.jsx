import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Save, Trash2 } from 'lucide-react';
import toast from 'react-hot-toast';
import { userApi } from '../api/endpoints';
import { useAuth } from '../context/AuthContext';
import PageHeader from '../components/PageHeader';

export default function Profile() {
  const { user, setUser, logout } = useAuth();
  const navigate = useNavigate();
  const [form, setForm] = useState({
    firstName: '', lastName: '', email: '', age: '', height: '', gender: 'MALE',
  });
  const [busy, setBusy] = useState(false);

  useEffect(() => {
    if (user) {
      setForm({
        firstName: user.firstName || '',
        lastName: user.lastName || '',
        email: user.email || '',
        age: user.age || '',
        height: user.height || '',
        gender: user.gender || 'MALE',
      });
    }
  }, [user]);

  const save = async (e) => {
    e.preventDefault();
    setBusy(true);
    try {
      const updated = await userApi.updateMe({
        ...form,
        age: form.age ? Number(form.age) : null,
        height: form.height ? Number(form.height) : null,
      });
      const merged = { ...user, ...updated };
      localStorage.setItem('calorix_user', JSON.stringify(merged));
      setUser(merged);
      toast.success('Profile updated.');
    } catch (err) { toast.error(err?.response?.data?.message || 'Failed'); }
    finally { setBusy(false); }
  };

  const deleteAccount = async () => {
    if (!confirm('This will permanently delete your account. Continue?')) return;
    try {
      await userApi.deleteMe();
      logout();
      navigate('/');
      toast.success('Account deleted.');
    } catch { toast.error('Could not delete.'); }
  };

  return (
    <div>
      <PageHeader title="Profile" subtitle="Your information — always in your hands." />

      <form onSubmit={save} className="card grid gap-4 md:grid-cols-2" data-testid="profile-form">
        <div>
          <label className="label">First name</label>
          <input className="input" value={form.firstName} onChange={(e) => setForm({ ...form, firstName: e.target.value })} data-testid="profile-firstname" />
        </div>
        <div>
          <label className="label">Last name</label>
          <input className="input" value={form.lastName} onChange={(e) => setForm({ ...form, lastName: e.target.value })} />
        </div>
        <div className="md:col-span-2">
          <label className="label">Email</label>
          <input type="email" className="input" value={form.email} onChange={(e) => setForm({ ...form, email: e.target.value })} />
        </div>
        <div>
          <label className="label">Age</label>
          <input type="number" className="input" value={form.age} onChange={(e) => setForm({ ...form, age: e.target.value })} />
        </div>
        <div>
          <label className="label">Height (cm)</label>
          <input type="number" step="0.1" className="input" value={form.height} onChange={(e) => setForm({ ...form, height: e.target.value })} />
        </div>
        <div className="md:col-span-2">
          <label className="label">Gender</label>
          <div className="flex gap-2">
            {['MALE', 'FEMALE', 'OTHER'].map((g) => (
              <button key={g} type="button"
                onClick={() => setForm({ ...form, gender: g })}
                className={'flex-1 rounded-2xl border px-3 py-2.5 text-sm font-semibold transition ' + (form.gender === g ? 'border-sage-500 bg-sage-500 text-cream-50' : 'border-cream-200 bg-cream-50 text-ink-700 hover:bg-cream-100')}>
                {g}
              </button>
            ))}
          </div>
        </div>
        <div className="flex items-center gap-3 md:col-span-2">
          <button type="submit" disabled={busy} className="btn-primary" data-testid="profile-save"><Save className="h-4 w-4" /> Save changes</button>
          <button type="button" className="btn-danger" onClick={deleteAccount} data-testid="profile-delete"><Trash2 className="h-3.5 w-3.5" /> Delete account</button>
        </div>
      </form>

      <div className="mt-6 card">
        <p className="text-xs uppercase tracking-[0.24em] text-ink-500">Account</p>
        <div className="mt-2 grid gap-3 md:grid-cols-3">
          <Meta label="Role" value={user?.role || '—'} />
          <Meta label="Email verified" value={user?.emailVerified ? 'Yes' : 'Pending'} />
          <Meta label="User ID" value={user?.id || '—'} />
        </div>
      </div>
    </div>
  );
}

function Meta({ label, value }) {
  return (
    <div className="rounded-2xl border border-cream-200 bg-white/60 p-3">
      <p className="text-[10px] uppercase tracking-[0.2em] text-ink-500">{label}</p>
      <p className="mt-1 text-base font-semibold text-ink-900">{String(value)}</p>
    </div>
  );
}
