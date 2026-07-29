import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import Link from "next/link";
import { Star, Menu } from "lucide-react";

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "Smart Duplicate Detector",
  description: "A smart, AST-powered static analysis tool for Java.",
};

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="en" className="dark scroll-smooth">
      <body suppressHydrationWarning className={`${inter.className} min-h-screen flex flex-col bg-zinc-900 text-zinc-300 antialiased selection:bg-sky-600/30 selection:text-sky-200`}>
        {/* Navbar */}
        <header className="sticky top-0 z-50 w-full border-b border-zinc-800 bg-zinc-900/90 backdrop-blur-md">
          <div className="container mx-auto px-6 h-16 flex items-center justify-between">
            <Link href="/" className="flex items-center gap-2 text-zinc-100 font-bold text-lg tracking-wide hover:text-sky-400 transition-colors">
              <span className="bg-sky-600 text-white px-2 py-1 rounded">SDD</span>
              Smart Duplicate Detector
            </Link>

            {/* Desktop Nav */}
            <nav className="hidden md:flex items-center gap-6 text-sm font-medium">
              <Link href="/docs" className="text-zinc-400 hover:text-zinc-100 transition-colors">Documentation</Link>
              <Link href="https://github.com/fetehadin/smart-duplicate-detector/pulls" className="text-zinc-400 hover:text-zinc-100 transition-colors">Contribute</Link>
              <a 
                href="https://github.com/fetehadin/smart-duplicate-detector" 
                target="_blank" 
                rel="noreferrer"
                className="flex items-center gap-2 bg-zinc-800 hover:bg-zinc-700 text-zinc-100 px-4 py-2 rounded-md border border-zinc-700 transition-all shadow-sm"
              >
                <Star size={16} className="text-zinc-400" />
                Star on GitHub
              </a>
            </nav>

            {/* Mobile Nav Toggle */}
            <button className="md:hidden text-zinc-400 hover:text-zinc-100">
              <Menu size={24} />
            </button>
          </div>
        </header>

        {/* Main Content */}
        <main className="flex-1">
          {children}
        </main>

        {/* Footer */}
        <footer className="border-t border-zinc-800 py-8 text-center text-sm text-zinc-500">
          <div className="container mx-auto px-6">
            <p>Built by Fetehadin Negash. Released under the MIT License.</p>
          </div>
        </footer>
      </body>
    </html>
  );
}