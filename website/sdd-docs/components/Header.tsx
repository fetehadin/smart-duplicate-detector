"use client";

import Link from "next/link";
import { Star, Sun, Moon } from "lucide-react";
import { useTheme } from "next-themes";

export default function Header() {
  const { theme, setTheme } = useTheme();

  return (
    <header className="sticky top-0 z-50 w-full border-b border-slate-200 dark:border-zinc-800 bg-white/90 dark:bg-zinc-900/90 backdrop-blur-md">
      <div className="container mx-auto px-6 h-16 flex items-center justify-between">
        <Link href="/" className="flex items-center gap-2 font-bold text-lg hover:text-sky-500 transition-colors">
          <span className="bg-sky-600 text-white px-2 py-1 rounded">SDD</span>
          Smart Duplicate Detector
        </Link>

        <nav className="flex items-center gap-6 text-sm font-medium">
          <Link href="/docs" className="text-slate-600 dark:text-zinc-400 hover:text-slate-900 dark:hover:text-zinc-100">
            Documentation
          </Link>

          {/* Single Theme Toggle Button */}
          <button
            onClick={() => setTheme(theme === "dark" ? "light" : "dark")}
            className="p-2 rounded-md border border-slate-200 dark:border-zinc-700 bg-slate-100 dark:bg-zinc-800 text-slate-700 dark:text-zinc-300"
            aria-label="Toggle Theme"
          >
            <Sun size={16} className="hidden dark:block text-amber-400" />
            <Moon size={16} className="block dark:hidden text-sky-600" />
          </button>

          <a 
            href="https://github.com/fetehadin/smart-duplicate-detector" 
            target="_blank" 
            rel="noreferrer"
            className="hidden sm:flex items-center gap-2 px-4 py-2 rounded-md border border-slate-200 dark:border-zinc-700 bg-slate-100 dark:bg-zinc-800"
          >
            <Star size={16} />
            Star on GitHub
          </a>
        </nav>
      </div>
    </header>
  );
}