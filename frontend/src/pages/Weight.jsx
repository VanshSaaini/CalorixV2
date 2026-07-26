import { useEffect, useState } from 'react';
import { Plus, Trash2 } from 'lucide-react';
import toast from 'react-hot-toast';
import { weightApi } from '../api/endpoints';
import { useAuth } from '../context/AuthContext';
import PageHeader from '../components/PageHeader';
import Loader from '../components/Loader';
import TrendChart from '../components/TrendChart';
import EmptyState from '../components/EmptyState';
import { fmtDate, today, num } from '../utils/format';

export default function Weight() {
  const { user } = useAuth();
  const [records, setRecords] = useState([]);
  const [loading, setLoading] = useState(true);
  const [form, setForm] = useState({ weight: '', recordDate: today() });
  const [busy, setBusy] = useState(false);

  const load = async () => {
    setLoading(true);
    try {
      const data = await weightApi.list(user.id);
      setRecords(data || []);
    } catch {
      setRecords([]);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (user?.id) load();
  }, [user?.id]);

  const submit = async (e) => {
    e.preventDefault();
    if (!form.weight) return;
    setBusy(true);
    try {
      await weightApi.save(user.id, { weight: Number(form.weight), recordDate: form.recordDate });
      toast.success('Weight logged.');
      setForm({ weight: '', recordDate: today() });
      load();
    } catch (err) {
      toast.error(err?.response?.data?.message || 'Failed to save');
    } finally {
      setBusy(false);
    }
  };

  const remove = async (id) => {
    if (!confirm('Delete this record?')) return;
    try {
      await weightApi.delete(id);
      toast.success('Deleted.');
      load();
    } catch {
      toast.error('Failed to delete');
    }
  };

  return (
    <div>
      <PageHeader title="Weight" subtitle="Your kilograms, kindly recorded." />

      <div className="grid gap-6 lg:grid-cols-[1.4fr,1fr]">
        <div className="card">
          <p className="text-xs uppercase tracking-[0.24em] text-ink-500">Trend</p>
          {loading ? <Loader /> : <TrendChart data={records} dataKey="weight" unit="kg" color="sage" />}
        </div>

        <form onSubmit={submit} className="card space-y-4" data-testid="weight-form">
          <p className="h-serif text-xl font-semibold text-ink-900">Log a new record</p>
          <div>
            <label className="label">Weight (kg)</label>
            <input
              type="number"
              step="0.1"
              required
              className="input"
              data-testid="weight-input"
              value={form.weight}
              onChange={(e) => setForm({ ...form, weight: e.target.value })}
            />
          </div>
          <div>
            <label className="label">Date</label>
            <input
              type="date"
              className="input"
              value={form.recordDate}
              onChange={(e) => setForm({ ...form, recordDate: e.target.value })}
            />
          </div>
          <button type="submit" disabled={busy} className="btn-primary w-full" data-testid="weight-submit">
            <Plus className="h-4 w-4" /> Save
          </button>
        </form>
      </div>

      <div className="mt-6">
        <p className="mb-3 text-xs uppercase tracking-[0.24em] text-ink-500">History</p>
        {records.length === 0 && !loading ? (
          <EmptyState title="No weight records yet" hint="Log your first entry using the form above." />
        ) : (
          <div className="overflow-hidden rounded-3xl border border-cream-200 bg-cream-50" data-testid="weight-list">
            <table className="w-full text-sm">
              <thead className="border-b border-cream-200 bg-cream-100">
                <tr className="text-left text-xs uppercase tracking-[0.2em] text-ink-500">
                  <th className="px-5 py-3">Date</th>
                  <th className="px-5 py-3">Weight</th>
                  <th className="px-5 py-3"></th>
                </tr>
              </thead>
              <tbody>
                {records.map((r) => (
                  <tr key={r.id} className="border-b border-cream-100 last:border-0">
                    <td className="px-5 py-3">{fmtDate(r.recordDate)}</td>
                    <td className="px-5 py-3 h-serif text-lg font-semibold">{num(r.weight)} kg</td>
                    <td className="px-5 py-3 text-right">
                      <button className="btn-ghost text-clay-600" onClick={() => remove(r.id)} data-testid={`weight-delete-${r.id}`}>
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
