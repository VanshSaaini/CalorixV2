import { Sparkles } from 'lucide-react';

export default function EmptyState({ title = 'Nothing here yet', hint }) {
  return (
    <div className="flex flex-col items-center justify-center rounded-3xl border border-dashed border-cream-200 bg-cream-50 px-6 py-16 text-center">
      <span className="grid h-12 w-12 place-items-center rounded-2xl bg-sage-50 text-sage-500">
        <Sparkles className="h-5 w-5" />
      </span>
      <p className="mt-4 h-serif text-xl font-semibold text-ink-900">{title}</p>
      {hint && <p className="mt-1 max-w-sm text-sm text-ink-500">{hint}</p>}
    </div>
  );
}
