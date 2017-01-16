Microbenchmarks
===============

 * measure effects in the range of nanoseconds (not microseconds)
 * not necessarily bad, it depends how we use them. You should not jump to conclusion too fast.
 * division operations are expensive (up to 92 cycles on 64bit x86) and therefore should not be done in microbenchmarks [1](https://youtu.be/1DuMvpwWHH4?t=1334)

# Java

All microbenchmarks are writting using [JMH](http://openjdk.java.net/projects/code-tools/jmh/).

 * [OpenJDK MicroBenchmarks](https://wiki.openjdk.java.net/display/HotSpot/MicroBenchmarks)
 * [Anatomy of a flawed benchmark](http://www.ibm.com/developerworks/java/library/j-jtp02225/index.html)

## JMH
 
 * [Samples](http://hg.openjdk.java.net/code-tools/jmh/file/tip/jmh-samples/src/main/java/org/openjdk/jmh/samples/) 
 * [The Art of Java Benchmarking] Oredev 2013
 * [JMH the lesser of two evils] Devoxx 2013

**Attention**

 * Sources and sinks must be unpredictable, use Blackhole or return values for sinks, sources/params should NOT be final

The no-op benchmark.

```java
    @Benchmark
    public void noOp() {
    }
```

Environment: [home-ws]

Observations:

 * A single core is on 100% (as expected)
 * During warmup the thread switches between cores but most of the time than stays on one core for the rest of the fork (as expected)
 * The thread sometimes switches between cores during one fork, almost looks random (not really as expected)

Results:

```
Result "testMethod":
  4337584455.651 ±(99.9%) 31219828.361 ops/s [Average]
  (min, avg, max) = (3532643414.729, 4337584455.651, 4428412768.070), stdev = 132186708.160
  CI (99.9%): [4306364627.291, 4368804284.012] (assumes normal distribution)


# Run complete. Total time: 00:06:42

Benchmark                Mode  Cnt           Score          Error  Units
MyBenchmark.testMethod  thrpt  200  4337584455.651 ± 31219828.361  ops/s
```

# Videos

 * https://shipilev.net/talks/jvmls-July2014-benchmarking.mp4

[home-ws]: env/home-ws
[JMH the lesser of two evils]: https://www.youtube.com/watch?v=VaWgOCDBxYw&t=518s
[The Art of Java Benchmarking]: https://vimeo.com/78900556