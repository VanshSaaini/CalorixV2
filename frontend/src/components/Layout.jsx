import { useState } from 'react';
import { Outlet } from 'react-router-dom';
import { Menu } from 'lucide-react';
import Sidebar from './Sidebar';

export default function Layout() {
  const [open, setOpen] = useState(false);
  return (
    <div className="flex min-h-screen w-full bg-cream-50">
      <Sidebar open={open} onClose={() => setOpen(false)} />
      <main className="flex min-h-screen flex-1 flex-col">
        <header className="sticky top-0 z-20 flex items-center gap-3 border-b border-cream-200 bg-cream-50/80 px-5 py-3 backdrop-blur md:hidden">
          <button
            onClick={() => setOpen(true)}
            className="rounded-full border border-cream-200 p-2"
            data-testid="open-sidebar-btn"
          >
            <Menu className="h-4 w-4" />
          </button>
          <p className="h-serif text-xl font-semibold">Calorix</p>
        </header>
        <div className="flex-1 px-5 py-6 md:px-10 md:py-10">
          <Outlet />
        </div>
      </main>
    </div>
  );
}
