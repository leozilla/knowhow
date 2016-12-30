Performance
===========

**Under construction - currently only brainstorming**

# Performance metrics

* Throughput
* Latency
* Service Time
* Response Time

## Howto measure

* Coordinated Omission
* Constant rate
* Throughput at given latency
* Latency at given throughput
* Tooling: tcpkali, gatling, wrk

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

