import { useEffect, useState } from 'react';
import { Plus, Trash2 } from 'lucide-react';
import toast from 'react-hot-toast';
import { waterApi } from '../api/endpoints';
import { useAuth } from '../context/AuthContext';
import PageHeader from '../components/PageHeader';
import Loader from '../components/Loader';
import TrendChart from '../components/TrendChart';
import EmptyState from '../components/EmptyState';
import { fmtDate, today, num } from '../utils/format';

export default function Water() {
  const { user } = useAuth();
  const [records, setRecords] = useState([]);
  const [loading, setLoading] = useState(true);
  const [form, setForm] = useState({ litres: '', recordDate: today() });
  const [busy, setBusy] = useState(false);

  const load = async () => {
    setLoading(true);
    try {
      setRecords((await waterApi.list(user.id)) || []);
    } finally {
      setLoading(false);
    }
  };
  useEffect(() => { if (user?.id) load(); }, [user?.id]);

  const submit = async (e) => {
    e.preventDefault();
    setBusy(true);
    try {
      await waterApi.save(user.id, { litres: Number(form.litres), recordDate: form.recordDate });
      toast.success('Hydration logged.');
      setForm({ litres: '', recordDate: today() });
      load();
    } catch (err) {
      toast.error(err?.response?.data?.message || 'Failed to save');
    } finally { setBusy(false); }
  };

  const remove = async (id) => {
    if (!confirm('Delete this record?')) return;
    await waterApi.delete(id);
    load();
  };

  return (
    <div>
      <PageHeader title="Water Intake" subtitle="Small sips, steady rhythm — hydration adds up." />

      <div className="grid gap-6 lg:grid-cols-[1.4fr,1fr]">
        <div className="card">
          <p className="text-xs uppercase tracking-[0.24em] text-ink-500">Trend</p>
          {loading ? <Loader /> : <TrendChart data={records} dataKey="litres" unit="L" color="blue" />}
        </div>

        <form onSubmit={submit} className="card space-y-4" data-testid="water-form">
          <p className="h-serif text-xl font-semibold text-ink-900">Log water</p>
          <div>
            <label className="label">Litres</label>
            <input type="number" step="0.1" required className="input" value={form.litres} onChange={(e) => setForm({ ...form, litres: e.target.value })} data-testid="water-input" />
          </div>
          <div className="flex flex-wrap gap-2">
            {[0.25, 0.5, 1, 2].map((v) => (
              <button
                type="button"
                key={v}
                onClick={() => setForm({ ...form, litres: String((Number(form.litres) || 0) + v) })}
                className="chip !cursor-pointer !bg-cream-100 hover:!bg-sage-100"
              >
                +{v}L
              </button>
            ))}
          </div>
          <div>
            <label className="label">Date</label>
            <input type="date" className="input" value={form.recordDate} onChange={(e) => setForm({ ...form, recordDate: e.target.value })} />
          </div>
          <button type="submit" disabled={busy} className="btn-primary w-full" data-testid="water-submit">
            <Plus className="h-4 w-4" /> Save
          </button>
        </form>
      </div>

      <div className="mt-6">
        <p className="mb-3 text-xs uppercase tracking-[0.24em] text-ink-500">History</p>
        {records.length === 0 && !loading ? (
          <EmptyState title="No entries" hint="Start with a glass — 0.25 L is a great first log." />
        ) : (
          <div className="overflow-hidden rounded-3xl border border-cream-200 bg-cream-50">
            <table className="w-full text-sm">
              <thead className="border-b border-cream-200 bg-cream-100">
                <tr className="text-left text-xs uppercase tracking-[0.2em] text-ink-500">
                  <th className="px-5 py-3">Date</th>
                  <th className="px-5 py-3">Litres</th>
                  <th className="px-5 py-3"></th>
                </tr>
              </thead>
              <tbody>
                {records.map((r) => (
                  <tr key={r.id} className="border-b border-cream-100 last:border-0">
                    <td className="px-5 py-3">{fmtDate(r.recordDate)}</td>
                    <td className="px-5 py-3 h-serif text-lg font-semibold">{num(r.litres)} L</td>
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
