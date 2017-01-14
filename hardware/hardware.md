Hardware
========

# Instructions

Division operations are expensive (up to 92 cycles on 64bit x86) [1](https://youtu.be/1DuMvpwWHH4?t=1334)

## Instructions Retired

https://software.intel.com/en-us/forums/intel-vtune-amplifier-xe/topic/311170
Ignores branch misspredictions, when stalled you are not retiring instructions, aim to maximize when reducing cache misses

## Stack

 * [Understanding the Stack](https://www.cs.umd.edu/class/sum2003/cmsc311/Notes/Mips/stack.html)

# Scheduling

## Context Switching

 * Direct Cost
   + Save old process state
   + Schedule new process
   + Load new process state
 * Indirect cost
   + TLB reload (new process)
   + CPU Pipeline flush
   + Cache Interference (pollution)
   + Temporal Locality
 * [Quantifying The Cost of Context Switch](http://www.cs.rochester.edu/u/cli/research/switch.pdf) numbers outdated but principle still applies
   + Direct cost nearly constant time 3 micros
   + Indirect cost up to 1500 micros
   + Indirect cost dominate direct cost if working set > L2 cache
 * Benchmarks
   + http://blog.tsunanet.net/2010/11/how-long-does-it-take-to-make-context.html
   + http://lmbench.sourceforge.net/cgi-bin/man?keyword=lmbench&section=8

# Memory

See: [Caching In: Understand, Measure, and Use Your CPU Cache More Effectively](https://www.youtube.com/watch?v=EAUlxpdj3fY&index=4&list=WL)

 * TLAB (page table cache)
   + multi level (TLB for L1, L2, L3)
   + measurable via CPU perf counters (miss/hit rate, walk duration - how long lookup takes)
   + on older hardware flushed on process context switch
   + on newer hardware ASI (Address Space Identifier) is added to not flush all TLBs
 * Page Table (virtual to physical memory address)
   + OS managed
   + page fault when address not in memory (need to bring in from disk)
 * Page Size
   + tradeoff when using big pages: less pages + quick lookups vs wasted memory space + more disk paging
   + page size is adjustable (often requires reboot when changed)
     + JVM flag: ```-XX:+UseLargePages```
     + linux: ```cat /proc/meminfo```
 * http://minnie.tuhs.org/CompArch/Lectures/week06.html
 * http://landley.net/writing/memory-faq.txt
 
**Readings**

   + [Hitting the Memory Wall](http://www.eecs.ucf.edu/~lboloni/Teaching/EEL5708_2006/slides/wulf94.pdf)
   + [What Every Programmer Should Know About Memory](http://david.jobet.free.fr/wiclear-blog/images/cpumemory.pdf)

# Cache

* L1 (Instruction, Data), L2, L3 (https://www.scss.tcd.ie/Jeremy.Jones/CS3021/5%20caches.pdf)
* Cache lines 64 bytes
* https://lwn.net/Articles/252125/
* http://igoro.com/archive/gallery-of-processor-cache-effects/
* Old but concepts still apply: http://arstechnica.com/gadgets/2002/07/caching/2/
* https://mechanical-sympathy.blogspot.co.at/2013/02/cpu-cache-flushing-fallacy.html
* Store Buffers are used as the first optimization [1](https://www.youtube.com/watch?v=OFgxAFdxYAQ) for reducing write back latency
* Store Forwarding causes the loads to return the value in the store buffer, if present [1](http://gvsmirnov.ru/blog/tech/2014/02/10/jmm-under-the-hood.html#store-buffers)

## Coherency

 * https://www.cs.auckland.ac.nz/~jmor159/363/html/cache_coh.html
 * http://gvsmirnov.ru/blog/tech/2014/02/10/jmm-under-the-hood.html
 * https://www.burnison.ca/articles/perceiving-the-effects-of-cache-coherency-in-spin-locks
 * [Memory Barriers: a Hardware View for Software Hackers](http://www.rdrop.com/users/paulmck/scalability/paper/whymb.2010.07.23a.pdf)

## Misses

 * 3 kinds of misses [1](http://arstechnica.com/gadgets/2002/07/caching/2/)
   + Compulsory miss: Desired data was never in the cache and therefor must be paged in

## Prefetching

Prefetch = Eagerly load of data
Adjacent cache lines
Prefetching works best when data is alligned sequential and access patterns are predictable/sequential
When pre-fetching works and when it does not: https://t.co/SBzIKrD3wS
https://mechanical-sympathy.blogspot.co.at/2012/08/memory-access-patterns-are-important.html

## Predictable Access Patterns

 * Temporal Locality: Refering to same data within a short time span
 * Spacial Locality: Refering to data that is close together (cohesion)
 * Sequential Locality: Refering to data which is alligned linearly in memory (array)

# Tooling

perf, likwid, [lmbench](http://lmbench.sourceforge.net/), [toplev](https://github.com/andikleen/pmu-tools/wiki/toplev-manual)

# Groups

https://groups.google.com/forum/#!forum/mechanical-sympathy

# Videos

Name | Recorded At | Speaker | Language/Platform | Rating | Description |
-----|-------------|---------|-------------------|--------|-------------|
[A Crash Course in Modern Hardware] | Devoxx | Cliff Click | HW,OS,JVM,Java | 8 | Really a crash course but still quite good |
[CPU caches and why you care] | code::dive conference 2014 | Scott Meyers | HW,C++ | 9 | Classic one about caches, must watch |
[History of Memory Models] | MIT course | ? | Theory | ? | Not complete watched yet |
[Caching in: understand, measure, and use your CPU Cache more effectively](https://youtu.be/EAUlxpdj3fY?list=WL) | JavaOne 2015 | Richard Warburton | HW | 9 | Easy intro |
[Writing Fast Code I] | Code::dive 2015 | Andrei Alexandrescu | C++/HW | 9 | Low level |
[Writing Fast Code 2] | Code::dive 2015 | Andrei Alexandrescu | C++/HW | 9 | Low level |
[Fastware] | ACCU 2016 | Andrei Alexandrescu | - | - |
[Writing Quick Code in C++, Quickly] | GoingNative 2015 | Andrei Alexandrescu | - | - |
[Optimization Tips - Mo' Hustle Mo' Problems] | CooCon 2014 |  Andrei Alexandrescu | 8 | Very low level |
[Data Oriented Design and C++] | CppCon 2014 | Mike Acton | 9 | Low level and interesting but very limited use |

[A Crash Course in Modern Hardware]: https://www.youtube.com/watch?v=OFgxAFdxYAQ
[CPU caches and why you care]: https://www.youtube.com/watch?v=WDIkqP4JbkE
[History of Memory Models]: https://www.youtube.com/watch?v=3e1ZF1L1VhY&t
[Writing Fast Code I]: https://www.youtube.com/watch?v=vrfYLlR8X8k
[Writing Fast Code 2]: https://youtu.be/9tvbz8CSI8M
[Fastware]: https://youtu.be/AxnotgLql0k
[Writing Quick Code in C++, Quickly]: https://youtu.be/ea5DiCg8HOY
[Optimization Tips - Mo' Hustle Mo' Problems]: https://youtu.be/Qq_WaiwzOtI
[Data Oriented Design and C++]: https://youtu.be/rX0ItVEVjHc