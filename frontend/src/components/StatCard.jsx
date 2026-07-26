import clsx from 'clsx';

export default function StatCard({ label, value, unit, hint, tone = 'sage', icon: Icon, testid }) {
  const tones = {
    sage: 'from-sage-500/90 to-sage-400/80 text-cream-50',
    cream: 'from-cream-100 to-cream-50 text-ink-900 border border-cream-200',
    clay: 'from-clay-500/90 to-clay-400/80 text-cream-50',
    ink: 'from-ink-900 to-ink-700 text-cream-50',
  };
  return (
    <div
      data-testid={testid}
      className={clsx(
        'reveal relative flex flex-col justify-between overflow-hidden rounded-3xl bg-gradient-to-br p-6 shadow-soft',
        tones[tone]
      )}
    >
      <div className="flex items-start justify-between">
        <p className={clsx('text-[11px] uppercase tracking-[0.24em]', tone === 'cream' ? 'text-ink-500' : 'opacity-80')}>
          {label}
        </p>
        {Icon && <Icon className="h-4.5 w-4.5 opacity-70" strokeWidth={1.6} />}
      </div>
      <div className="mt-6 flex items-baseline gap-1.5">
        <span className="h-serif text-4xl font-semibold leading-none">{value ?? '—'}</span>
        {unit && <span className="text-sm opacity-80">{unit}</span>}
      </div>
      {hint && <p className={clsx('mt-2 text-xs', tone === 'cream' ? 'text-ink-500' : 'opacity-70')}>{hint}</p>}
    </div>
  );
}
