import { NavLink, useNavigate } from 'react-router-dom';
import {
  LayoutDashboard,
  Scale,
  Activity,
  Flame,
  Droplets,
  Utensils,
  Ruler,
  Target,
  Image as ImgIcon,
  User,
  ShieldCheck,
  LogOut,
  Leaf,
} from 'lucide-react';
import { useAuth } from '../context/AuthContext';
import clsx from 'clsx';

const nav = [
  { to: '/dashboard', label: 'Dashboard', icon: LayoutDashboard, testid: 'nav-dashboard' },
  { to: '/weight', label: 'Weight', icon: Scale, testid: 'nav-weight' },
  { to: '/bmi', label: 'BMI', icon: Activity, testid: 'nav-bmi' },
  { to: '/bmr', label: 'BMR', icon: Flame, testid: 'nav-bmr' },
  { to: '/calories', label: 'Calories', icon: Flame, testid: 'nav-calories' },
  { to: '/macros', label: 'Macros', icon: Utensils, testid: 'nav-macros' },
  { to: '/water', label: 'Water', icon: Droplets, testid: 'nav-water' },
  { to: '/body-measurements', label: 'Body', icon: Ruler, testid: 'nav-body' },
  { to: '/goals', label: 'Goals', icon: Target, testid: 'nav-goals' },
  { to: '/photos', label: 'Photos', icon: ImgIcon, testid: 'nav-photos' },
  { to: '/profile', label: 'Profile', icon: User, testid: 'nav-profile' },
];

export default function Sidebar({ open, onClose }) {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const isAdmin = user?.role === 'ROLE_ADMIN' || user?.role === 'ADMIN';

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <>
      {open && (
        <div
          className="fixed inset-0 z-30 bg-ink-900/30 backdrop-blur-sm md:hidden"
          onClick={onClose}
        />
      )}
      <aside
        className={clsx(
          'fixed inset-y-0 left-0 z-40 flex w-72 flex-col border-r border-cream-200 bg-cream-50 px-5 py-6 transition-transform md:sticky md:top-0 md:h-screen md:translate-x-0',
          open ? 'translate-x-0' : '-translate-x-full'
        )}
        data-testid="sidebar"
      >
        <NavLink to="/dashboard" className="mb-8 flex items-center gap-2.5">
          <span className="grid h-10 w-10 place-items-center rounded-2xl bg-sage-500 text-cream-50 shadow-soft">
            <Leaf className="h-5 w-5" />
          </span>
          <div>
            <p className="h-serif text-2xl font-semibold leading-none text-ink-900">Calorix</p>
            <p className="text-[10px] uppercase tracking-[0.3em] text-ink-500">v2 · wellness</p>
          </div>
        </NavLink>

        <nav className="flex-1 space-y-1 overflow-y-auto">
          {nav.map(({ to, label, icon: Icon, testid }) => (
            <NavLink
              key={to}
              to={to}
              onClick={onClose}
              data-testid={testid}
              className={({ isActive }) =>
                clsx(
                  'group flex items-center gap-3 rounded-2xl px-3.5 py-2.5 text-sm font-medium transition-all',
                  isActive
                    ? 'bg-sage-500 text-cream-50 shadow-soft'
                    : 'text-ink-700 hover:bg-cream-100 hover:text-ink-900'
                )
              }
            >
              <Icon className="h-4.5 w-4.5" strokeWidth={1.75} />
              <span>{label}</span>
            </NavLink>
          ))}

          {isAdmin && (
            <NavLink
              to="/admin"
              onClick={onClose}
              data-testid="nav-admin"
              className={({ isActive }) =>
                clsx(
                  'group flex items-center gap-3 rounded-2xl px-3.5 py-2.5 text-sm font-medium transition-all',
                  isActive
                    ? 'bg-clay-500 text-cream-50 shadow-soft'
                    : 'text-clay-600 hover:bg-cream-100'
                )
              }
            >
              <ShieldCheck className="h-4.5 w-4.5" strokeWidth={1.75} />
              <span>Admin Console</span>
            </NavLink>
          )}
        </nav>

        <div className="mt-4 rounded-2xl border border-cream-200 bg-white/60 p-3">
          <p className="truncate text-sm font-semibold text-ink-900" data-testid="sidebar-user-name">
            {user?.firstName} {user?.lastName}
          </p>
          <p className="truncate text-xs text-ink-500">{user?.email}</p>
          <button
            onClick={handleLogout}
            data-testid="logout-btn"
            className="mt-3 flex w-full items-center justify-center gap-2 rounded-full border border-cream-200 bg-cream-50 px-3 py-2 text-xs font-semibold text-ink-700 transition hover:border-clay-500/40 hover:text-clay-600"
          >
            <LogOut className="h-3.5 w-3.5" /> Log out
          </button>
        </div>
      </aside>
    </>
  );
}
