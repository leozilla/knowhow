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

# Code to learn from

* JCTools
* Disruptor
* Fast Flow
* Aeron
* Akka

# Books, Literature, etc

# Videos

[JMH]: http://openjdk.java.net/projects/code-tools/jmh/
[Gatling]: http://gatling.io/
[CO_USER_GROUP]: https://groups.google.com/forum/#!msg/mechanical-sympathy/icNZJejUHfE/BfDekfBEs_sJ
[CO_PDF]: https://www.azul.com/files/HowNotToMeasureLatency_LLSummit_NYC_12Nov2013.pdf