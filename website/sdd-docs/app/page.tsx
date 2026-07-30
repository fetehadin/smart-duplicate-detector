"use client";

import Link from "next/link";
import CodeBlock from "@/components/CodeBlock";
import { ArrowRight, Mail, FileCode, CheckCircle, Terminal, FolderOpen, Sliders, AlertTriangle } from "lucide-react";
import { FaGithub, FaLinkedin } from "react-icons/fa";

export default function LandingPage() {
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

  return (
    <div className="flex flex-col items-center">
      {/* Hero Section */}
      <section className="w-full max-w-5xl mx-auto px-6 pt-20 pb-12 text-center">
        <h1 className="text-4xl md:text-5xl font-bold tracking-tight mb-6 text-zinc-100">
          Catch Code Duplication <br className="hidden md:block" /> Before It Scales.
        </h1>
        <p className="text-lg text-zinc-400 mb-8 max-w-2xl mx-auto leading-relaxed">
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
          <a href="https://github.com/fetehadin/smart-duplicate-detector" target="_blank" rel="noreferrer" className="flex items-center gap-2 bg-zinc-800 hover:bg-zinc-700 text-zinc-100 border border-zinc-700 px-6 py-2.5 rounded-md font-medium transition-colors w-full sm:w-auto justify-center shadow-sm">
            <FaGithub size={18} />
            View on GitHub
          </a>
        </div>
      </section>

      {/* Features Grid & Tech Stack Integration */}
      <section className="w-full max-w-6xl mx-auto px-6 py-16 border-t border-zinc-800/80">
        <div className="text-center mb-12">
          <h2 className="text-3xl font-bold text-zinc-100 mb-3">Engineered with Modern Tech</h2>
          <p className="text-zinc-400 max-w-xl mx-auto text-sm">
            Powered by robust static analysis libraries and a native desktop interface wrapper.
          </p>
        </div>

        <div className="grid md:grid-cols-3 gap-6 mb-12">
          <div className="bg-zinc-800/50 p-6 rounded-lg border border-zinc-700/50 hover:border-zinc-600 transition-colors shadow-sm">
            <div className="mb-4">
              <FileCode className="text-sky-400" size={28} />
            </div>
            <h3 className="text-lg font-semibold mb-2 text-zinc-100">Semantic AST Parsing</h3>
            <p className="text-zinc-400 leading-relaxed text-sm mb-4">Uses <strong>JavaParser</strong> and <strong>Java 17</strong> to completely ignore boilerplate, imports, and field declarations, comparing only structural logic.</p>
            <span className="inline-block text-xs font-mono bg-zinc-900 text-sky-400 px-2 py-1 rounded border border-zinc-700">Tech: Java 17 + JavaParser</span>
          </div>

          <div className="bg-zinc-800/50 p-6 rounded-lg border border-zinc-700/50 hover:border-zinc-600 transition-colors shadow-sm">
            <div className="mb-4">
              <CheckCircle className="text-sky-400" size={28} />
            </div>
            <h3 className="text-lg font-semibold mb-2 text-zinc-100">Smart Scoring</h3>
            <p className="text-zinc-400 leading-relaxed text-sm mb-4">Utilizes a sophisticated <strong>Weighted Levenshtein distance algorithm</strong> via Maven backend to calculate accurate similarity percentages.</p>
            <span className="inline-block text-xs font-mono bg-zinc-900 text-sky-400 px-2 py-1 rounded border border-zinc-700">Tech: Maven + Levenshtein Engine</span>
          </div>

          <div className="bg-zinc-800/50 p-6 rounded-lg border border-zinc-700/50 hover:border-zinc-600 transition-colors shadow-sm">
            <div className="mb-4">
              <Terminal className="text-sky-400" size={28} />
            </div>
            <h3 className="text-lg font-semibold mb-2 text-zinc-100">CLI & GUI Options</h3>
            <p className="text-zinc-400 leading-relaxed text-sm mb-4">Run headlessly via CLI or launch the sleek <strong>FlatLaf (FlatDarkLaf)</strong> desktop Java Swing dashboard wrapped in an NPM global CLI.</p>
            <span className="inline-block text-xs font-mono bg-zinc-900 text-sky-400 px-2 py-1 rounded border border-zinc-700">Tech: Node.js/NPM + FlatLaf Swing</span>
          </div>
        </div>
      </section>

      {/* See It in Action (Stacked Wide Cards for High Image Visibility) */}
      <section className="w-full max-w-4xl mx-auto px-6 py-16 border-t border-zinc-800/80">
        <div className="text-center mb-12">
          <h2 className="text-3xl font-bold text-zinc-100 mb-3">See It in Action</h2>
          <p className="text-zinc-400 max-w-xl mx-auto text-sm">
            Follow the step-by-step process below to see how the Java Swing FlatLaf desktop interface handles target selection, scanning, and analysis.
          </p>
        </div>

        <div className="flex flex-col gap-12">
          {steps.map((step, idx) => {
            const Icon = step.icon;
            return (
              <div key={idx} className="bg-zinc-900/60 p-6 md:p-8 rounded-xl border border-zinc-800 shadow-lg">
                <div className="mb-6">
                  <div className="flex items-center gap-3 mb-2">
                    <Icon className="text-sky-400" size={24} />
                    <h3 className="text-xl font-semibold text-zinc-100">{step.title}</h3>
                  </div>
                  <p className="text-sm text-zinc-400 leading-relaxed">
                    {step.description}
                  </p>
                </div>
                
                {/* Full-width, high-visibility embedded screenshot */}
                <div className="w-full rounded-lg overflow-hidden border border-zinc-700 bg-zinc-950 p-2 shadow-inner flex justify-center">
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

      {/* Meet the Developer */}
      <section className="w-full max-w-4xl mx-auto px-6 py-16 mt-8 border-t border-zinc-800 text-center">
        <h2 className="text-2xl font-bold text-zinc-100 mb-4">Meet the Developer</h2>
        <p className="text-zinc-400 mb-8 max-w-lg mx-auto text-sm">
          Built by <strong className="text-zinc-200">Fetehadin Negash</strong>. Passionate about engineering smart, reliable tools that make developers' lives easier.
        </p>
        <div className="flex items-center justify-center gap-4">
          <a href="https://github.com/fetehadin" target="_blank" rel="noreferrer" className="p-2.5 bg-zinc-800 border border-zinc-700 rounded-md hover:bg-zinc-700 transition-all text-zinc-400 hover:text-zinc-100 shadow-sm" title="GitHub">
            <FaGithub size={20} />
          </a>
          <a href="https://www.linkedin.com/in/fetehadin/" target="_blank" rel="noreferrer" className="p-2.5 bg-zinc-800 border border-zinc-700 rounded-md hover:bg-zinc-700 transition-all text-zinc-400 hover:text-[#0A66C2] shadow-sm" title="LinkedIn">
            <FaLinkedin size={20} />
          </a>
          <a href="mailto:fetehadinnegash@gmail.com" className="p-2.5 bg-zinc-800 border border-zinc-700 rounded-md hover:bg-zinc-700 transition-all text-zinc-400 hover:text-sky-400 shadow-sm" title="Email">
            <Mail size={20} />
          </a>
        </div>
      </section>
    </div>
  );
}