"use client";

import Link from "next/link";
import CodeBlock from "@/components/CodeBlock";
import { FaGithub } from "react-icons/fa";
import { useTheme } from "next-themes";
import { 
  ArrowRight, FileCode, CheckCircle, Terminal, 
  FolderOpen, Sliders, AlertTriangle, 
  Sparkles, Zap, Code2, GitBranch 
} from "lucide-react";

export default function LandingPage() {
  const { theme } = useTheme();

  const steps = [
    {
      title: "1. Target Project Selection",
      description: "Browse and select any local Java project directory using the native FlatDarkLaf file selector dialog to point the analyzer directly to your source code.",
      icon: FolderOpen,
      img: "/targetProject.png",
      alt: "Target Project Selection Screenshot",
    },
    {
      title: "2. Configure Threshold & Run",
      description: "Fine-tune the similarity slider (e.g., set to 80%) to control sensitivity, then execute the AST extraction engine with a single click.",
      icon: Sliders,
      img: "/configure.png",
      alt: "Configure Threshold & Run Screenshot",
    },
    {
      title: "3. Real-Time Structural Analysis",
      description: "View immediate analysis output highlighting method signatures, similarity match percentages, and pinpointed structural duplicates.",
      icon: AlertTriangle,
      img: "/analysis.png",
      alt: "Real-Time Structural Analysis Screenshot",
    },
  ];

  const roadmap = [
    {
      icon: Code2,
      title: "VS Code Extension Integration",
      description: "Bringing real-time duplicate method detection and inline diff highlighting directly inside VS Code as you write code.",
    },
    {
      icon: Zap,
      title: "Optimized Time Complexity",
      description: "Implementing parallel AST processing and indexing to drastically minimize scan duration on large enterprise codebases.",
    },
    {
      icon: GitBranch,
      title: "CI/CD GitHub Action Support",
      description: "Automated PR blocking when duplicate code ratio exceeds project threshold directly in your pull requests.",
    },
  ];

  return (
    <div className={`min-h-screen transition-colors duration-300 flex flex-col items-center ${
      theme === "dark" ? "bg-zinc-950 text-zinc-100" : "bg-slate-50 text-slate-900"
    }`}>
      {/* Hero Section */}
      <section className="w-full max-w-5xl mx-auto px-6 pt-16 pb-12 text-center">
        <h1 className="text-4xl md:text-5xl font-bold tracking-tight mb-6">
          Catch Code Duplication <br className="hidden md:block" /> Before It Scales.
        </h1>
        <p className={`text-lg mb-8 max-w-2xl mx-auto leading-relaxed ${
          theme === "dark" ? "text-zinc-400" : "text-slate-600"
        }`}>
          A smart, AST-powered static analysis tool that finds duplicated method logic in Java codebases. Eliminate false positives and technical debt.
        </p>
        
        <div className="max-w-xl mx-auto mb-8 text-left">
          <CodeBlock code="npm install -g smart-duplicate-detector" isTerminal={true} />
        </div>

        <div className="flex flex-col sm:flex-row items-center justify-center gap-4">
          <Link href="/docs" className="flex items-center gap-2 bg-sky-600 hover:bg-sky-500 text-white px-6 py-2.5 rounded-md font-medium transition-colors w-full sm:w-auto justify-center shadow-sm">
            Read the Docs
            <ArrowRight size={18} />
          </Link>
          <a 
            href="https://github.com/fetehadin/smart-duplicate-detector" 
            target="_blank" 
            rel="noreferrer" 
            className={`flex items-center gap-2 px-6 py-2.5 rounded-md font-medium transition-colors w-full sm:w-auto justify-center border shadow-sm ${
              theme === "dark" 
                ? "bg-zinc-900 hover:bg-zinc-800 text-zinc-100 border-zinc-800" 
                : "bg-white hover:bg-slate-100 text-slate-800 border-slate-200"
            }`}
          >
            <FaGithub size={18} />
            View on GitHub
          </a>
        </div>
      </section>

      {/* Traditional vs SDD Problem Statement */}
      <section className={`w-full max-w-5xl mx-auto px-6 py-12 border-t ${
        theme === "dark" ? "border-zinc-800/80" : "border-slate-200"
      }`}>
        <div className={`p-8 rounded-2xl border ${
          theme === "dark" ? "bg-zinc-900/50 border-zinc-800" : "bg-white border-slate-200 shadow-sm"
        }`}>
          <div className="flex items-center gap-2 text-sky-500 font-semibold text-sm mb-2">
            <Sparkles size={18} />
            <span>Why Smart Duplicate Detector?</span>
          </div>
          <h2 className="text-2xl font-bold mb-4">Semantic AST Logic vs. Raw String Matching</h2>
          <div className="grid md:grid-cols-2 gap-6 text-sm leading-relaxed">
            <div className={`p-4 rounded-lg border ${
              theme === "dark" ? "bg-red-950/20 border-red-900/40 text-zinc-300" : "bg-red-50 border-red-200 text-red-900"
            }`}>
              <h3 className="font-semibold mb-2 text-base">The Problem with Traditional Detectors</h3>
              <p>
                Traditional duplicate detectors often rely on raw string matching or line tokenization. This leads to a massive amount of <strong>&quot;false positive&quot; noise</strong>—flagging standard boilerplate, imports, or basic getters and setters as duplicated code.
              </p>
            </div>
            <div className={`p-4 rounded-lg border ${
              theme === "dark" ? "bg-emerald-950/20 border-emerald-900/40 text-zinc-300" : "bg-emerald-50 border-emerald-200 text-emerald-900"
            }`}>
              <h3 className="font-semibold mb-2 text-base">The SDD Two-Step Approach</h3>
              <p>
                <strong>Smart Duplicate Detector (SDD)</strong> solves this using a two-step semantic approach. By relying on Abstract Syntax Tree (AST) parsing, SDD isolates method logic to find real structural clones while ignoring variable renames and boilerplate.
              </p>
            </div>
          </div>
        </div>
      </section>

      {/* Tech Stack Grid */}
      <section className={`w-full max-w-6xl mx-auto px-6 py-16 border-t ${
        theme === "dark" ? "border-zinc-800/80" : "border-slate-200"
      }`}>
        <div className="text-center mb-12">
          <h2 className="text-3xl font-bold mb-3">Engineered with Modern Tech</h2>
          <p className={`max-w-xl mx-auto text-sm ${
            theme === "dark" ? "text-zinc-400" : "text-slate-600"
          }`}>
            Powered by robust static analysis libraries and a native desktop interface wrapper.
          </p>
        </div>

        <div className="grid md:grid-cols-3 gap-6">
          <div className={`p-6 rounded-xl border transition-colors shadow-sm ${
            theme === "dark" ? "bg-zinc-900/50 border-zinc-800" : "bg-white border-slate-200"
          }`}>
            <div className="mb-4">
              <FileCode className="text-sky-500" size={28} />
            </div>
            <h3 className="text-lg font-semibold mb-2">Semantic AST Parsing</h3>
            <p className={`leading-relaxed text-sm mb-4 ${
              theme === "dark" ? "text-zinc-400" : "text-slate-600"
            }`}>
              Uses <strong>JavaParser</strong> and <strong>Java 17</strong> to completely ignore boilerplate, imports, and field declarations, comparing only structural logic.
            </p>
            <span className={`inline-block text-xs font-mono px-2.5 py-1 rounded border ${
              theme === "dark" ? "bg-zinc-950 text-sky-400 border-zinc-800" : "bg-slate-100 text-sky-700 border-slate-200"
            }`}>Tech: Java 17 + JavaParser</span>
          </div>

          <div className={`p-6 rounded-xl border transition-colors shadow-sm ${
            theme === "dark" ? "bg-zinc-900/50 border-zinc-800" : "bg-white border-slate-200"
          }`}>
            <div className="mb-4">
              <CheckCircle className="text-sky-500" size={28} />
            </div>
            <h3 className="text-lg font-semibold mb-2">Smart Scoring</h3>
            <p className={`leading-relaxed text-sm mb-4 ${
              theme === "dark" ? "text-zinc-400" : "text-slate-600"
            }`}>
              Utilizes a sophisticated <strong>Weighted Levenshtein distance algorithm</strong> via Maven backend to calculate accurate similarity percentages.
            </p>
            <span className={`inline-block text-xs font-mono px-2.5 py-1 rounded border ${
              theme === "dark" ? "bg-zinc-950 text-sky-400 border-zinc-800" : "bg-slate-100 text-sky-700 border-slate-200"
            }`}>Tech: Maven + Levenshtein Engine</span>
          </div>

          <div className={`p-6 rounded-xl border transition-colors shadow-sm ${
            theme === "dark" ? "bg-zinc-900/50 border-zinc-800" : "bg-white border-slate-200"
          }`}>
            <div className="mb-4">
              <Terminal className="text-sky-500" size={28} />
            </div>
            <h3 className="text-lg font-semibold mb-2">CLI & GUI Options</h3>
            <p className={`leading-relaxed text-sm mb-4 ${
              theme === "dark" ? "text-zinc-400" : "text-slate-600"
            }`}>
              Run headlessly via CLI or launch the sleek <strong>FlatLaf (FlatDarkLaf)</strong> desktop Java Swing dashboard wrapped in an NPM global CLI.
            </p>
            <span className={`inline-block text-xs font-mono px-2.5 py-1 rounded border ${
              theme === "dark" ? "bg-zinc-950 text-sky-400 border-zinc-800" : "bg-slate-100 text-sky-700 border-slate-200"
            }`}>Tech: Node.js/NPM + FlatLaf Swing</span>
          </div>
        </div>
      </section>

      {/* See It in Action */}
      <section className={`w-full max-w-4xl mx-auto px-6 py-16 border-t ${
        theme === "dark" ? "border-zinc-800/80" : "border-slate-200"
      }`}>
        <div className="text-center mb-12">
          <h2 className="text-3xl font-bold mb-3">See It in Action</h2>
          <p className={`max-w-xl mx-auto text-sm ${
            theme === "dark" ? "text-zinc-400" : "text-slate-600"
          }`}>
            Follow the step-by-step process below to see how the Java Swing FlatLaf desktop interface handles target selection, scanning, and analysis.
          </p>
        </div>

        <div className="flex flex-col gap-12">
          {steps.map((step, idx) => {
            const Icon = step.icon;
            return (
              <div key={idx} className={`p-6 md:p-8 rounded-xl border shadow-lg ${
                theme === "dark" ? "bg-zinc-900/60 border-zinc-800" : "bg-white border-slate-200"
              }`}>
                <div className="mb-6">
                  <div className="flex items-center gap-3 mb-2">
                    <Icon className="text-sky-500" size={24} />
                    <h3 className="text-xl font-semibold">{step.title}</h3>
                  </div>
                  <p className={`text-sm leading-relaxed ${
                    theme === "dark" ? "text-zinc-400" : "text-slate-600"
                  }`}>
                    {step.description}
                  </p>
                </div>
                
                <div className={`w-full rounded-lg overflow-hidden border p-2 shadow-inner flex justify-center ${
                  theme === "dark" ? "border-zinc-700 bg-zinc-950" : "border-slate-300 bg-slate-100"
                }`}>
                  <img 
                    src={step.img} 
                    alt={step.alt} 
                    className="w-full max-h-[500px] object-contain rounded"
                  />
                </div>
              </div>
            );
          })}
        </div>
      </section>

      {/* Future Roadmap Section */}
      <section className={`w-full max-w-6xl mx-auto px-6 py-16 border-t ${
        theme === "dark" ? "border-zinc-800/80" : "border-slate-200"
      }`}>
        <div className="text-center mb-12">
          <h2 className="text-3xl font-bold mb-3">Future Roadmap</h2>
          <p className={`max-w-xl mx-auto text-sm ${
            theme === "dark" ? "text-zinc-400" : "text-slate-600"
          }`}>
            What is coming next to make Smart Duplicate Detector even faster and more accessible.
          </p>
        </div>

        <div className="grid md:grid-cols-3 gap-6">
          {roadmap.map((item, idx) => {
            const Icon = item.icon;
            return (
              <div key={idx} className={`p-6 rounded-xl border shadow-sm ${
                theme === "dark" ? "bg-zinc-900/50 border-zinc-800" : "bg-white border-slate-200"
              }`}>
                <div className="p-3 w-fit rounded-lg bg-sky-500/10 text-sky-500 mb-4">
                  <Icon size={24} />
                </div>
                <h3 className="text-lg font-semibold mb-2">{item.title}</h3>
                <p className={`text-sm leading-relaxed ${
                  theme === "dark" ? "text-zinc-400" : "text-slate-600"
                }`}>
                  {item.description}
                </p>
              </div>
            );
          })}
        </div>
      </section>

      
    </div>
  );
}