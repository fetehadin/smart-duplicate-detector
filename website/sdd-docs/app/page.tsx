import Link from "next/link";
import CodeBlock from "@/components/CodeBlock";
import { ArrowRight, Mail, FileCode, CheckCircle, Terminal } from "lucide-react";
import { FaGithub, FaLinkedin } from "react-icons/fa";

export default function LandingPage() {
  return (
    <div className="flex flex-col items-center">
      {/* Hero Section */}
      <section className="w-full max-w-5xl mx-auto px-6 pt-24 pb-16 text-center">
        <h1 className="text-4xl md:text-5xl font-bold tracking-tight mb-6 text-zinc-100">
          Catch Code Duplication <br className="hidden md:block" /> Before It Scales.
        </h1>
        <p className="text-lg text-zinc-400 mb-10 max-w-2xl mx-auto leading-relaxed">
          A smart, AST-powered static analysis tool that finds duplicated method logic in Java codebases. Eliminate false positives and technical debt.
        </p>
        
        <div className="max-w-xl mx-auto mb-10 text-left">
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

      {/* Features Grid */}
      <section className="w-full max-w-6xl mx-auto px-6 py-16">
        <div className="grid md:grid-cols-3 gap-6">
          <div className="bg-zinc-800/50 p-6 rounded-lg border border-zinc-700/50 hover:border-zinc-600 transition-colors shadow-sm">
            <div className="mb-4">
              <FileCode className="text-sky-400" size={28} />
            </div>
            <h3 className="text-lg font-semibold mb-2 text-zinc-100">Semantic AST Parsing</h3>
            <p className="text-zinc-400 leading-relaxed text-sm">Uses JavaParser to completely ignore boilerplate, imports, and field declarations, comparing only the actual structural logic of your methods.</p>
          </div>
          <div className="bg-zinc-800/50 p-6 rounded-lg border border-zinc-700/50 hover:border-zinc-600 transition-colors shadow-sm">
            <div className="mb-4">
              <CheckCircle className="text-sky-400" size={28} />
            </div>
            <h3 className="text-lg font-semibold mb-2 text-zinc-100">Smart Scoring</h3>
            <p className="text-zinc-400 leading-relaxed text-sm">Utilizes a sophisticated Weighted Levenshtein distance algorithm to accurately calculate similarity percentages, preventing false positives.</p>
          </div>
          <div className="bg-zinc-800/50 p-6 rounded-lg border border-zinc-700/50 hover:border-zinc-600 transition-colors shadow-sm">
            <div className="mb-4">
              <Terminal className="text-sky-400" size={28} />
            </div>
            <h3 className="text-lg font-semibold mb-2 text-zinc-100">CLI & GUI Options</h3>
            <p className="text-zinc-400 leading-relaxed text-sm">Run headlessly in your CI/CD pipeline via `sdd --path .` or instantly launch the sleek FlatLaf desktop dashboard for visual analysis.</p>
          </div>
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
          <a href="#" className="p-2.5 bg-zinc-800 border border-zinc-700 rounded-md hover:bg-zinc-700 transition-all text-zinc-400 hover:text-[#0A66C2] shadow-sm" title="LinkedIn">
            <FaLinkedin size={20} />
          </a>
          <a href="#" className="p-2.5 bg-zinc-800 border border-zinc-700 rounded-md hover:bg-zinc-700 transition-all text-zinc-400 hover:text-sky-400 shadow-sm" title="Email">
            <Mail size={20} />
          </a>
        </div>
      </section>
    </div>
  );
}