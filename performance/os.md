Operation System Performance
============================

 * Kernel threads
 * Scheduling
   + http://www.javamex.com/tutorials/threads/thread_scheduling_java.shtml
 * Thread pinning
 * Time sync
 * Sockets, Filedescriptors
 * System calls, user mode, kernel/priviledged mode
 * Context Switch http://www.javamex.com/tutorials/threads/context_switch.shtml
   + Benchmarks
     + http://blog.tsunanet.net/2010/11/how-long-does-it-take-to-make-context.html
     + http://lmbench.sourceforge.net/cgi-bin/man?keyword=lmbench&section=8
   + Thread
   + Process
   + Virtual OS (Hypervisor)
 * Jitter
   + http://epickrram.blogspot.co.at/2015/09/reducing-system-jitter.html, http://epickrram.blogspot.co.at/2015/11/reducing-system-jitter-part-2.html
   + https://www.researchgate.net/publication/258406264_Exploratory_Study_on_the_Linux_OS_Jitter
   + http://highscalability.com/blog/2015/5/27/a-toolkit-to-measure-basic-system-performance-and-os-jitter.html
   + http://highscalability.com/blog/2015/4/8/the-black-magic-of-systematically-reducing-linux-os-jitter.html
   + https://www.kernel.org/doc/Documentation/kernel-per-CPU-kthreads.txt
 * Even async file IO sys calls can sometimes block