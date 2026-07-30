"use client";

import { useState } from "react";
import { 
  BookOpen, 
  Terminal, 
  Cpu, 
  Sliders, 
  ShieldCheck, 
  Layers,
  Copy,
  Check,
  FileCode,
  GitCompare,
  Activity
} from "lucide-react";

export default function DocsPage() {
  const [activeSection, setActiveSection] = useState("overview");
  const [copiedCmd, setCopiedCmd] = useState<string | null>(null);

  const copyToClipboard = (text: string, id: string) => {
    navigator.clipboard.writeText(text);
    setCopiedCmd(id);
    setTimeout(() => setCopiedCmd(null), 2000);
  };

  const navItems = [
    { id: "overview", label: "Overview & Engine", icon: BookOpen },
    { id: "installation", label: "Installation & CLI", icon: Terminal },
    { id: "architecture", label: "AST & Scoring", icon: Cpu },
    { id: "usage", label: "GUI & Headless Mode", icon: Sliders },
  ];

  return (
    <div className="container mx-auto px-6 py-12 max-w-7xl">
      <div className="flex flex-col lg:flex-row gap-10">
        
        {/* Sidebar Navigation */}
        <aside className="w-full lg:w-64 shrink-0">
          <div className="sticky top-24 p-4 rounded-xl border border-slate-200 dark:border-zinc-800 bg-white dark:bg-zinc-900/80 shadow-sm transition-colors duration-300">
            <h2 className="text-xs font-bold uppercase tracking-wider text-slate-500 dark:text-zinc-400 mb-3 px-3">
              Documentation
            </h2>
            <nav className="flex flex-col gap-1">
              {navItems.map((item) => {
                const Icon = item.icon;
                const isActive = activeSection === item.id;
                return (
                  <button
                    key={item.id}
                    onClick={() => setActiveSection(item.id)}
                    className={`flex items-center gap-2.5 px-3 py-2 rounded-lg text-sm font-medium transition-all text-left cursor-pointer ${
                      isActive
                        ? "bg-sky-600 text-white shadow-sm font-semibold"
                        : "text-slate-700 dark:text-zinc-400 hover:bg-slate-100 dark:hover:bg-zinc-800 hover:text-slate-900 dark:hover:text-zinc-100"
                    }`}
                  >
                    <Icon size={16} />
                    {item.label}
                  </button>
                );
              })}
            </nav>
          </div>
        </aside>

        {/* Documentation Content Area */}
        <div className="flex-1 min-w-0 space-y-12">
          
          {/* Section: Overview */}
          {activeSection === "overview" && (
            <section className="space-y-6">
              <div className="border-b border-slate-200 dark:border-zinc-800 pb-4 transition-colors">
                <h1 className="text-3xl font-extrabold text-slate-900 dark:text-zinc-100 tracking-tight">
                  Overview & Technical Approach
                </h1>
                <p className="text-sm text-slate-600 dark:text-zinc-400 mt-1">
                  Why traditional token matching fails and how AST semantic analysis solves duplicate detection.
                </p>
              </div>

              <div className="p-6 rounded-xl border border-slate-200 dark:border-zinc-800 bg-white dark:bg-zinc-900/60 shadow-sm space-y-4 transition-colors">
                <h3 className="text-lg font-semibold text-slate-900 dark:text-zinc-100 flex items-center gap-2">
                  <ShieldCheck className="text-sky-600 dark:text-sky-400" size={20} />
                  The False-Positive Trap
                </h3>
                <p className="text-sm text-slate-700 dark:text-zinc-300 leading-relaxed">
                  Traditional duplicate code detectors rely on raw string matching or line-by-line tokenization. This creates massive false-positive noise by flagging boilerplate getters, setters, import blocks, and common interfaces as duplicated code.
                </p>
              </div>

              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div className="p-6 rounded-xl border border-slate-200 dark:border-zinc-800 bg-white dark:bg-zinc-900/60 shadow-sm space-y-3 transition-colors">
                  <h4 className="font-semibold text-slate-900 dark:text-zinc-100 flex items-center gap-2">
                    <Layers className="text-sky-600 dark:text-sky-400" size={18} />
                    1. AST Method Parsing
                  </h4>
                  <p className="text-sm text-slate-700 dark:text-zinc-300 leading-relaxed">
                    Powered by <strong>JavaParser</strong>, SDD parses Java codebases into an Abstract Syntax Tree (AST), isolating functional methods while ignoring class-level noise.
                  </p>
                </div>

                <div className="p-6 rounded-xl border border-slate-200 dark:border-zinc-800 bg-white dark:bg-zinc-900/60 shadow-sm space-y-3 transition-colors">
                  <h4 className="font-semibold text-slate-900 dark:text-zinc-100 flex items-center gap-2">
                    <Cpu className="text-sky-600 dark:text-sky-400" size={18} />
                    2. Weighted Levenshtein Scoring
                  </h4>
                  <p className="text-sm text-slate-700 dark:text-zinc-300 leading-relaxed">
                    Compares method structure and logic using custom weighted Levenshtein distance, ignoring minor whitespace drift and variable renaming.
                  </p>
                </div>
              </div>
            </section>
          )}

          {/* Section: Installation */}
          {activeSection === "installation" && (
            <section className="space-y-6">
              <div className="border-b border-slate-200 dark:border-zinc-800 pb-4 transition-colors">
                <h1 className="text-3xl font-extrabold text-slate-900 dark:text-zinc-100 tracking-tight">
                  Installation & Global CLI
                </h1>
                <p className="text-sm text-slate-600 dark:text-zinc-400 mt-1">
                  Install Smart Duplicate Detector globally using npm or build directly from source.
                </p>
              </div>

              <div className="space-y-4">
                <h3 className="text-base font-semibold text-slate-900 dark:text-zinc-100">
                  NPM Global Package
                </h3>
                <p className="text-sm text-slate-700 dark:text-zinc-300">
                  The fastest way to get started is via our NPM wrapper, which registers the <code className="px-1.5 py-0.5 rounded bg-slate-100 dark:bg-zinc-800 font-mono text-xs text-sky-600 dark:text-sky-400 font-bold">sdd</code> command globally:
                </p>

                <div className="relative rounded-xl border border-slate-300 dark:border-zinc-800 bg-slate-100 dark:bg-zinc-900 text-slate-900 dark:text-slate-100 p-4 font-mono text-sm shadow-sm flex items-center justify-between transition-colors">
                  <span className="font-semibold">npm install -g smart-duplicate-detector</span>
                  <button
                    onClick={() => copyToClipboard("npm install -g smart-duplicate-detector", "install")}
                    className="p-2 rounded-lg border border-slate-300 dark:border-zinc-700 bg-white dark:bg-zinc-800 hover:bg-slate-200 dark:hover:bg-zinc-700 text-slate-700 dark:text-slate-300 transition-colors cursor-pointer shadow-sm"
                    aria-label="Copy Command"
                  >
                    {copiedCmd === "install" ? <Check size={16} className="text-emerald-600 dark:text-emerald-400" /> : <Copy size={16} />}
                  </button>
                </div>
              </div>

              <div className="space-y-4 pt-4">
                <h3 className="text-base font-semibold text-slate-900 dark:text-zinc-100">
                  Prerequisites
                </h3>
                <ul className="list-disc list-inside text-sm text-slate-700 dark:text-zinc-300 space-y-2 pl-2">
                  <li><strong>Java 17</strong> or higher installed and available in your system path.</li>
                  <li><strong>Node.js 18+</strong> and NPM for global command wrapper usage.</li>
                  <li><strong>Maven 3.8+</strong> (only required if compiling the Java engine from source).</li>
                </ul>
              </div>
            </section>
          )}

          {/* Section: Architecture */}
          {activeSection === "architecture" && (
            <section className="space-y-8">
              <div className="border-b border-slate-200 dark:border-zinc-800 pb-4 transition-colors">
                <h1 className="text-3xl font-extrabold text-slate-900 dark:text-zinc-100 tracking-tight">
                  AST & Scoring Engine
                </h1>
                <p className="text-sm text-slate-600 dark:text-zinc-400 mt-1">
                  A look under the hood of the JavaParser extraction and Java Swing integration.
                </p>
              </div>

              {/* Component Layer Table */}
              <div className="overflow-x-auto border border-slate-200 dark:border-zinc-800 rounded-xl shadow-sm bg-white dark:bg-zinc-900/60 transition-colors">
                <table className="w-full text-left border-collapse text-sm">
                  <thead>
                    <tr className="border-b border-slate-200 dark:border-zinc-800 bg-slate-100 dark:bg-zinc-900">
                      <th className="py-3 px-4 font-semibold text-slate-900 dark:text-zinc-100">Package Layer</th>
                      <th className="py-3 px-4 font-semibold text-slate-900 dark:text-zinc-100">Component</th>
                      <th className="py-3 px-4 font-semibold text-slate-900 dark:text-zinc-100">Responsibility</th>
                    </tr>
                  </thead>
                  <tbody className="divide-y divide-slate-200 dark:divide-zinc-800">
                    <tr>
                      <td className="py-3 px-4 font-mono text-xs font-bold text-sky-600 dark:text-sky-400">core</td>
                      <td className="py-3 px-4 font-medium text-slate-900 dark:text-zinc-200">AstMethodParser.java</td>
                      <td className="py-3 px-4 text-slate-700 dark:text-zinc-300">Extracts method signatures and bodies into AST nodes.</td>
                    </tr>
                    <tr>
                      <td className="py-3 px-4 font-mono text-xs font-bold text-sky-600 dark:text-sky-400">core</td>
                      <td className="py-3 px-4 font-medium text-slate-900 dark:text-zinc-200">SimilarityAlgorithm.java</td>
                      <td className="py-3 px-4 text-slate-700 dark:text-zinc-300">Computes structural Levenshtein percentages.</td>
                    </tr>
                    <tr>
                      <td className="py-3 px-4 font-mono text-xs font-bold text-sky-600 dark:text-sky-400">gui</td>
                      <td className="py-3 px-4 font-medium text-slate-900 dark:text-zinc-200">MainFrame.java</td>
                      <td className="py-3 px-4 text-slate-700 dark:text-zinc-300">FlatDarkLaf Swing dashboard with background workers.</td>
                    </tr>
                    <tr>
                      <td className="py-3 px-4 font-mono text-xs font-bold text-sky-600 dark:text-sky-400">api</td>
                      <td className="py-3 px-4 font-medium text-slate-900 dark:text-zinc-200">ApiServer.java</td>
                      <td className="py-3 px-4 text-slate-700 dark:text-zinc-300">Javalin REST server delivering JSON scan reports.</td>
                    </tr>
                  </tbody>
                </table>
              </div>

              {/* Additional Architecture Notes */}
              <div className="space-y-4 pt-2">
                <h2 className="text-xl font-bold text-slate-900 dark:text-zinc-100 tracking-tight">
                  Engine Architecture Notes
                </h2>
                
                <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                  {/* Note 1: AST Normalization */}
                  <div className="p-5 rounded-xl border border-slate-200 dark:border-zinc-800 bg-white dark:bg-zinc-900/60 shadow-sm space-y-2.5 transition-colors">
                    <div className="flex items-center gap-2 font-semibold text-slate-900 dark:text-zinc-100">
                      <FileCode size={18} className="text-sky-600 dark:text-sky-400" />
                      <h4>AST Normalization</h4>
                    </div>
                    <p className="text-xs text-slate-600 dark:text-zinc-400 leading-relaxed">
                      Before comparison occurs, <code className="font-mono text-slate-800 dark:text-zinc-200">AstMethodParser</code> strips comments, annotations, imports, and indentation. Methods are normalized into a stream of structural node tokens so formatting differences never skew scores.
                    </p>
                  </div>

                  {/* Note 2: Weighted Scoring */}
                  <div className="p-5 rounded-xl border border-slate-200 dark:border-zinc-800 bg-white dark:bg-zinc-900/60 shadow-sm space-y-2.5 transition-colors">
                    <div className="flex items-center gap-2 font-semibold text-slate-900 dark:text-zinc-100">
                      <GitCompare size={18} className="text-sky-600 dark:text-sky-400" />
                      <h4>Weighted Scoring</h4>
                    </div>
                    <p className="text-xs text-slate-600 dark:text-zinc-400 leading-relaxed">
                      Unlike standard edit distance, <code className="font-mono text-slate-800 dark:text-zinc-200">SimilarityAlgorithm</code> assigns heavier penalties to control-flow divergence (<code className="font-mono">if</code>, <code className="font-mono">for</code>, <code className="font-mono">while</code>) than to renamed variables or literal value swaps.
                    </p>
                  </div>

                  {/* Note 3: Background Worker Execution */}
                  <div className="p-5 rounded-xl border border-slate-200 dark:border-zinc-800 bg-white dark:bg-zinc-900/60 shadow-sm space-y-2.5 transition-colors">
                    <div className="flex items-center gap-2 font-semibold text-slate-900 dark:text-zinc-100">
                      <Activity size={18} className="text-sky-600 dark:text-sky-400" />
                      <h4>Non-Blocking UI Scans</h4>
                    </div>
                    <p className="text-xs text-slate-600 dark:text-zinc-400 leading-relaxed">
                      All-pairs method comparison runs at an <strong>O(n²)</strong> time complexity. To prevent UI freezing, <code className="font-mono text-slate-800 dark:text-zinc-200">MainFrame</code> offloads scanning tasks to background Java <code className="font-mono">SwingWorker</code> threads.
                    </p>
                  </div>
                </div>
              </div>
            </section>
          )}

          {/* Section: Usage */}
          {activeSection === "usage" && (
            <section className="space-y-6">
              <div className="border-b border-slate-200 dark:border-zinc-800 pb-4 transition-colors">
                <h1 className="text-3xl font-extrabold text-slate-900 dark:text-zinc-100 tracking-tight">
                  GUI & Headless Mode Usage
                </h1>
                <p className="text-sm text-slate-600 dark:text-zinc-400 mt-1">
                  Run interactive scans in the FlatLaf desktop dashboard or integrate headless checks into CI/CD.
                </p>
              </div>

              <div className="space-y-4">
                <h3 className="text-base font-semibold text-slate-900 dark:text-zinc-100">
                  1. Launching the Desktop Dashboard
                </h3>
                <p className="text-sm text-slate-700 dark:text-zinc-300">
                  Open your terminal and run the global command without arguments to open the desktop GUI:
                </p>
                <div className="relative rounded-xl border border-slate-300 dark:border-zinc-800 bg-slate-100 dark:bg-zinc-900 text-slate-900 dark:text-slate-100 p-4 font-mono text-sm shadow-sm flex items-center justify-between transition-colors">
                  <span className="font-semibold">sdd</span>
                  <button
                    onClick={() => copyToClipboard("sdd", "gui")}
                    className="p-2 rounded-lg border border-slate-300 dark:border-zinc-700 bg-white dark:bg-zinc-800 hover:bg-slate-200 dark:hover:bg-zinc-700 text-slate-700 dark:text-slate-300 transition-colors cursor-pointer shadow-sm"
                    aria-label="Copy Command"
                  >
                    {copiedCmd === "gui" ? <Check size={16} className="text-emerald-600 dark:text-emerald-400" /> : <Copy size={16} />}
                  </button>
                </div>
              </div>

              <div className="space-y-4 pt-4">
                <h3 className="text-base font-semibold text-slate-900 dark:text-zinc-100">
                  2. Headless CLI Scanning
                </h3>
                <p className="text-sm text-slate-700 dark:text-zinc-300">
                  To scan a project directory silently from your terminal and output matched pairs:
                </p>
                <div className="relative rounded-xl border border-slate-300 dark:border-zinc-800 bg-slate-100 dark:bg-zinc-900 text-slate-900 dark:text-slate-100 p-4 font-mono text-sm shadow-sm flex items-center justify-between transition-colors">
                  <span className="font-semibold">sdd --path ./path/to/project --threshold 0.80</span>
                  <button
                    onClick={() => copyToClipboard("sdd --path ./path/to/project --threshold 0.80", "cli")}
                    className="p-2 rounded-lg border border-slate-300 dark:border-zinc-700 bg-white dark:bg-zinc-800 hover:bg-slate-200 dark:hover:bg-zinc-700 text-slate-700 dark:text-slate-300 transition-colors cursor-pointer shadow-sm"
                    aria-label="Copy Command"
                  >
                    {copiedCmd === "cli" ? <Check size={16} className="text-emerald-600 dark:text-emerald-400" /> : <Copy size={16} />}
                  </button>
                </div>
              </div>
            </section>
          )}

        </div>
      </div>
    </div>
  );
}