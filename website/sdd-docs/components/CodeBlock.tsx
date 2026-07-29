"use client";

import { useState } from "react";
import { Check, Copy, Terminal } from "lucide-react";

export default function CodeBlock({ code, isTerminal = false }: { code: string; isTerminal?: boolean }) {
  const [copied, setCopied] = useState(false);

  const handleCopy = () => {
    navigator.clipboard.writeText(code);
    setCopied(true);
    setTimeout(() => setCopied(false), 2000);
  };

  return (
    <div className="relative group rounded-md bg-[#1E1E1E] border border-zinc-700 overflow-hidden shadow-sm">
      <div className="flex items-center justify-between px-3 py-1.5 bg-zinc-800 border-b border-zinc-700">
        <div className="flex items-center gap-2 text-zinc-400 text-xs font-semibold uppercase tracking-wider">
          {isTerminal && <Terminal size={14} />}
          <span>{isTerminal ? "Terminal" : "Bash"}</span>
        </div>
        <button
          onClick={handleCopy}
          className="text-zinc-400 hover:text-zinc-100 transition-colors p-1"
          aria-label="Copy code"
        >
          {copied ? <Check size={14} className="text-emerald-500" /> : <Copy size={14} />}
        </button>
      </div>
      <div className="p-4 overflow-x-auto">
        <code className="font-mono text-sm text-sky-300">{code}</code>
      </div>
    </div>
  );
}