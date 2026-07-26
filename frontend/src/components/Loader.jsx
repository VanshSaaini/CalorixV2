export default function Loader({ label = 'Loading' }) {
  return (
    <div className="flex h-full min-h-[240px] w-full items-center justify-center" data-testid="loader">
      <div className="flex flex-col items-center gap-3">
        <div className="h-9 w-9 animate-spin rounded-full border-2 border-sage-200 border-t-sage-500" />
        <p className="text-xs uppercase tracking-[0.2em] text-ink-500">{label}</p>
      </div>
    </div>
  );
}
