export default function PageHeader({ title, subtitle, action, testid }) {
  return (
    <div className="mb-8 flex flex-col items-start justify-between gap-4 md:flex-row md:items-end reveal">
      <div>
        <p className="mb-1 text-xs uppercase tracking-[0.3em] text-sage-500">CalorixV2</p>
        <h1 className="h-serif text-4xl font-semibold text-ink-900 md:text-5xl" data-testid={testid || 'page-title'}>
          {title}
        </h1>
        {subtitle && <p className="mt-2 max-w-xl text-sm text-ink-500">{subtitle}</p>}
      </div>
      {action && <div className="flex flex-wrap items-center gap-3">{action}</div>}
    </div>
  );
}
