import { useEffect, useState } from 'react';
import { Plus, Trash2, Calculator } from 'lucide-react';
import toast from 'react-hot-toast';
import { bmrApi } from '../api/endpoints';
import { useAuth } from '../context/AuthContext';
import PageHeader from '../components/PageHeader';
import Loader from '../components/Loader';
import TrendChart from '../components/TrendChart';
import EmptyState from '../components/EmptyState';
import { fmtDate, today, num, computeBmr, activityMultiplier } from '../utils/format';

const ACTIVITY = [
  { v: 'SEDENTARY', l: 'Sedentary' },
  { v: 'LIGHTLY_ACTIVE', l: 'Lightly active' },
  { v: 'MODERATELY_ACTIVE', l: 'Moderately active' },
  { v: 'VERY_ACTIVE', l: 'Very active' },
  { v: 'EXTRA_ACTIVE', l: 'Extra active' },
];

export default function BMR() {
  const { user } = useAuth();
  const [records, setRecords] = useState([]);
  const [loading, setLoading] = useState(true);
  const [form, setForm] = useState({
    age: user?.age || 25,
    weight: '',
    height: user?.height || '',
    gender: user?.gender || 'MALE',
    activityLevel: 'MODERATELY_ACTIVE',
    recordDate: today(),
  });
  const [busy, setBusy] = useState(false);

  useEffect(() => {
    if (user) {
      setForm((f) => ({ ...f, age: user.age || f.age, height: user.height || f.height, gender: user.gender || f.gender }));
    }
  }, [user]);

  const load = async () => {
    setLoading(true);
    try {
      const data = await bmrApi.list(user.id);
      setRecords(data || []);
    } finally {
      setLoading(false);
    }
  };
  useEffect(() => { if (user?.id) load(); }, [user?.id]);

  const bmr = computeBmr(Number(form.weight), Number(form.height), Number(form.age), form.gender);
  const maint = bmr ? Math.round(bmr * activityMultiplier(form.activityLevel)) : null;

  const submit = async (e) => {
    e.preventDefault();
    if (!bmr) return;
    setBusy(true);
    try {
      await bmrApi.save(user.id, {
        age: Number(form.age),
        weight: Number(form.weight),
        height: Number(form.height),
        gender: form.gender,
        bmr,
        activityLevel: form.activityLevel,
        maintenanceCalories: maint,
        recordDate: form.recordDate,
      });
      toast.success('BMR logged.');
      setForm({ ...form, weight: '' });
      load();
    } catch (err) {
      toast.error(err?.response?.data?.message || 'Failed to save');
    } finally {
      setBusy(false);
    }
  };

  const remove = async (id) => {
    if (!confirm('Delete this record?')) return;
    await bmrApi.delete(id);
    load();
  };

  return (
    <div>
      <PageHeader title="Basal Metabolic Rate" subtitle="Estimated calories at rest, plus your daily maintenance need." />
      <div className="grid gap-6 lg:grid-cols-[1.4fr,1fr]">
        <div className="card">
          <p className="text-xs uppercase tracking-[0.24em] text-ink-500">Trend</p>
          {loading ? <Loader /> : <TrendChart data={records} dataKey="bmr" unit="kcal" color="ink" />}
        </div>

        <form onSubmit={submit} className="card space-y-4" data-testid="bmr-form">
          <p className="h-serif text-xl font-semibold text-ink-900">Log BMR</p>
          <div className="grid grid-cols-2 gap-3">
            <div>
              <label className="label">Age</label>
              <input type="number" required className="input" value={form.age} onChange={(e) => setForm({ ...form, age: e.target.value })} />
            </div>
            <div>
              <label className="label">Gender</label>
              <select className="input" value={form.gender} onChange={(e) => setForm({ ...form, gender: e.target.value })}>
                <option value="MALE">Male</option>
                <option value="FEMALE">Female</option>
                <option value="OTHER">Other</option>
              </select>
            </div>
            <div>
              <label className="label">Weight (kg)</label>
              <input type="number" step="0.1" required className="input" value={form.weight} onChange={(e) => setForm({ ...form, weight: e.target.value })} data-testid="bmr-weight" />
            </div>
            <div>
              <label className="label">Height (cm)</label>
              <input type="number" step="0.1" required className="input" value={form.height} onChange={(e) => setForm({ ...form, height: e.target.value })} />
            </div>
          </div>
          <div>
            <label className="label">Activity</label>
            <select className="input" value={form.activityLevel} onChange={(e) => setForm({ ...form, activityLevel: e.target.value })}>
              {ACTIVITY.map((a) => <option key={a.v} value={a.v}>{a.l}</option>)}
            </select>
          </div>
          <div>
            <label className="label">Date</label>
            <input type="date" className="input" value={form.recordDate} onChange={(e) => setForm({ ...form, recordDate: e.target.value })} />
          </div>

          <div className="rounded-2xl border border-cream-200 bg-white/60 p-4">
            <div className="flex items-center gap-2 text-xs uppercase tracking-[0.22em] text-ink-500">
              <Calculator className="h-3.5 w-3.5" /> Computed
            </div>
            <div className="mt-2 grid grid-cols-2 gap-3">
              <div>
                <p className="text-[10px] uppercase tracking-[0.22em] text-ink-500">BMR</p>
                <p className="h-serif text-2xl font-semibold text-ink-900">{bmr ?? '—'} <span className="text-xs text-ink-500">kcal</span></p>
              </div>
              <div>
                <p className="text-[10px] uppercase tracking-[0.22em] text-ink-500">Maintenance</p>
                <p className="h-serif text-2xl font-semibold text-ink-900">{maint ?? '—'} <span className="text-xs text-ink-500">kcal</span></p>
              </div>
            </div>
          </div>

          <button type="submit" disabled={busy} className="btn-primary w-full" data-testid="bmr-submit">
            <Plus className="h-4 w-4" /> Save
          </button>
        </form>
      </div>

      <div className="mt-6">
        <p className="mb-3 text-xs uppercase tracking-[0.24em] text-ink-500">History</p>
        {records.length === 0 && !loading ? (
          <EmptyState title="No BMR records" hint="Compute your first BMR using the form above." />
        ) : (
          <div className="overflow-hidden rounded-3xl border border-cream-200 bg-cream-50">
            <table className="w-full text-sm">
              <thead className="border-b border-cream-200 bg-cream-100">
                <tr className="text-left text-xs uppercase tracking-[0.2em] text-ink-500">
                  <th className="px-5 py-3">Date</th>
                  <th className="px-5 py-3">BMR</th>
                  <th className="px-5 py-3">Maintenance</th>
                  <th className="px-5 py-3">Activity</th>
                  <th className="px-5 py-3"></th>
                </tr>
              </thead>
              <tbody>
                {records.map((r) => (
                  <tr key={r.id} className="border-b border-cream-100 last:border-0">
                    <td className="px-5 py-3">{fmtDate(r.recordDate)}</td>
                    <td className="px-5 py-3 h-serif text-lg font-semibold">{num(r.bmr, 0)}</td>
                    <td className="px-5 py-3">{num(r.maintenanceCalories, 0)}</td>
                    <td className="px-5 py-3"><span className="chip">{r.activityLevel?.replaceAll('_', ' ')}</span></td>
                    <td className="px-5 py-3 text-right">
                      <button className="btn-ghost text-clay-600" onClick={() => remove(r.id)}>
                        <Trash2 className="h-3.5 w-3.5" />
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
}
