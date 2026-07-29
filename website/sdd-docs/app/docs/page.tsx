import CodeBlock from "@/components/CodeBlock";
import { ExternalLink } from "lucide-react";

export default function DocsPage() {
  return (
    <div className="container mx-auto px-6 py-8 flex flex-col md:flex-row gap-12">
      
      {/* Sticky Sidebar */}
      <aside className="w-full md:w-64 flex-shrink-0">
        <div className="sticky top-24">
          <h4 className="text-xs font-semibold text-gray-500 uppercase tracking-wider mb-4">On this page</h4>
          <ul className="space-y-3 text-sm text-gray-400">
            <li><a href="#introduction" className="hover:text-blue-400 transition-colors">Introduction</a></li>
            <li><a href="#installation" className="hover:text-blue-400 transition-colors">Installation</a></li>
            <li><a href="#cli-usage" className="hover:text-blue-400 transition-colors">CLI Usage</a></li>
            <li><a href="#gui-usage" className="hover:text-blue-400 transition-colors">GUI Usage</a></li>
            <li><a href="#how-it-works" className="hover:text-blue-400 transition-colors">How it Works</a></li>
          </ul>
          
          <div className="mt-8 pt-6 border-t border-gray-800">
            <a 
              href="https://github.com/fetehadin/smart-duplicate-detector"
              target="_blank"
              rel="noreferrer"
              className="flex items-center gap-2 text-sm text-blue-400 hover:text-blue-300 font-medium"
            >
              Contribute on GitHub <ExternalLink size={14} />
            </a>
          </div>
        </div>
      </aside>

      {/* Main Content Area */}
      <article className="flex-1 max-w-3xl text-gray-300 leading-relaxed space-y-12 pb-24">
        
        <section id="introduction" className="scroll-mt-24">
          <h1 className="text-4xl font-extrabold text-white mb-6">Documentation</h1>
          <p className="mb-4">
            Traditional duplicate detectors often rely on raw string matching or tokenization. This leads to a massive amount of "false positive" noise—flagging standard boilerplate, imports, or basic getters and setters as duplicated code.
          </p>
          <p>
            <strong>Smart Duplicate Detector (SDD)</strong> solves this using a two-step semantic approach, relying on AST parsing to find <em>real</em> logical clones.
          </p>
        </section>

        <section id="installation" className="scroll-mt-24">
          <h2 className="text-2xl font-bold text-white mb-4 border-b border-gray-800 pb-2">Installation</h2>
          <h3 className="text-lg font-semibold text-white mt-6 mb-2">Prerequisites</h3>
          <ul className="list-disc list-inside mb-6 space-y-1 text-gray-400">
            <li>Java 17+</li>
            <li>Node.js & NPM</li>
          </ul>
          <p className="mb-4">The easiest way to use the tool is directly from the official NPM registry as a global package:</p>
          <div className="mb-4">
            <CodeBlock code="npm install -g smart-duplicate-detector" isTerminal={true} />
          </div>
          <div className="bg-blue-950/30 border border-blue-900/50 p-4 rounded-lg text-sm text-blue-200">
            <strong>Fallback:</strong> If you don't want to install it globally, you can run it on-demand using <code>npx smart-duplicate-detector</code>.
          </div>
        </section>

        <section id="cli-usage" className="scroll-mt-24">
          <h2 className="text-2xl font-bold text-white mb-4 border-b border-gray-800 pb-2">CLI Usage</h2>
          <p className="mb-4">To run a silent scan in your terminal without opening the GUI, pass the target directory and similarity threshold:</p>
          <CodeBlock code="sdd --path ./my-project --threshold 0.85" isTerminal={true} />
        </section>

        <section id="gui-usage" className="scroll-mt-24">
          <h2 className="text-2xl font-bold text-white mb-4 border-b border-gray-800 pb-2">GUI Usage</h2>
          <p className="mb-4">
            To launch the modern FlatLaf desktop dashboard, open your terminal anywhere on your machine and simply type:
          </p>
          <CodeBlock code="sdd" isTerminal={true} />
          <p className="mt-4 text-sm text-gray-400">
            The Java Swing UI will automatically render a dark-themed dashboard where you can browse file trees, view highlighted logic clones, and adjust similarity thresholds visually.
          </p>
        </section>

        <section id="how-it-works" className="scroll-mt-24">
          <h2 className="text-2xl font-bold text-white mb-4 border-b border-gray-800 pb-2">How it Works</h2>
          <div className="space-y-6">
            <div>
              <h3 className="text-white font-semibold mb-2">1. Abstract Syntax Tree (AST) Extraction</h3>
              <p className="text-gray-400 text-sm">By leveraging JavaParser, the engine parses the codebase into an AST and isolates functional methods. It completely ignores class-level boilerplate, fields, and imports.</p>
            </div>
            <div>
              <h3 className="text-white font-semibold mb-2">2. Semantic Comparison</h3>
              <p className="text-gray-400 text-sm">Instead of basic string comparison, SDD compares the logic and structure of the methods using a custom Weighted Levenshtein distance algorithm. This accurately calculates similarity percentages, ensuring that only genuine logic clones are flagged.</p>
            </div>
          </div>
        </section>
        
      </article>
    </div>
  );
}