Hardware
========

# Instructions

Division operations are expensive (up to 92 cycles on 64bit x86) [1](https://youtu.be/1DuMvpwWHH4?t=1334)

## Instructions Retired

https://software.intel.com/en-us/forums/intel-vtune-amplifier-xe/topic/311170
Ignores branch misspredictions, when stalled you are not retiring instructions, aim to maximize when reducing cache misses

# Cache

* https://lwn.net/Articles/252125/
* Cache lines

## Misses

L1 (Instruction, Data), L2, L3

## Prefetching

Prefetch = Eagerly load of data
Adjacent cache lines
Prefetching works best when data is alligned sequential and access patterns are predictable/sequential
When pre-fetching works and when it does not: https://t.co/SBzIKrD3wS
https://mechanical-sympathy.blogspot.co.at/2012/08/memory-access-patterns-are-important.html

### Predictable Access Patterns

 * Temporal Locality: Refering to same data within a short time span
 * Spacial Locality: Refering to data that is close together (cohesion)
 * Sequential Locality: Refering to data which is alligned linearly in memory (array)

# Tooling

perf, likwid

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