import { Link } from 'react-router-dom';
import { Leaf, Activity, Droplets, Target, ArrowUpRight, Sparkles } from 'lucide-react';

export default function Landing() {
  return (
    <div className="min-h-screen bg-cream-50 bg-grain">
      {/* Nav */}
      <header className="mx-auto flex max-w-7xl items-center justify-between px-6 py-6 md:px-10">
        <div className="flex items-center gap-2.5">
          <span className="grid h-10 w-10 place-items-center rounded-2xl bg-sage-500 text-cream-50 shadow-soft">
            <Leaf className="h-5 w-5" />
          </span>
          <div>
            <p className="h-serif text-2xl font-semibold leading-none text-ink-900">Calorix</p>
            <p className="text-[10px] uppercase tracking-[0.3em] text-ink-500">v2 · wellness</p>
          </div>
        </div>
        <div className="flex items-center gap-3">
          <Link to="/login" className="btn-ghost" data-testid="landing-login-link">
            Log in
          </Link>
          <Link to="/register" className="btn-primary" data-testid="landing-register-link">
            Get started <ArrowUpRight className="h-4 w-4" />
          </Link>
        </div>
      </header>

      {/* Hero */}
      <section className="mx-auto grid max-w-7xl gap-16 px-6 py-12 md:grid-cols-[1.15fr,1fr] md:px-10 md:py-24">
        <div className="reveal">
          <span className="chip">
            <Sparkles className="h-3 w-3" /> A softer approach to fitness
          </span>
          <h1 className="mt-6 h-serif text-5xl font-semibold leading-[1.02] text-ink-900 md:text-7xl">
            Track your body,
            <br />
            <em className="not-italic text-sage-500">balance your life.</em>
          </h1>
          <p className="mt-6 max-w-lg text-base text-ink-500 md:text-lg">
            CalorixV2 is a calm, classic companion for weight, BMI, BMR, hydration, macros and
            goals — designed for people who want progress without the noise.
          </p>
          <div className="mt-8 flex flex-wrap items-center gap-3">
            <Link to="/register" className="btn-primary" data-testid="hero-cta">
              Start free · 2 min setup
            </Link>
            <Link to="/login" className="btn-secondary">
              I already have an account
            </Link>
          </div>

          <div className="mt-14 grid max-w-lg grid-cols-3 gap-6">
            {[
              { k: '9', v: 'trackers' },
              { k: '∞', v: 'entries' },
              { k: '0%', v: 'noise' },
            ].map((s) => (
              <div key={s.v}>
                <p className="h-serif text-4xl font-semibold text-ink-900">{s.k}</p>
                <p className="text-xs uppercase tracking-[0.24em] text-ink-500">{s.v}</p>
              </div>
            ))}
          </div>
        </div>

        {/* Visual */}
        <div className="relative reveal">
          <div className="absolute -inset-6 rounded-[40px] bg-sage-100 blur-2xl opacity-70" />
          <div className="relative rounded-[36px] border border-cream-200 bg-cream-50 p-6 shadow-soft">
            <div className="mb-5 flex items-center justify-between">
              <p className="text-xs uppercase tracking-[0.24em] text-ink-500">Today</p>
              <span className="chip">On track</span>
            </div>
            <div className="grid grid-cols-2 gap-3">
              <MiniStat icon={Activity} label="BMI" value="22.4" tone="sage" />
              <MiniStat icon={Droplets} label="Water" value="2.1 L" tone="ink" />
              <MiniStat icon={Target} label="Goal" value="-3 kg" tone="clay" />
              <MiniStat icon={Leaf} label="Calories" value="1,820" tone="sage" />
            </div>
            <div className="mt-5 rounded-2xl border border-cream-200 bg-white p-4">
              <p className="text-xs uppercase tracking-[0.22em] text-ink-500">Weight trend · 7d</p>
              <div className="mt-3 flex h-24 items-end gap-1.5">
                {[42, 55, 48, 60, 52, 63, 58].map((h, i) => (
                  <div
                    key={i}
                    className="flex-1 rounded-t-md bg-gradient-to-t from-sage-200 to-sage-500"
                    style={{ height: `${h}%` }}
                  />
                ))}
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Features */}
      <section className="mx-auto max-w-7xl px-6 pb-24 md:px-10">
        <div className="grid gap-6 md:grid-cols-3">
          {[
            {
              t: 'Full picture, one screen',
              d: 'Weight, BMI, BMR, calories, macros, water & goals aggregated into one calm dashboard.',
            },
            {
              t: 'Charts that actually help',
              d: 'Interactive trend graphs so you can see the shape of your progress week by week.',
            },
            {
              t: 'Goals with grace',
              d: 'Set a target, track weekly deltas, mark it complete. No streak shaming.',
            },
          ].map((f) => (
            <div key={f.t} className="card">
              <p className="h-serif text-2xl font-semibold text-ink-900">{f.t}</p>
              <p className="mt-2 text-sm text-ink-500">{f.d}</p>
            </div>
          ))}
        </div>
      </section>

      <footer className="border-t border-cream-200 bg-cream-100/50 px-6 py-8 md:px-10">
        <div className="mx-auto flex max-w-7xl flex-col items-center justify-between gap-2 text-xs text-ink-500 md:flex-row">
          <p>© {new Date().getFullYear()} CalorixV2 — Track. Balance. Thrive.</p>
          <p className="uppercase tracking-[0.24em]">Built with intention</p>
        </div>
      </footer>
    </div>
  );
}

function MiniStat({ icon: Icon, label, value, tone }) {
  const map = {
    sage: 'bg-sage-500 text-cream-50',
    clay: 'bg-clay-500 text-cream-50',
    ink: 'bg-ink-900 text-cream-50',
  };
  return (
    <div className={`rounded-2xl p-3.5 ${map[tone]}`}>
      <Icon className="h-4 w-4 opacity-80" />
      <p className="mt-2 text-[10px] uppercase tracking-[0.24em] opacity-80">{label}</p>
      <p className="h-serif text-2xl font-semibold leading-none">{value}</p>
    </div>
  );
}
