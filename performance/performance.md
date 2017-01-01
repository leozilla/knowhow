Performance
===========

**Under construction - currently mostly brainstorming**

# Performance metrics

* Throughput
* Latency
* Service Time
* Response Time

## Howto measure

* Constant rate
* Throughput at given latency
* Latency at given throughput
* [Latency for a set Throughput](https://vanilla-java.github.io/2016/07/20/Latency-for-a-set-Throughput.html)
* Pitfalls
   + Coordinated Omission: [CO_USER_GROUP], [CO_PDF]
   + [Why don't I get the throughput I benchmarked?](https://vanilla-java.github.io/2016/07/23/Why-dont-I-get-the-throughput-I-benchmarked.html)

See [Tooling](#tooling)

# Design

* Batching
   + [Batching-and-Low-Latency](https://vanilla-java.github.io/2016/07/09/Batching-and-Low-Latency.html)
   + [How-can-batching-requests-actually-reduce-latency](http://highscalability.com/blog/2013/12/4/how-can-batching-requests-actually-reduce-latency.html)
   + [Smart batching](https://mechanical-sympathy.blogspot.co.at/2011/10/smart-batching.html)

# Typical performance numbers

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

# Java

* Indirections = cache misses -> arrays rock
* Async APIs needed
* JDK NIO still too much blocking, garbage for selector key set
* JNI expensive especially from C back to Java, caching JMethodIds helps etc

# JVM

* JIT
* Object Layout, Object Header, Padding, Memory Abstraction+GC overhead
* Profiling C1,C2,C3?
* JMM
* De-Optimization
* Virtual Method Dispatch, Bimorphic, Megamorphic
* Lock coarsening
* Lock biasing
* Loop unrolling
* Escape analysis
* Constant propagation
* Dead code elimination
* Tail call elimination
* Inlining
* GC
* Object allocation super cheap

# C++

# OS

* Kernel threads
* Scheduling
* Thread pinning
* Time sync
* Sockets, Filedescriptors
* System calls, user mode, kernel/priviledged mode

# Hardware

* Power control
* Caching, ICache, DCache
* TLB
* Cache subsystem, L1,L2,L3,QPI, Bandwith, >90% of chip is cache

## Memory layout

* Cache lines 64 byte?

## Memory access patterns

* Cache misses
* Sequential
* Arbitrary
* Spatial locality
* Temporal locality

## Tooling

* [JMH]
* [Gatling]
* [wrk2](https://github.com/giltene/wrk2)
* [tcpkali](https://github.com/machinezone/tcpkali/blob/master/doc/tcpkali.man.md)
* [jrt-socket-response-tool](https://www.azul.com/products/open-source-tools/jrt-socket-response-tool/)

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
* sustrik (ZeroMQ inventor)
* Brendan D. Gregg

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

# Videos

Name | Recorded | Speaker | Platform | Rating | Description |
-----| ---------|---------|----------|--------|-------------|
[How NOT to Measure Latency] | Strangeloop 2014 | Tene (Azul) | - | 10 | Must watch! |
[LMAX - How to Do 100K TPS at Less than 1ms Latency] | QCon SF 2010 | Barker & Thompson (LMAX) | Java | 9 | Classic one about the Disruptor |
[Java at the Cutting Edge: Stuff I Learned about Performance] | JVM Users Aukland | Barker (LMAX) | Java | - | Watched long ago, but think it was good |
[Benchmarking: You're Doing It Wrong] | Strangeloop 2014 | Greenberg (Google) | - | 8 | - |
[Taming the 9s] | Strangeloop 2014 | Weisberg (VoltDB) | Java/C++ | - | - |
[When to OS gets in the way] | Strangeloop 2015 | Price (LMAX) | Java/OS/HW | 9 | Explains importance of thread pinning etc |  
Mythbusting Modern Hardware to Gain 'Mechanical Sympathy' - https://www.youtube.com/watch?v=MC1EKLQ2Wmg&t=4s | Goto 2012 | Thompson | - | 8 | Clears up some common misconceptions about HW |
[JVM Profiling pitfalls] | - | Nitsan Wakart | Java | 9 | Getting deep into profiling |
[Performance Testing Java Applications] | Devoxx UK 2013 | Thompson | Java | 9 | Java Devs should watch this |
[A Crash Course in Modern Hardware] | Devoxx | Cliff Click | HW,OS,JVM,Java | 8 | Really a crash course but still quite good |
[CPU caches and why you care] | code::dive conference 2014 | Scott Meyers | HW,C++ | 9 | Classic one about caches, must watch |
[Deep Dive Performance] | ? | Nitsan Wakart | Java | 6 | 3 talks - some good some not so good |
[The Illusion of Execution] | JFokus 2015 | Nitsan Wakart | Java | 9 | Nice deep dive |
[CON1517 An Introduction to JVM Performance] | JavaOne 2015 | Rafael Winterhalter | Java | 9 | Very good and practical |

[Java at the Cutting Edge: Stuff I Learned about Performance]: https://www.youtube.com/watch?v=uKoZgIdVZQ4&t=402s
[Benchmarking: You're Doing It Wrong]: https://www.youtube.com/watch?v=XmImGiVuJno&list=PLljcY9k9tmL8k8oGzKcKL2D0AeDIi-V_A
[Taming the 9s]: https://www.youtube.com/watch?v=EmiIUW4splQ&index=2&list=PLljcY9k9tmL8k8oGzKcKL2D0AeDIi-V_A
[How NOT to Measure Latency]: https://www.youtube.com/watch?v=lJ8ydIuPFeU
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

[JMH]: http://openjdk.java.net/projects/code-tools/jmh/
[Gatling]: http://gatling.io/
[CO_USER_GROUP]: https://groups.google.com/forum/#!msg/mechanical-sympathy/icNZJejUHfE/BfDekfBEs_sJ
[CO_PDF]: https://www.azul.com/files/HowNotToMeasureLatency_LLSummit_NYC_12Nov2013.pdf