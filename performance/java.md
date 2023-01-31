Java
====

+ Always disable OS swapping!
* Indirections = cache misses -> arrays rock
* Async APIs needed
* JDK NIO still too much blocking, garbage for selector key set
* JNI expensive especially from C back to Java, caching JMethodIds helps etc

# JVM

* JIT
* Object Layout, Object Header, Padding, Memory Abstraction+GC overhead
* Profiling C1,C2,C3?
* JMM
* GC (Object allocation super cheap just one or two pointer move)
* Can take up to 20-30 min for JVM to really become hot - consider this when doing perf testing

## Optimizations

OpenJDK: https://wiki.openjdk.java.net/display/HotSpot/PerformanceTechniques

 * Tiered compilation
   + C0: Interpreted code
   + C1: Simple C1 compiled (client)
   + C2: Limited C1 compiled (client)
   + C3: Full C1 compiled (client)
   + C4: C2 complied (server)
 * Code Cache [Video Visualization](https://youtu.be/XeTgtS3xdcc?list=WL)
   + [JitWatch](https://github.com/AdoptOpenJDK/jitwatch)
 * Optimization and De-Optimization at runtime (makes use of OSR)
 * Virtual Method Dispatch: Monomorphic, Bimorphic, Megamorphic call sites
 * Lock elision
   + [1](https://youtu.be/9yzZ4d3xueU?list=WL&t=1314)
   + [2](http://www.ibm.com/developerworks/library/j-jtp10185/index.html)
 * Lock coarsening [1](http://www.ibm.com/developerworks/library/j-jtp10185/index.html)
 * Lock biasing
 * Adaptive locking [1](http://www.ibm.com/developerworks/library/j-jtp10185/index.html)
 * Inlining
 * [Scalar Replacement](https://www.youtube.com/watch?v=9yzZ4d3xueU&list=WL&index=3)
 * [Type Sharpening](https://youtu.be/9yzZ4d3xueU?list=WL&t=1341)
 * Loop unrolling
 * Escape analysis
 * Reads and Writes can be eliminated [1](https://www.infoq.com/presentations/JVM-Mechanics)
 * Constant propagation
 * Dead code elimination
 * Tail call elimination
 * On stack replacement (OSR) [1](https://youtu.be/9yzZ4d3xueU?list=WL&t=1375)
 * Class Hierarchy Analysis (CHA)
 * Inlining
    + https://wiki.openjdk.java.net/display/HotSpot/Inlining
    + http://www.azulsystems.com/blog/cliff/2011-04-04-fixing-the-inlining-problem
 * Interface invocation ([invoke-interface-optimisations])

Optimizations work best when:

 * JVM optimizes common and clean code, strange or unconventional code has less change to get optimized (JVM engineers dont plan for this)
 * Predictable control flow
 * Small methods
 * Specialized types (dont use Object if you can)
 
Optimizations which are not necessary to do by hand (https://www.infoq.com/presentations/JVM-Mechanics): 

 * creating temporary variables because you want to reduce memory access -> not necessary JIT does it for you
 * simplify math by hand -> not necessary JIT does it for you
 
## Inlining

Most important is *MaxInlineSize*

 * MaxInlineSize: https://youtu.be/teYzwaWmi-8?list=WL&t=1351
 
## Flags

 * Bigger Page Sizes: ```-XX:+UseLargePages``` potential speedup 10-30%
 * Compressed Ops ```-XX:+UseCompressedOops``` on by default, can save significant amount of memory
 * Prevent False Sharing in GC Card Tables ```-XX:+UseConCardMark``` Use with care: 15% sequential slowdown
 * Check compilations ```-XX:+PrintCompilation```
 * ```-XX:+DebugNonSafepoints``` for profiling everything
 * ```-XX:+PrintGCDetails``` [1](https://youtu.be/teYzwaWmi-8?list=WL&t=2167)
 * ```-XX:+PrintGCApplicationStoppedTime``` [1](https://youtu.be/teYzwaWmi-8?list=WL&t=2167)
 * ```-XX:+PrintSafepointStatistics``` [1](https://youtu.be/teYzwaWmi-8?list=WL&t=2167)
 * ```-XX:+PrintSafepointStatisticsCount=1``` [1](https://youtu.be/teYzwaWmi-8?list=WL&t=2167)

Inline JVM options (https://youtu.be/vZngvuXk7PM?t=1533) -XX:MaxBCEAEstimateSize=450 -XX:FreqInlineSize=425

## Safepoints

Point in execution where it is save to observe a threads state. All threads must be at a safe point to perform GC (at least in hotspot).

 * http://epickrram.blogspot.co.at/2015/08/jvm-guaranteed-safepoints.html

## Profiling

 * Dont use hprof (proposal to remove it)
 * VisualVM is a bit better than hprof
 * Best profilers
   + Jave Mission Control / Java Flight Recorder
   + Honest profiler (richard warburton)
   + Lightweight Java Profiler (google)
   + jHiccup
   + [async-profiler](https://github.com/jvm-profiling-tools/async-profiler)
 
### Java Mission Control

 * ```-XX:+DebugNonSafepoints``` for sampling at any location
 
## Memory
 
![MemoryLayout](http://www.pointsoftware.ch/wp-content/uploads/2012/11/Cookbook_JVMArguments_2_MemoryModel.png)

Most input comes from [this video](https://www.youtube.com/watch?v=c755fFv1Rnk&t=12s)

Sources of memory consumption:
- Heap
- Class Loading
- JIT compiler
- Threads

Footprint = sum of
- heap
- code cache
- GC and compiler structures
- metaspace
- symbol tables
- thread stacks
- direct buffers
- mapped files
- native libraries
- malloc/allocator overhead
- ...

JVM reserved/committed memory is basically what the OS calls virtual/resident memory. 

*reserved*: memory the JVM has already reserved and is allowed to use (but might not be able to use if sys runs oo physical memory) 
*committed*: memory the JVM claims to use atm

### Heap

```-Xms``` means initial size, not minimal size. Heap can still shrink bellow this unless explicitly disabled.

```-Xmx4G``` does only limit, but does not limit native memory consumption or off heap memory which can be large
Effects of ```-XX:UseContainerSupport```: 
 - Default heap size (JVM ergonomics)
 - Visible number of CPUs

Heap is allowed to grow and shrink and also JVM is allowed to free virtual memory (give mem back to OS), this typically happens after full GC.
The resizing ration can be controlled by ```-XX:MinHeapFreeRatio=N``` and ```-XX:MaxHeapFreeRatio=N```.
Use ```-XX:-AdaptiveSizePolicy``` if u dont want the heap to resize.

The data structures and memory used for GC is stored on the heap!

GC memory overhead:
- Mark bitmap (marks visited objs)
- Mark stacks
- Remembered set (probably the biggest one) - cannot be sized directly but only indirectly via ```-XX:G1HeapRegionSize```

```sun.nio.ch.Util$BufferCache``` is the cache for direct ByteBuffer's used for example when working with heap ByteBuffers.

### JVM native memory

Memory pools (probably incomplete list):
- metaspace
- code cache
- (compressed) class space

Memory can be allocated in 2 ways:
- via system allocator: per default `malloc`
- allocated directly from the OS via [mmap](https://man7.org/linux/man-pages/man2/mmap.2.html)

#### Class metadata

Metaspace:
- classes
- methods
- constant pools
- symbols
- annotations

Metaspace is unlimited by default.

#### JIT compilation

Code cache:
- controlled by ```-XX:InitialCodeCacheSize``` and ```-XX:ReservedCodeCacheSize```
- size depends on which compiler is used (C1 or C2), default = *ReservedCodeCacheSize* * 5

#### Thread memory

Default thread stack size is 1MB controlled via ```-Xss```

#### Symbols

- *SymbolTable*: names, signatures, etc
- *StringTable*: interned strings

### Off-Heap/Direct memory

Allocated via:
- `ByteBuffer` - limit configured via ```-XX:MaxDirectMemorySize```, collected after GC
- `FileChannel.map` - no limit, and also not counted in native memory tracking

```sun.nio.ch.Util$BufferCache``` is the cache for direct ByteBuffer's used for example when working with heap ByteBuffers.
This cache is thread local, which can become a problem with lots of threads.
Size can be limited via ```-Djdk.nio.maxCachedBufferSize=N```.

Standard allocator is typically `malloc` but `malloc` handles fragmentation badly which can cause inefficient/excessive memory usage.
Consider switching the allocator: [jemalloc](https://jemalloc.net/), [tcmalloc](https://github.com/google/tcmalloc), [mimalloc](https://github.com/microsoft/mimalloc)

### Important config params

* Let JVM respect cgroup limits ```-XX:UseContainerSupport```
* Set initial heap size as a percentage of total memory ```-XX:InitialRAMPercentage=N```
* Set maximum heap size as a percentage of total memory ```-XX:MaxRAMPercentage=N```
* Allocate physical memory immediately ```-XX:+AlwaysPreTouch``` - interessting for low latency sensitive apps
* Dont use ```-XX:-DisableExplicitGC```, instead use ```-XX:-ExplicitGCInvokesConcurrent``` on HotSpot
* Limit metasapce size via ```-XX:MaxMetaspaceSize=N```, ```-XX:CompressedClassSpaceSize```, ```-XX:MinMetaspaceFreeRatio=N``` and ```-XX:MaxMetaspaceFreeRatio=N```
* ```-XX:MetaspaceSize=N``` defines the high water mark when GC should run on metaspace
* ```-Xss``` to set thread stack size

### Tools

Check virtual and resident memory usage  
```top -o %MEM```

Complete memory map for each address (*anonymous* is usually java heap memory)   
```pmap -X $PID```

Tracking of native memory usage of JVM - attention high overhead! But tracks only memory allocated by the JVM and not 3rd party library JNI allocations etc
See: https://docs.oracle.com/javase/8/docs/technotes/guides/vm/nmt-8.html  
```java -XX:NativeMemoryTracking```

Analyzing metaspace and class loading issues  
```jcmd PID VM.classloader_stats```
```jcmd PID GC.class_stats```

Analyzing JVM internal symbol tables  
```jcmd PID VM.stringtable```
```jcmd PID VM.symboltable```

## GC

 * Tune Eden space in a way that short lived objects dont get promoted to old gen (https://www.youtube.com/watch?v=DFub1L3gzGo&index=4&list=WL)
 * Context switch between mutator and GC (not guaranteed to be scheduled back on same core)
 * False Sharing in GC Card Tables
   + Try ```-XX:+UseConCardMark``` Use with care: 15% sequential slowdown
 * GC Stop Time does not include Time to Safepoint (time to get all threads stopping)

Method references create garbage? WTF (https://youtu.be/vZngvuXk7PM?t=956)
8 fields, capturing lambdas is often the limit for Escape analysis (https://youtu.be/vZngvuXk7PM?t=1959)
Enums and Lambda trick (https://youtu.be/vZngvuXk7PM?t=2166)

```bash
# show gc stats for VM every second
$ jstat -gc $PID 1000
```

## Call Sites

See: [CON1517 An Introduction to JVM Performance] and [invoke-interface-optimisations]

 * Monomorphic: Only one call site - very good optimized - static method calls are always monomorphic
 * Bimorphic: Two possible call sites - still only one branch
 * Megamorphic: More than two possible call sites - vtable lookup, slow

Every type matters (even when calling final methods) the JVM does not check if a method is actually called.

**One type good, many types bad**

```java
List<String> list = ... // either ArrayList or LinkedList
list.size(); // a bimorphic call site
```

**Call site specialization**

See: CON1517 An Introduction to JVM Performance at 23:42 https://youtu.be/q8-10v15sZE?t=1422

The following is only possible if the log method is monomorphic. 
If the JVM cannot figure that out this optimizations are not possible.

Step 1:

```java
// very generic, will observe a lot of different types, hard to optimize
static void log(Objects... args) {
    System.out.println("Log");
    for (Object a : args) {
        System.out.println(a.toString()); // toString is super megamorphic
    }
}

void logSomething() {
    log("foo", 4, new Object()); // call site
}
```

Step 2: Will probably be inlined to

```java
void logSomething() {
    System.out.println("Log");
    Object[] args = new Object[] { "foo", 4, new Object() };
    for (Object a : args) {
        System.out.println(a.toString());
    }
}
```

Step 3: Loop unrolling

```java
// reduced previously megamorphic call site to 3 monomorphic call sites
void logSomething() {
    System.out.println("Log");
    System.out.println("foo".toString());
    System.out.println(new Integer(4).toString());
    System.out.println(new Object().toString());
}
```

Step 4: Specialization method calles based on type

```java
// reduced previously megamorphic call site to 3 monomorphic call sites
void logSomething() {
    System.out.println("Log");
    System.out.println("foo"); // specialized to String - calls overload which takes String
    System.out.println(4); // specialized to int - calls overload which takes int
    System.out.println(new Object()); // specialized to Object - calls overload which takes Object
}
```

## Pauses

https://youtu.be/vZngvuXk7PM?t=795

Tools:
 * [jHiccup](https://github.com/giltene/jHiccup) [1](https://youtu.be/teYzwaWmi-8?list=WL&t=2568)

Causes for pauses:
 * IO delays (seeks and writes)
 * OS interrupts (5ms is not uncommon, most of the pauses are from the OS)
 * Lock contention
 * Switching between locking modes [1](https://youtu.be/teYzwaWmi-8?list=WL&t=2266)
 * De-optimizing code [1](https://youtu.be/teYzwaWmi-8?list=WL&t=2266)

Flags to print statistics about pauses:

 * ```-XX:+PrintSafepointStatistics``` [1](https://youtu.be/teYzwaWmi-8?list=WL&t=2167)
 * ```-XX:+PrintSafepointStatisticsCount=1``` [1](https://youtu.be/teYzwaWmi-8?list=WL&t=2167)

# Literature

 * http://jeremymanson.blogspot.co.at/2010/07/why-many-profilers-have-serious.html
 
# Videos

Name | Recorded | Speaker         | Rating | Description            |
-----| ---------|-----------------|--------|------------------------|
[JVM Mechanics](https://www.youtube.com/watch?v=E9i9NJeXGmM) | Silicon Valley JUG 2015 | Azul            | 9 |                        | 
[JVM Mechanics 2](https://www.infoq.com/presentations/JVM-Mechanics) | QCon 2012 | Jil Tene        | |                        |
[How to Lie (to Yourself) about Performance](https://youtu.be/teYzwaWmi-8?list=WL) | Devoxx Poland 2016 | Douglas Hawkins | 8 | JIT, CO, Sampling Bias |
[Performance Testing Java Applications](https://www.youtube.com/watch?v=4qxfVuK50Jk) | Devox 2022 | Pratik Patel    | 8 | JHiccup, Azul          |
[Memory footprint of a Java process](https://www.youtube.com/watch?v=c755fFv1Rnk&t=12s) | Devox 2022 | Andrei Pangin                | 10 | Java memory deep dive  |