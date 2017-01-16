Java
====

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

## Optimizations

OpenJDK: https://wiki.openjdk.java.net/display/HotSpot/PerformanceTechniques

 * Tiered compilation
   + C0: Interpreted code
   + C1: Simple C1 compiled (client)
   + C2: Limited C1 compiled (client)
   + C3: Full C1 compiled (client)
   + C4: C2 complied (server)
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
 
## Flags

 * Bigger Page Sizes: ```-XX:+UseLargePages``` potential speedup 10-30%
 * Compressed Ops ```-XX:+UseCompressedOops``` on by default, can save significant amount of memory
 * Prevent False Sharing in GC Card Tables ```-XX:+UseConCardMark``` Use with care: 15% sequential slowdown
 * Check compilations ```-XX:+PrintCompilation```

Inline JVM options (https://youtu.be/vZngvuXk7PM?t=1533) -XX:MaxBCEAEstimateSize=450 -XX:FreqInlineSize=425

## Safepoints

Point in execution where it is save to observe a threads state. All threads must be at a safe point to perform GC (at least in hotspot).

 * http://epickrram.blogspot.co.at/2015/08/jvm-guaranteed-safepoints.html
 
## Memory
 
![MemoryLayout](http://www.pointsoftware.ch/wp-content/uploads/2012/11/Cookbook_JVMArguments_2_MemoryModel.png)
 
### Heap

### Off-Heap
 
## GC

 * Context switch between mutator and GC (not guaranteed to be scheduled back on same core)
 * False Sharing in GC Card Tables
   + Try ```-XX:+UseConCardMark``` Use with care: 15% sequential slowdown

Method references create garbage? WTF (https://youtu.be/vZngvuXk7PM?t=956)
8 fields, capturing lambdas is often the limit for Escape analysis (https://youtu.be/vZngvuXk7PM?t=1959)
Enums and Lambda trick (https://youtu.be/vZngvuXk7PM?t=2166)

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

Causes for pauses
 * IO delays (seeks and writes)
 * OS interrupts (5ms is not uncommon, most of the pauses are from the OS)
 * Lock contention
 
# Videos

Name | Recorded | Speaker | Rating | Description |
-----| ---------|---------|--------|-------------|
[JVM Mechanics](https://www.youtube.com/watch?v=E9i9NJeXGmM) | Silicon Valley JUG 2015 | Azul | 9 |  | 
[JVM Mechanics 2](https://www.infoq.com/presentations/JVM-Mechanics) | QCon 2012 | Jil Tene | | |