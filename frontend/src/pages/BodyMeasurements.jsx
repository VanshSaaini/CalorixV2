import { useEffect, useState } from 'react';
import { Plus, Trash2 } from 'lucide-react';
import toast from 'react-hot-toast';
import { bodyApi } from '../api/endpoints';
import { useAuth } from '../context/AuthContext';
import PageHeader from '../components/PageHeader';
import Loader from '../components/Loader';
import TrendChart from '../components/TrendChart';
import EmptyState from '../components/EmptyState';
import { fmtDate, today, num } from '../utils/format';

const FIELDS = [
  ['neck', 'Neck'], ['chest', 'Chest'], ['waist', 'Waist'], ['hips', 'Hips'],
  ['leftArm', 'Left arm'], ['rightArm', 'Right arm'],
  ['leftThigh', 'Left thigh'], ['rightThigh', 'Right thigh'],
  ['leftCalf', 'Left calf'], ['rightCalf', 'Right calf'],
];

const initial = () => Object.fromEntries([...FIELDS.map(([k]) => [k, '']), ['recordDate', today()]]);

export default function BodyMeasurements() {
  const { user } = useAuth();
  const [records, setRecords] = useState([]);
  const [loading, setLoading] = useState(true);
  const [form, setForm] = useState(initial);
  const [busy, setBusy] = useState(false);

  const load = async () => {
    setLoading(true);
    try { setRecords((await bodyApi.list(user.id)) || []); } finally { setLoading(false); }
  };
  useEffect(() => { if (user?.id) load(); }, [user?.id]);

  const submit = async (e) => {
    e.preventDefault();
    setBusy(true);
    try {
      const payload = { recordDate: form.recordDate };
      for (const [k] of FIELDS) payload[k] = form[k] ? Number(form[k]) : null;
      await bodyApi.save(user.id, payload);
      toast.success('Measurements saved.');
      setForm(initial());
      load();
    } catch (err) { toast.error(err?.response?.data?.message || 'Failed'); }
    finally { setBusy(false); }
  };

  const remove = async (id) => { if (!confirm('Delete?')) return; await bodyApi.delete(id); load(); };

  return (
    <div>
      <PageHeader title="Body Measurements" subtitle="Tape-measure numbers, thoughtfully recorded." />

      <div className="grid gap-6 lg:grid-cols-[1.4fr,1fr]">
        <div className="card">
          <p className="text-xs uppercase tracking-[0.24em] text-ink-500">Waist trend</p>
          {loading ? <Loader /> : <TrendChart data={records} dataKey="waist" unit="cm" color="clay" />}
        </div>

        <form onSubmit={submit} className="card space-y-3" data-testid="body-form">
          <p className="h-serif text-xl font-semibold text-ink-900">New measurement</p>
          <div className="grid grid-cols-2 gap-3">
            {FIELDS.map(([k, label]) => (
              <div key={k}>
                <label className="label">{label} (cm)</label>
                <input type="number" step="0.1" className="input" value={form[k]} onChange={(e) => setForm({ ...form, [k]: e.target.value })} />
              </div>
            ))}
          </div>
          <div>
            <label className="label">Date</label>
            <input type="date" className="input" value={form.recordDate} onChange={(e) => setForm({ ...form, recordDate: e.target.value })} />
          </div>
          <button type="submit" disabled={busy} className="btn-primary w-full" data-testid="body-submit">
            <Plus className="h-4 w-4" /> Save
          </button>
        </form>
      </div>

      <div className="mt-6">
        <p className="mb-3 text-xs uppercase tracking-[0.24em] text-ink-500">History</p>
        {records.length === 0 && !loading ? (
          <EmptyState title="No measurements yet" hint="Add your first measurement — even one field is a start." />
        ) : (
          <div className="overflow-x-auto rounded-3xl border border-cream-200 bg-cream-50">
            <table className="w-full text-sm">
              <thead className="border-b border-cream-200 bg-cream-100">
                <tr className="text-left text-xs uppercase tracking-[0.2em] text-ink-500">
                  <th className="px-4 py-3">Date</th>
                  {FIELDS.map(([k, l]) => (<th key={k} className="px-3 py-3">{l}</th>))}
                  <th className="px-4 py-3"></th>
                </tr>
              </thead>
              <tbody>
                {records.map((r) => (
                  <tr key={r.id} className="border-b border-cream-100 last:border-0">
                    <td className="px-4 py-3 whitespace-nowrap">{fmtDate(r.recordDate)}</td>
                    {FIELDS.map(([k]) => (<td key={k} className="px-3 py-3">{r[k] != null ? num(r[k]) : '—'}</td>))}
                    <td className="px-4 py-3 text-right">
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
