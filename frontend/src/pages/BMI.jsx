import { useEffect, useState } from 'react';
import { Plus, Trash2, Calculator } from 'lucide-react';
import toast from 'react-hot-toast';
import { bmiApi } from '../api/endpoints';
import { useAuth } from '../context/AuthContext';
import PageHeader from '../components/PageHeader';
import Loader from '../components/Loader';
import TrendChart from '../components/TrendChart';
import EmptyState from '../components/EmptyState';
import { fmtDate, today, num, computeBmi, bmiCategory } from '../utils/format';

export default function BMI() {
  const { user } = useAuth();
  const [records, setRecords] = useState([]);
  const [loading, setLoading] = useState(true);
  const [form, setForm] = useState({
    weight: '',
    height: user?.height || '',
    recordDate: today(),
  });
  const [busy, setBusy] = useState(false);

  useEffect(() => {
    if (user?.height) setForm((f) => ({ ...f, height: user.height }));
  }, [user?.height]);

  const load = async () => {
    setLoading(true);
    try {
      const data = await bmiApi.list(user.id);
      setRecords(data || []);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (user?.id) load();
  }, [user?.id]);

  const computed = computeBmi(Number(form.weight), Number(form.height));
  const cat = bmiCategory(computed);

  const submit = async (e) => {
    e.preventDefault();
    if (!form.weight || !form.height) return;
    setBusy(true);
    try {
      const payload = {
        weight: Number(form.weight),
        height: Number(form.height),
        bmi: computed,
        category: cat.label,
        recordDate: form.recordDate,
      };
      await bmiApi.save(user.id, payload);
      toast.success('BMI logged.');
      setForm({ weight: '', height: user?.height || '', recordDate: today() });
      load();
    } catch (err) {
      toast.error(err?.response?.data?.message || 'Failed to save');
    } finally {
      setBusy(false);
    }
  };

  const remove = async (id) => {
    if (!confirm('Delete this record?')) return;
    await bmiApi.delete(id);
    load();
  };

  return (
    <div>
      <PageHeader title="Body Mass Index" subtitle="Your BMI, calculated & tracked without judgment." />

      <div className="grid gap-6 lg:grid-cols-[1.4fr,1fr]">
        <div className="card">
          <p className="text-xs uppercase tracking-[0.24em] text-ink-500">Trend</p>
          {loading ? <Loader /> : <TrendChart data={records} dataKey="bmi" color="clay" />}
        </div>

        <form onSubmit={submit} className="card space-y-4" data-testid="bmi-form">
          <p className="h-serif text-xl font-semibold text-ink-900">Log BMI</p>
          <div className="grid grid-cols-2 gap-3">
            <div>
              <label className="label">Weight (kg)</label>
              <input type="number" step="0.1" required className="input" value={form.weight} onChange={(e) => setForm({ ...form, weight: e.target.value })} data-testid="bmi-weight" />
            </div>
            <div>
              <label className="label">Height (cm)</label>
              <input type="number" step="0.1" required className="input" value={form.height} onChange={(e) => setForm({ ...form, height: e.target.value })} data-testid="bmi-height" />
            </div>
          </div>
          <div>
            <label className="label">Date</label>
            <input type="date" className="input" value={form.recordDate} onChange={(e) => setForm({ ...form, recordDate: e.target.value })} />
          </div>

          <div className="rounded-2xl border border-cream-200 bg-white/60 p-4">
            <div className="flex items-center gap-2 text-xs uppercase tracking-[0.22em] text-ink-500">
              <Calculator className="h-3.5 w-3.5" /> Computed
            </div>
            <div className="mt-2 flex items-baseline justify-between">
              <span className="h-serif text-3xl font-semibold text-ink-900" data-testid="bmi-computed">
                {computed ?? '—'}
              </span>
              <span className="chip">{cat.label}</span>
            </div>
          </div>

          <button type="submit" disabled={busy} className="btn-primary w-full" data-testid="bmi-submit">
            <Plus className="h-4 w-4" /> Save
          </button>
        </form>
      </div>

      <div className="mt-6">
        <p className="mb-3 text-xs uppercase tracking-[0.24em] text-ink-500">History</p>
        {records.length === 0 && !loading ? (
          <EmptyState title="No BMI records" hint="Add your weight and height to log your first BMI." />
        ) : (
          <div className="overflow-hidden rounded-3xl border border-cream-200 bg-cream-50">
            <table className="w-full text-sm">
              <thead className="border-b border-cream-200 bg-cream-100">
                <tr className="text-left text-xs uppercase tracking-[0.2em] text-ink-500">
                  <th className="px-5 py-3">Date</th>
                  <th className="px-5 py-3">Weight</th>
                  <th className="px-5 py-3">Height</th>
                  <th className="px-5 py-3">BMI</th>
                  <th className="px-5 py-3">Category</th>
                  <th className="px-5 py-3"></th>
                </tr>
              </thead>
              <tbody>
                {records.map((r) => (
                  <tr key={r.id} className="border-b border-cream-100 last:border-0">
                    <td className="px-5 py-3">{fmtDate(r.recordDate)}</td>
                    <td className="px-5 py-3">{num(r.weight)} kg</td>
                    <td className="px-5 py-3">{num(r.height)} cm</td>
                    <td className="px-5 py-3 h-serif text-lg font-semibold">{num(r.bmi)}</td>
                    <td className="px-5 py-3"><span className="chip">{r.category || '—'}</span></td>
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
