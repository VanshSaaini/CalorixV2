import { useEffect, useState } from 'react';
import { Plus, Trash2, ShieldCheck, Users } from 'lucide-react';
import toast from 'react-hot-toast';
import { roleApi, userApi } from '../api/endpoints';
import PageHeader from '../components/PageHeader';
import Loader from '../components/Loader';

export default function Admin() {
  const [roles, setRoles] = useState([]);
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [newRole, setNewRole] = useState('ROLE_');
  const [busy, setBusy] = useState(false);

  const load = async () => {
    setLoading(true);
    try {
      const [r, u] = await Promise.all([roleApi.list(), userApi.all()]);
      setRoles(r || []);
      setUsers(u || []);
    } catch {
      toast.error('Insufficient permission to view admin data');
    } finally {
      setLoading(false);
    }
  };
  useEffect(() => { load(); }, []);

  const addRole = async (e) => {
    e.preventDefault();
    if (!/^ROLE_[A-Z_]+$/.test(newRole)) {
      toast.error('Role must match ROLE_NAME format');
      return;
    }
    setBusy(true);
    try {
      await roleApi.create({ name: newRole });
      setNewRole('ROLE_');
      toast.success('Role created.');
      load();
    } catch (err) { toast.error(err?.response?.data?.message || 'Failed'); }
    finally { setBusy(false); }
  };

  const removeRole = async (id) => {
    if (!confirm('Delete role?')) return;
    await roleApi.delete(id);
    load();
  };

  return (
    <div>
      <PageHeader title="Admin Console" subtitle="Manage users & roles." />

      {loading ? <Loader /> : (
        <div className="grid gap-6 lg:grid-cols-[1.5fr,1fr]">
          <div className="card">
            <div className="mb-4 flex items-center gap-2">
              <Users className="h-4 w-4 text-sage-500" />
              <p className="text-xs uppercase tracking-[0.24em] text-ink-500">Users · {users.length}</p>
            </div>
            <div className="overflow-x-auto">
              <table className="w-full text-sm">
                <thead className="border-b border-cream-200">
                  <tr className="text-left text-xs uppercase tracking-[0.2em] text-ink-500">
                    <th className="px-4 py-3">ID</th>
                    <th className="px-4 py-3">Name</th>
                    <th className="px-4 py-3">Email</th>
                    <th className="px-4 py-3">Role</th>
                    <th className="px-4 py-3">Verified</th>
                  </tr>
                </thead>
                <tbody>
                  {users.map((u) => (
                    <tr key={u.id} className="border-b border-cream-100 last:border-0" data-testid={`admin-user-${u.id}`}>
                      <td className="px-4 py-3 font-mono text-xs text-ink-500">#{u.id}</td>
                      <td className="px-4 py-3 font-semibold">{u.firstName} {u.lastName}</td>
                      <td className="px-4 py-3">{u.email}</td>
                      <td className="px-4 py-3"><span className="chip">{u.role || '—'}</span></td>
                      <td className="px-4 py-3">{u.emailVerified ? '✓' : '—'}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>

          <div className="card">
            <div className="mb-4 flex items-center gap-2">
              <ShieldCheck className="h-4 w-4 text-clay-500" />
              <p className="text-xs uppercase tracking-[0.24em] text-ink-500">Roles · {roles.length}</p>
            </div>
            <form onSubmit={addRole} className="mb-4 flex gap-2" data-testid="role-form">
              <input className="input" value={newRole} onChange={(e) => setNewRole(e.target.value.toUpperCase())} placeholder="ROLE_EDITOR" data-testid="role-name" />
              <button type="submit" disabled={busy} className="btn-primary"><Plus className="h-4 w-4" /></button>
            </form>
            <div className="space-y-2">
              {roles.map((r) => (
                <div key={r.id} className="flex items-center justify-between rounded-2xl border border-cream-200 bg-white/60 px-4 py-2.5" data-testid={`role-item-${r.id}`}>
                  <span className="font-semibold text-ink-900">{r.name}</span>
                  <button className="btn-ghost text-clay-600" onClick={() => removeRole(r.id)}>
                    <Trash2 className="h-3.5 w-3.5" />
                  </button>
                </div>
              ))}
              {roles.length === 0 && <p className="text-sm text-ink-500">No roles.</p>}
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
