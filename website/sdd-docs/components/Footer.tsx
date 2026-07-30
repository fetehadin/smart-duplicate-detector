import { FaGithub, FaLinkedin } from "react-icons/fa";
import { Mail } from "lucide-react";

export default function Footer() {
  return (
    <footer className="w-full border-t border-slate-200 dark:border-zinc-800/80 bg-slate-50 dark:bg-zinc-950 text-slate-600 dark:text-zinc-400 py-12 text-center text-sm transition-colors duration-300">
      <div className="w-full max-w-4xl mx-auto px-6 flex flex-col items-center">
        <h2 className="text-2xl font-bold mb-3 text-slate-900 dark:text-zinc-100">
          Meet the Developer
        </h2>
        
        <p className="mb-6 max-w-lg mx-auto text-sm leading-relaxed text-slate-600 dark:text-zinc-400">
          Built by <strong className="text-slate-900 dark:text-zinc-200">Fetehadin Negash</strong>. Passionate about engineering smart, reliable tools that make developers&apos; lives easier.
        </p>

        {/* Social Links */}
        <div className="flex items-center justify-center gap-4 mb-8">
          <a 
            href="https://github.com/fetehadin" 
            target="_blank" 
            rel="noreferrer" 
            className="p-2.5 rounded-md border border-slate-200 dark:border-zinc-800 bg-white dark:bg-zinc-900 text-slate-600 dark:text-zinc-400 hover:text-slate-900 dark:hover:text-zinc-100 hover:bg-slate-100 dark:hover:bg-zinc-800 transition-all shadow-sm" 
            title="GitHub"
          >
            <FaGithub size={20} />
          </a>
          <a 
            href="https://www.linkedin.com/in/fetehadin/" 
            target="_blank" 
            rel="noreferrer" 
            className="p-2.5 rounded-md border border-slate-200 dark:border-zinc-800 bg-white dark:bg-zinc-900 text-slate-600 dark:text-zinc-400 hover:text-[#0A66C2] dark:hover:text-[#0A66C2] hover:bg-slate-100 dark:hover:bg-zinc-800 transition-all shadow-sm" 
            title="LinkedIn"
          >
            <FaLinkedin size={20} />
          </a>
          <a 
            href="mailto:fetehadinnegash@gmail.com" 
            className="p-2.5 rounded-md border border-slate-200 dark:border-zinc-800 bg-white dark:bg-zinc-900 text-slate-600 dark:text-zinc-400 hover:text-sky-600 dark:hover:text-sky-400 hover:bg-slate-100 dark:hover:bg-zinc-800 transition-all shadow-sm" 
            title="Email"
          >
            <Mail size={20} />
          </a>
        </div>

        <p className="text-xs text-slate-500 dark:text-zinc-500">
          Smart Duplicate Detector &bull; Released under the MIT License
        </p>
      </div>
    </footer>
  );
}