Performance
===========

**Under construction - currently mostly brainstorming**

 * [Java](java.md)
 * [OS](os.md)
 * [Memory](memory.md)
 * [Scalability](scalability.md)
 * [Monitoring](monitoring.md)
 * [Benchmarks](testing.md)
 * [Microbenchmarks](microbench.md)
 * [System](system.md)

# General

Performance is usually not composable!
Functions contain more branches, there is more risk of confusing the optimizer
Good explanation about what does (premature) optimization means: https://youtu.be/1DuMvpwWHH4?t=777
If you are serious about performance, performance tests can fail your build (CI or nightly at least).
Division operations are expensive (up to 92 cycles on 64bit x86) and therefore should not be done in microbenchmarks [1](https://youtu.be/1DuMvpwWHH4?t=1334)
Benefits of low allocation rates, higher cache utilization: https://youtu.be/vZngvuXk7PM?t=758
Average Latency * Throughput https://t.co/DR7Od7IrRb at 28:14
Big lie: "Normal" Distributions [1](https://youtu.be/teYzwaWmi-8?list=WL&t=2408)

Exceptions: This might be surprising to some people, since undoubtedly everyone has heard that “exceptions are slow.” 
It turns out that they don’t have to be. 
And, when done right, they get error handling code and data off hot paths which increases I-cache and TLB performance.

# Performance metrics

* Throughput
* Latency
* Service Time
* Response Time

# [Benchmarks](testing.md)

# Design

* [Amdahls Law](law/amdahls.md)
* Batching
   + [Batching-and-Low-Latency](https://vanilla-java.github.io/2016/07/09/Batching-and-Low-Latency.html)
   + [How-can-batching-requests-actually-reduce-latency](http://highscalability.com/blog/2013/12/4/how-can-batching-requests-actually-reduce-latency.html)
   + [Smart batching](https://mechanical-sympathy.blogspot.co.at/2011/10/smart-batching.html)
* Little's law
* Law of diminishing returns

# Typical performance numbers

* Numbers every programmer should know
  + https://gist.github.com/jboner/2841832
  + http://highscalability.com/blog/2013/1/15/more-numbers-every-awesome-programmer-must-know.html
  + https://people.eecs.berkeley.edu/~rcs/research/interactive_latency.html
* Register access
* L1 access
* L2 access
* L3 access
* RAM access
* Sequential SSD
* Sequential HDD
* Network access
* Mutex lock/unlock
* CPU pipeline stall
* Roundtrip 10Gbit kernel bypass
* Contention cost
* QPI (Intel QuickPath Interconnect)

https://www.youtube.com/watch?v=vZngvuXk7PM

data passing | latency | light over a fibre | throughput |
-------------|---------|--------------------|------------|
Methodcall | inlined 0 real: 50ns | 10m | 20,000,000/sec |
Shared memory | 200ns | 40m | 5,000,000/sec |
Sysv shared memory | 2micros | 400m | 500,000/sec | 
Low latency network | 8micros | 1.6km | 125,000/sec |
Typical LAN | 30micros | 6km | 30,000/sec |
Typical data grid | 800micros | 160km | 1,250/sec |
4G request latency | 55ms | 11,000km | 18/sec |

# Methodology

https://youtu.be/Zw_z7pjis7k?list=WL&t=3214

 1. What prevents app to go faster: Monitoring
 2. Where it resides: Profiling
 3. How to change it stop messing with perf
 
# Response Time IPC

Deplyoment factors affecting response time: https://youtu.be/gsJztZkhQUQ?list=WL&t=2160

Key takeaway is: dedicated resource assignment and affinity

HW configuration:
 * multiple network cards, latency can be improved by affinitizing traffic to particular card and directing to specific processors
 * connection via inifiniBand or fiber optic instead of ethernet
 
Native OS:
 * within single OS image: take advantage of loopback traffic optimizations
 * across multiple OS images: careful about traffic routing
 
Virtual OS:
 * good to affinitize VM to cores instead of free floating
 * be careful about IO across virtual OS images
 
Process level:
 * affinitized to a socket delivers more consistent response time

 10. Reduce communication as much as possible
 9. Send data in big chunks
 8. Combine messages in batches
 7. If you care about througput, use an async model
 6. If you care about consistency, you have to use sync commit protocols
 5. Re-use resources (connection channels, threads, etc) do not create new ones for each message
 4. Check and log errors
 3. Use frameworks when appropriate (gives consistency)
 2. Profile and tune
 1. Dont believe it when someone says "it will never happen"

# Pauses

https://youtu.be/vZngvuXk7PM?t=795

Causes for pauses
 * IO delays (seeks and writes)
 * OS interrupts (5ms is not uncommon, most of the pauses are from the OS)
 * Lock contention

## Flame Graphs

 * http://techblog.netflix.com/2015/07/java-in-flames.html
 * http://www.brendangregg.com/FlameGraphs/cpuflamegraphs.html#Java
 * http://www.slideshare.net/brendangregg/java-performance-analysis-on-linux-with-flame-graphs
 * Containerized: http://blog.alicegoldfuss.com/making-flamegraphs-with-containerized-java/
 * [My first flame graph](experiments/home-ws/flamegraph/firstflamegraph.md)

# Profilers

 1. System profilers: like Linux perf, which shows system code paths (eg, JVM GC, syscalls, TCP), but not Java methods
 2. JVM profilers: like hprof, LJP, and commercial profilers. These show Java methods, but usually not system code paths

# Hardware

* Power control
* Caching, ICache, DCache
* TLB
* QPI (Intel QuickPath Interconnect)
* Cache subsystem, L1,L2,L3,QPI, Bandwith, >90% of chip is cache

10GBit Ethernet vs QPI 20GByte? Ethernet only 16 times slower?

## Tooling

* [JMH]
* [Gatling]
* [wrk2](https://github.com/giltene/wrk2)
* [tcpkali](https://github.com/machinezone/tcpkali/blob/master/doc/tcpkali.man.md)
* [jrt-socket-response-tool](https://www.azul.com/products/open-source-tools/jrt-socket-response-tool/)
* top: CPU should be split into instructions retired and memory stalls
* http://highscalability.com/blog/2015/5/27/a-toolkit-to-measure-basic-system-performance-and-os-jitter.html
* HdrHistogram
  + http://kamon.io/core/metrics/instruments/

# People to learn from

* Gil Tene, Nitsan Wakart, Cliff Click, basically everyone from [Azul Systems](https://www.azul.com/)
* Martin Thomson
* Doug Lea
* Aleksey Shipilev
* Norman Maurer
* Todd L. Montgomery
* Jonas Boner, Viktor Klang, Konrad Malawski, people from Typesafe/Lightbend
* Andrei Alexandrescu
* Herb Sutter
* Scott Meyers
* sustrik (ZeroMQ inventor) (http://250bpm.com/)
* Brendan D. Gregg (http://www.brendangregg.com)
* Richard Warburton (http://www.insightfullogic.com/)
* https://blog.codecentric.de/

# Code to learn from

* JCTools
* Disruptor
* Fast Flow
* Aeron
* Akka

# Books, Literature, etc

Name | Author | Platform | Rating | Description |
-----|--------|----------|--------|-------------|
http://www.lighterra.com/papers/modernmicroprocessors/ | Patterson | HW | 10 | Best intro to microprocessors i know |
[Bad Concurrency] | Barker | Java/OS/HW | 9 | Michael Barkers Blog |
[Nanotrusting the Nanotime] | Shipilev | Java- | 10 | Best description of time in java and on modern hardware |
https://blogs.oracle.com/dholmes/entry/inside_the_hotspot_vm_clocks | David Holmes | Java- | 8 | - |
https://medium.com/@octskyward/modern-garbage-collection-911ef4f8bd8e#.a3ax4ucvz | Mike Hearn | Java/GO | 8 | A critique on the claims about Gos GC plus a good intro to modern GC |
Your Load Generator Is Probably Lying To You - Take The Red Pill And Find Out Why | High Scalability | - | - | Talks about Coordinated Omission |
https://lwn.net/Articles/252125/ | - | - | - | Easy to get but very long explanation about CPU caches |
[Not all operations are equal](http://ithare.com/infographics-operation-costs-in-cpu-clock-cycles/) | - | - | - | |

https://groups.google.com/forum/#!forum/mechanical-sympathy

# Videos

Name | Recorded | Speaker | Platform | Rating | Description |
-----| ---------|---------|----------|--------|-------------|
[How NOT to Measure Latency] | - | Tene (Azul) | - | 10 | Must watch! |
[The Art of Java Benchmarking] | Oredev 2013 | Shipilev | Java | 10 | Must watch! for everyone benchmarking java |
[JMH the lesser of two evils] | Devoxx 2013 | Shipilev | Java | 9 | Similar to [The Art of Java Benchmarking] |
[Performance Methodology I] | Devoxx 2012 | Shipliv & Pepperdine | Java+ | 10 | Very good |
[Performance Methodology II] | Devoxx 2012 | Shipliv & Pepperdine | Java+ | 10 | Very good |
[LMAX - How to Do 100K TPS at Less than 1ms Latency] | QCon SF 2010 | Barker & Thompson (LMAX) | Java | 9 | Classic one about the Disruptor |
[Java at the Cutting Edge: Stuff I Learned about Performance] | JVM Users Aukland | Barker (LMAX) | Java | - | Watched long ago, but think it was good |
[Benchmarking: You're Doing It Wrong] | Strangeloop 2014 | Greenberg (Google) | - | 8 | - |
[Taming the 9s] | Strangeloop 2014 | Weisberg (VoltDB) | Java/C++ | - | - |
[When to OS gets in the way] | Strangeloop 2015 | Price (LMAX) | Java/OS/HW | 9 | Explains importance of thread pinning etc |  
Mythbusting Modern Hardware to Gain 'Mechanical Sympathy' - https://www.youtube.com/watch?v=MC1EKLQ2Wmg&t=4s | Goto 2012 | Thompson | - | 8 | Clears up some common misconceptions about HW |
[JVM Profiling pitfalls] | - | Nitsan Wakart | Java | 9 | Getting deep into profiling |
[Performance Testing Java Applications] | Devoxx UK 2013 | Thompson | Java | 8 | One thing to note: Dont write your benchmark harness, use JMH! |
[A Crash Course in Modern Hardware] | Devoxx | Cliff Click | HW,OS,JVM,Java | 8 | Really a crash course but still quite good |
[CPU caches and why you care] | code::dive conference 2014 | Scott Meyers | HW,C++ | 9 | Classic one about caches, must watch |
[Deep Dive Performance] | ? | Nitsan Wakart | Java | 6 | 3 talks - some good some not so good |
[The Illusion of Execution] | JFokus 2015 | Nitsan Wakart | Java | 10 | Nice deep dive |
[CON1517 An Introduction to JVM Performance] | JavaOne 2015 | Rafael Winterhalter | Java | 9 | Very good and practical |
[Caching in: understand, measure, and use your CPU Cache more effectively](https://youtu.be/EAUlxpdj3fY?list=WL) | JavaOne 2015 | Richard Warburton | HW | 9 | Easy intro |
[Data Oriented Design] | CppCon2014 | Mike Acton | Cpp | 8 | Designing code based on its data, very low level | 
[Life of a Twitter JVM engineer] | Devoxx 2016 | Tony Printezis | JVM | 8+ | Mostly GC problems |
[Writing Fast Code I] | Code::dive 2015 | Andrei Alexandrescu | C++/HW | 9 | Low level |
[Writing Fast Code 2] | Code::dive 2015 | Andrei Alexandrescu | C++/HW | 9 | Low level |
[Optimization Tips - Mo' Hustle Mo' Problems] | CooCon 2014 |  Andrei Alexandrescu | 8 | Very low level |

[The Art of Java Benchmarking]: https://vimeo.com/78900556
[Java at the Cutting Edge: Stuff I Learned about Performance]: https://www.youtube.com/watch?v=uKoZgIdVZQ4&t=402s
[Benchmarking: You're Doing It Wrong]: https://www.youtube.com/watch?v=XmImGiVuJno&list=PLljcY9k9tmL8k8oGzKcKL2D0AeDIi-V_A
[Taming the 9s]: https://www.youtube.com/watch?v=EmiIUW4splQ&index=2&list=PLljcY9k9tmL8k8oGzKcKL2D0AeDIi-V_A
[How NOT to Measure Latency]: https://www.youtube.com/watch?v=0b3sR32m0nU
[LMAX - How to Do 100K TPS at Less than 1ms Latency]: https://www.infoq.com/presentations/LMAX
[Nanotrusting the Nanotime]: https://shipilev.net/blog/2014/nanotrusting-nanotime/
[Bad Concurrency]: http://bad-concurrency.blogspot.co.at/
[When to OS gets in the way]: https://www.youtube.com/watch?v=-6nrhSdu--s
[JVM Profiling pitfalls]: https://www.youtube.com/watch?v=pXV4pY42FtM&t=2880s
[Performance Testing Java Applications]: https://www.youtube.com/watch?v=1DuMvpwWHH4&t=1s
[A Crash Course in Modern Hardware]: https://www.youtube.com/watch?v=OFgxAFdxYAQ
[CPU caches and why you care]: https://www.youtube.com/watch?v=WDIkqP4JbkE
[Deep Dive Performance]: https://www.youtube.com/watch?v=1Uc3M9YK5Tg&t
[The Illusion of Execution]: https://www.youtube.com/watch?v=rzuLbvT5354
[CON1517 An Introduction to JVM Performance]: https://www.youtube.com/watch?v=q8-10v15sZE&t
[Data Oriented Design]: https://www.youtube.com/watch?v=rX0ItVEVjHc&t=2303s
[Life of a Twitter JVM engineer]: https://youtu.be/M9o1LVfGp2A
[JMH the lesser of two evils]: https://www.youtube.com/watch?v=VaWgOCDBxYw&t=518s
[Writing Fast Code I]: https://www.youtube.com/watch?v=vrfYLlR8X8k
[Writing Fast Code 2]: https://youtu.be/9tvbz8CSI8M
[Optimization Tips - Mo' Hustle Mo' Problems]: https://youtu.be/Qq_WaiwzOtI
[Performance Methodology I]: https://www.youtube.com/watch?v=Zw_z7pjis7k
[Performance Methodology II]: https://www.youtube.com/watch?v=eDTTxYCGsKc

[JMH]: http://openjdk.java.net/projects/code-tools/jmh/
[Gatling]: http://gatling.io/
[CO_USER_GROUP]: https://groups.google.com/forum/#!msg/mechanical-sympathy/icNZJejUHfE/BfDekfBEs_sJ
[CO_PDF]: https://www.azul.com/files/HowNotToMeasureLatency_LLSummit_NYC_12Nov2013.pdf
[invoke-interface-optimisations]: https://mechanical-sympathy.blogspot.co.at/2012/04/invoke-interface-optimisations.html