Memory Performance
==================

# Paging

Demand paging touching a piece of memory may physically block to perform IO.

> I would much rather have the OS kill programs when it is close to the limit, 
and continue running reliably, than to deal with a paging madness"

Joe Duffy: [Asynchronous Everything]

# GC

> But I’d say the prime directive was to avoid superfluous allocations like the plague. 
> Even short-lived ones. There is a mantra that permeated .NET in the early days: Gen0 collections are free. 
> Unfortunately, this shaped a lot of .NET’s library code, and is utter hogwash. 
> Gen0 collections introduce pauses, dirty the cache, and introduce beat frequency issues in a highly concurrent system.

Joe Duffy: [Asynchronous Everything]

# Memory layout/allignment

* Cache lines 64 byte?
* http://www.insightfullogic.com/2013/Jan/03/slab-guaranteed-heap-alignment-jvm/
* http://psy-lob-saw.blogspot.co.at/2013/01/direct-memory-alignment-in-java.html

# Memory access patterns

* Cache misses
* Sequential
   + Take care when iterating multi dimensional arrays see [CPU caches and why you care]
* Arbitrary
* Spatial locality
   + use smaller data types ```-XX:+UseCompressedOops```
   + avoid 'holes' in your data - keep it close together (array)
   + make access as linear as possible
   + prefer collections of primitive types (unfortunately not incl in JDK)
* Temporal locality
* arrays >> linked list, for small n arrays are even better than hashmap or other O(1) data structures
* https://mechanical-sympathy.blogspot.co.at/2012/08/memory-access-patterns-are-important.html

**Sequential predictable access**

```java
int[] someArray = ... // arrays of primitive types have no object header and are alligned sequential in memory
for (int i = 0; i < someArray.length; i++) 
    someArray[i]++;
```

**Unpredictable access**

```java
int[] someArray = ... // see above
for (int i = 0; i < someArray.length; i += SKIP_NUM) 
    someArray[i]++;

List<Integer> list = new LinkedList<>();
// add elements
list.foreach(i -> /* .. do stuff .. */); // 1 cache miss for next node, 1 cache miss for Integer class?
```

[Asynchronous Everything]: http://joeduffyblog.com/2015/11/19/asynchronous-everything/