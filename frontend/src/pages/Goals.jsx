import { useEffect, useState } from 'react';
import { Plus, Trash2, CheckCircle2, Target as TargetIcon } from 'lucide-react';
import toast from 'react-hot-toast';
import { goalApi } from '../api/endpoints';
import { useAuth } from '../context/AuthContext';
import PageHeader from '../components/PageHeader';
import Loader from '../components/Loader';
import EmptyState from '../components/EmptyState';
import { fmtDate, today, num } from '../utils/format';

const TYPES = [
  { v: 'WEIGHT_LOSS', l: 'Weight loss' },
  { v: 'WEIGHT_GAIN', l: 'Weight gain' },
  { v: 'MUSCLE_GAIN', l: 'Muscle gain' },
  { v: 'MAINTENANCE', l: 'Maintenance' },
];

export default function Goals() {
  const { user } = useAuth();
  const [goals, setGoals] = useState([]);
  const [loading, setLoading] = useState(true);
  const [form, setForm] = useState({
    goalType: 'WEIGHT_LOSS',
    targetWeight: '',
    targetCalories: '',
    weeklyTarget: '',
    startDate: today(),
    targetDate: today(),
  });
  const [busy, setBusy] = useState(false);

  const load = async () => {
    setLoading(true);
    try { setGoals((await goalApi.list(user.id)) || []); } finally { setLoading(false); }
  };
  useEffect(() => { if (user?.id) load(); }, [user?.id]);

  const submit = async (e) => {
    e.preventDefault();
    setBusy(true);
    try {
      await goalApi.create(user.id, {
        goalType: form.goalType,
        targetWeight: Number(form.targetWeight) || null,
        targetCalories: Number(form.targetCalories) || null,
        weeklyTarget: Number(form.weeklyTarget) || null,
        startDate: form.startDate,
        targetDate: form.targetDate,
        completed: false,
      });
      toast.success('Goal added.');
      setForm({ ...form, targetWeight: '', targetCalories: '', weeklyTarget: '' });
      load();
    } catch (err) { toast.error(err?.response?.data?.message || 'Failed'); }
    finally { setBusy(false); }
  };

  const complete = async (id) => { await goalApi.complete(id); toast.success('Well done!'); load(); };
  const remove = async (id) => { if (!confirm('Delete?')) return; await goalApi.delete(id); load(); };

  return (
    <div>
      <PageHeader title="Goals" subtitle="Set the direction. Small commitments compound." />

      <div className="grid gap-6 lg:grid-cols-[1.4fr,1fr]">
        <div>
          {loading ? <Loader /> : goals.length === 0 ? (
            <EmptyState title="No goals yet" hint="Set your first north-star target on the right." />
          ) : (
            <div className="grid gap-4 md:grid-cols-2">
              {goals.map((g) => (
                <div key={g.id} className={`card relative ${g.completed ? 'opacity-70' : ''}`} data-testid={`goal-card-${g.id}`}>
                  <div className="mb-2 flex items-center justify-between">
                    <span className="chip"><TargetIcon className="h-3 w-3" /> {g.goalType?.replaceAll('_', ' ')}</span>
                    {g.completed ? <span className="chip !bg-sage-500 !text-cream-50">Completed</span> : <span className="chip">Active</span>}
                  </div>
                  <p className="h-serif text-2xl font-semibold text-ink-900">
                    {num(g.targetWeight)} kg · {num(g.targetCalories, 0)} kcal
                  </p>
                  <p className="mt-1 text-xs text-ink-500">
                    Weekly Δ {num(g.weeklyTarget)} kg · {fmtDate(g.startDate)} → {fmtDate(g.targetDate)}
                  </p>
                  <div className="mt-4 flex gap-2">
                    {!g.completed && (
                      <button className="btn-secondary flex-1" onClick={() => complete(g.id)} data-testid={`goal-complete-${g.id}`}>
                        <CheckCircle2 className="h-4 w-4" /> Complete
                      </button>
                    )}
                    <button className="btn-danger" onClick={() => remove(g.id)}>
                      <Trash2 className="h-3.5 w-3.5" />
                    </button>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>

        <form onSubmit={submit} className="card space-y-3" data-testid="goal-form">
          <p className="h-serif text-xl font-semibold text-ink-900">New goal</p>
          <div>
            <label className="label">Type</label>
            <select className="input" value={form.goalType} onChange={(e) => setForm({ ...form, goalType: e.target.value })}>
              {TYPES.map((t) => <option key={t.v} value={t.v}>{t.l}</option>)}
            </select>
          </div>
          <div className="grid grid-cols-2 gap-3">
            <div>
              <label className="label">Target weight (kg)</label>
              <input type="number" step="0.1" className="input" value={form.targetWeight} onChange={(e) => setForm({ ...form, targetWeight: e.target.value })} data-testid="goal-target-weight" />
            </div>
            <div>
              <label className="label">Target kcal</label>
              <input type="number" className="input" value={form.targetCalories} onChange={(e) => setForm({ ...form, targetCalories: e.target.value })} />
            </div>
          </div>
          <div>
            <label className="label">Weekly Δ (kg)</label>
            <input type="number" step="0.1" className="input" value={form.weeklyTarget} onChange={(e) => setForm({ ...form, weeklyTarget: e.target.value })} />
          </div>
          <div className="grid grid-cols-2 gap-3">
            <div>
              <label className="label">Start</label>
              <input type="date" className="input" value={form.startDate} onChange={(e) => setForm({ ...form, startDate: e.target.value })} />
            </div>
            <div>
              <label className="label">Deadline</label>
              <input type="date" className="input" value={form.targetDate} onChange={(e) => setForm({ ...form, targetDate: e.target.value })} />
            </div>
          </div>
          <button type="submit" disabled={busy} className="btn-primary w-full" data-testid="goal-submit">
            <Plus className="h-4 w-4" /> Create goal
          </button>
        </form>
      </div>
    </div>
  );
}
