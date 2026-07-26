import { useEffect, useState } from 'react';
import { Plus, Trash2 } from 'lucide-react';
import toast from 'react-hot-toast';
import { caloriesApi } from '../api/endpoints';
import { useAuth } from '../context/AuthContext';
import PageHeader from '../components/PageHeader';
import Loader from '../components/Loader';
import TrendChart from '../components/TrendChart';
import EmptyState from '../components/EmptyState';
import { fmtDate, today, num } from '../utils/format';

export default function Calories() {
  const { user } = useAuth();
  const [records, setRecords] = useState([]);
  const [loading, setLoading] = useState(true);
  const [form, setForm] = useState({ consumedCalories: '', burnedCalories: '', remainingCalories: '', recordDate: today() });
  const [busy, setBusy] = useState(false);

  const load = async () => {
    setLoading(true);
    try { setRecords((await caloriesApi.list(user.id)) || []); } finally { setLoading(false); }
  };
  useEffect(() => { if (user?.id) load(); }, [user?.id]);

  const submit = async (e) => {
    e.preventDefault();
    setBusy(true);
    try {
      await caloriesApi.save(user.id, {
        consumedCalories: Number(form.consumedCalories),
        burnedCalories: Number(form.burnedCalories),
        remainingCalories: Number(form.remainingCalories) || Number(form.consumedCalories) - Number(form.burnedCalories),
        recordDate: form.recordDate,
      });
      toast.success('Calories logged.');
      setForm({ consumedCalories: '', burnedCalories: '', remainingCalories: '', recordDate: today() });
      load();
    } catch (err) { toast.error(err?.response?.data?.message || 'Failed'); }
    finally { setBusy(false); }
  };

  const remove = async (id) => { if (!confirm('Delete?')) return; await caloriesApi.delete(id); load(); };

  return (
    <div>
      <PageHeader title="Daily Calories" subtitle="Consumed vs burned — the net picture of your day." />

      <div className="grid gap-6 lg:grid-cols-[1.4fr,1fr]">
        <div className="card">
          <p className="text-xs uppercase tracking-[0.24em] text-ink-500">Consumed trend</p>
          {loading ? <Loader /> : <TrendChart data={records} dataKey="consumedCalories" unit="kcal" color="sage" />}
        </div>

        <form onSubmit={submit} className="card space-y-4" data-testid="calories-form">
          <p className="h-serif text-xl font-semibold text-ink-900">Log calories</p>
          <div>
            <label className="label">Consumed (kcal)</label>
            <input type="number" required className="input" value={form.consumedCalories} onChange={(e) => setForm({ ...form, consumedCalories: e.target.value })} data-testid="calories-consumed" />
          </div>
          <div>
            <label className="label">Burned (kcal)</label>
            <input type="number" required className="input" value={form.burnedCalories} onChange={(e) => setForm({ ...form, burnedCalories: e.target.value })} />
          </div>
          <div>
            <label className="label">Remaining (optional)</label>
            <input type="number" className="input" value={form.remainingCalories} onChange={(e) => setForm({ ...form, remainingCalories: e.target.value })} placeholder="Auto: consumed − burned" />
          </div>
          <div>
            <label className="label">Date</label>
            <input type="date" className="input" value={form.recordDate} onChange={(e) => setForm({ ...form, recordDate: e.target.value })} />
          </div>
          <button type="submit" disabled={busy} className="btn-primary w-full" data-testid="calories-submit">
            <Plus className="h-4 w-4" /> Save
          </button>
        </form>
      </div>

      <div className="mt-6">
        <p className="mb-3 text-xs uppercase tracking-[0.24em] text-ink-500">History</p>
        {records.length === 0 && !loading ? (
          <EmptyState title="No entries" hint="Log today's calories to unlock your trend." />
        ) : (
          <div className="overflow-hidden rounded-3xl border border-cream-200 bg-cream-50">
            <table className="w-full text-sm">
              <thead className="border-b border-cream-200 bg-cream-100">
                <tr className="text-left text-xs uppercase tracking-[0.2em] text-ink-500">
                  <th className="px-5 py-3">Date</th>
                  <th className="px-5 py-3">Consumed</th>
                  <th className="px-5 py-3">Burned</th>
                  <th className="px-5 py-3">Remaining</th>
                  <th className="px-5 py-3"></th>
                </tr>
              </thead>
              <tbody>
                {records.map((r) => (
                  <tr key={r.id} className="border-b border-cream-100 last:border-0">
                    <td className="px-5 py-3">{fmtDate(r.recordDate)}</td>
                    <td className="px-5 py-3 h-serif text-lg font-semibold">{num(r.consumedCalories, 0)}</td>
                    <td className="px-5 py-3">{num(r.burnedCalories, 0)}</td>
                    <td className="px-5 py-3">{num(r.remainingCalories, 0)}</td>
                    <td className="px-5 py-3 text-right">
                      <button className="btn-ghost text-clay-600" onClick={() => remove(r.id)}><Trash2 className="h-3.5 w-3.5" /></button>
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
