import { useEffect, useState } from 'react';
import { Plus, Trash2 } from 'lucide-react';
import toast from 'react-hot-toast';
import { macroApi } from '../api/endpoints';
import { useAuth } from '../context/AuthContext';
import PageHeader from '../components/PageHeader';
import Loader from '../components/Loader';
import TrendChart from '../components/TrendChart';
import EmptyState from '../components/EmptyState';
import { fmtDate, today, num } from '../utils/format';

export default function Macros() {
  const { user } = useAuth();
  const [records, setRecords] = useState([]);
  const [loading, setLoading] = useState(true);
  const [form, setForm] = useState({
    calories: '',
    protein: '',
    carbohydrates: '',
    fats: '',
    goal: 'MAINTENANCE',
    recordDate: today(),
  });
  const [busy, setBusy] = useState(false);

  const load = async () => {
    setLoading(true);
    try { setRecords((await macroApi.list(user.id)) || []); } finally { setLoading(false); }
  };
  useEffect(() => { if (user?.id) load(); }, [user?.id]);

  const submit = async (e) => {
    e.preventDefault();
    setBusy(true);
    try {
      await macroApi.save(user.id, {
        calories: Number(form.calories),
        protein: Number(form.protein),
        carbohydrates: Number(form.carbohydrates),
        fats: Number(form.fats),
        goal: form.goal,
        recordDate: form.recordDate,
      });
      toast.success('Macros logged.');
      setForm({ ...form, calories: '', protein: '', carbohydrates: '', fats: '' });
      load();
    } catch (err) {
      toast.error(err?.response?.data?.message || 'Failed');
    } finally { setBusy(false); }
  };

  const remove = async (id) => { if (!confirm('Delete?')) return; await macroApi.delete(id); load(); };

  return (
    <div>
      <PageHeader title="Macros" subtitle="Protein, carbs & fats — the building blocks of your day." />

      <div className="grid gap-6 lg:grid-cols-[1.4fr,1fr]">
        <div className="card">
          <p className="text-xs uppercase tracking-[0.24em] text-ink-500">Calories trend</p>
          {loading ? <Loader /> : <TrendChart data={records} dataKey="calories" unit="kcal" color="clay" />}
        </div>

        <form onSubmit={submit} className="card space-y-4" data-testid="macros-form">
          <p className="h-serif text-xl font-semibold text-ink-900">Log macros</p>
          <div className="grid grid-cols-2 gap-3">
            <div>
              <label className="label">Calories</label>
              <input type="number" step="1" required className="input" value={form.calories} onChange={(e) => setForm({ ...form, calories: e.target.value })} data-testid="macros-calories" />
            </div>
            <div>
              <label className="label">Protein (g)</label>
              <input type="number" step="1" required className="input" value={form.protein} onChange={(e) => setForm({ ...form, protein: e.target.value })} />
            </div>
            <div>
              <label className="label">Carbs (g)</label>
              <input type="number" step="1" required className="input" value={form.carbohydrates} onChange={(e) => setForm({ ...form, carbohydrates: e.target.value })} />
            </div>
            <div>
              <label className="label">Fats (g)</label>
              <input type="number" step="1" required className="input" value={form.fats} onChange={(e) => setForm({ ...form, fats: e.target.value })} />
            </div>
          </div>
          <div>
            <label className="label">Goal</label>
            <select className="input" value={form.goal} onChange={(e) => setForm({ ...form, goal: e.target.value })}>
              <option value="WEIGHT_LOSS">Weight loss</option>
              <option value="WEIGHT_GAIN">Weight gain</option>
              <option value="MUSCLE_GAIN">Muscle gain</option>
              <option value="MAINTENANCE">Maintenance</option>
            </select>
          </div>
          <div>
            <label className="label">Date</label>
            <input type="date" className="input" value={form.recordDate} onChange={(e) => setForm({ ...form, recordDate: e.target.value })} />
          </div>
          <button type="submit" disabled={busy} className="btn-primary w-full" data-testid="macros-submit">
            <Plus className="h-4 w-4" /> Save
          </button>
        </form>
      </div>

      <div className="mt-6">
        <p className="mb-3 text-xs uppercase tracking-[0.24em] text-ink-500">History</p>
        {records.length === 0 && !loading ? (
          <EmptyState title="No macros yet" hint="Log a meal to see your trend." />
        ) : (
          <div className="overflow-hidden rounded-3xl border border-cream-200 bg-cream-50">
            <table className="w-full text-sm">
              <thead className="border-b border-cream-200 bg-cream-100">
                <tr className="text-left text-xs uppercase tracking-[0.2em] text-ink-500">
                  <th className="px-5 py-3">Date</th>
                  <th className="px-5 py-3">Kcal</th>
                  <th className="px-5 py-3">P</th>
                  <th className="px-5 py-3">C</th>
                  <th className="px-5 py-3">F</th>
                  <th className="px-5 py-3">Goal</th>
                  <th className="px-5 py-3"></th>
                </tr>
              </thead>
              <tbody>
                {records.map((r) => (
                  <tr key={r.id} className="border-b border-cream-100 last:border-0">
                    <td className="px-5 py-3">{fmtDate(r.recordDate)}</td>
                    <td className="px-5 py-3 h-serif text-lg font-semibold">{num(r.calories, 0)}</td>
                    <td className="px-5 py-3">{num(r.protein, 0)}g</td>
                    <td className="px-5 py-3">{num(r.carbohydrates, 0)}g</td>
                    <td className="px-5 py-3">{num(r.fats, 0)}g</td>
                    <td className="px-5 py-3"><span className="chip">{r.goal?.replaceAll('_', ' ')}</span></td>
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
