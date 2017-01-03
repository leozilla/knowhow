Microbenchmarks
===============

# Java

All microbenchmarks are writting using JMH.

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

## JMH

[home-ws]: env/home-ws