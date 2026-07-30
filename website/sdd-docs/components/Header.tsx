"use client";

import Link from "next/link";
import { Star, Sun, Moon } from "lucide-react";
import { useTheme } from "next-themes";
import { useEffect, useState } from "react";

export default function Header() {
  const { theme, setTheme } = useTheme();
  const [mounted, setMounted] = useState(false);

  useEffect(() => {
    setMounted(true);
  }, []);

  return (
    <header className="sticky top-0 z-50 w-full border-b border-slate-200 dark:border-zinc-800/80 bg-white/80 dark:bg-zinc-900/80 backdrop-blur-md transition-colors duration-300">
      <div className="container mx-auto px-6 h-16 flex items-center justify-between">
        <Link
          href="/"
          className="flex items-center gap-2.5 font-bold text-lg tracking-wide hover:opacity-90 transition-opacity"
        >
          <span className="bg-sky-600 text-white px-2 py-0.5 rounded text-sm font-extrabold shadow-sm">
            SDD
          </span>
          <span className="text-slate-900 dark:text-zinc-100">
            Smart Duplicate Detector
          </span>
        </Link>

        <nav className="flex items-center gap-6 text-sm font-medium">
          <Link
            href="/docs"
            className="text-slate-600 dark:text-zinc-400 hover:text-slate-900 dark:hover:text-zinc-100 transition-colors"
          >
            Documentation
          </Link>

          {mounted && (
            <button
              onClick={() => setTheme(theme === "dark" ? "light" : "dark")}
              className="p-2 rounded-md border border-slate-200 dark:border-zinc-800 bg-slate-100 dark:bg-zinc-800/60 text-slate-700 dark:text-zinc-300 hover:bg-slate-200 dark:hover:bg-zinc-800 transition-all cursor-pointer"
              aria-label="Toggle Theme"
            >
              {theme === "dark" ? (
                <Sun size={16} className="text-amber-400" />
              ) : (
                <Moon size={16} className="text-sky-600" />
              )}
            </button>
          )}

          <a
            href="https://github.com/fetehadin/smart-duplicate-detector"
            target="_blank"
            rel="noreferrer"
            className="hidden sm:flex items-center gap-2 px-3.5 py-1.5 rounded-md border border-slate-200 dark:border-zinc-800 bg-slate-100 dark:bg-zinc-800/60 text-slate-800 dark:text-zinc-200 hover:bg-slate-200 dark:hover:bg-zinc-800 transition-all text-xs font-semibold shadow-sm"
          >
            <Star size={14} className="text-amber-500 fill-amber-500" />
            Star on GitHub
          </a>
        </nav>
      </div>
    </header>
  );
}