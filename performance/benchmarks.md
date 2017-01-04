Benchmark
=========

 * Constant rate
 * Throughput at given latency
 * Latency at given throughput
 * [Latency for a set Throughput](https://vanilla-java.github.io/2016/07/20/Latency-for-a-set-Throughput.html)
 * Pitfalls
    + Coordinated Omission: [CO_USER_GROUP], [CO_PDF]
    + [Why don't I get the throughput I benchmarked?](https://vanilla-java.github.io/2016/07/23/Why-dont-I-get-the-throughput-I-benchmarked.html)
    + Performance is not composable. If you have the performanservice A and service B
 * [Synthetic benchmarking actually sucks](https://youtu.be/M9o1LVfGp2A?t=2901) - best data is obtained from production load 
 * Always use [JMH] for java micro benchmarks, always!
    + [The Art of Java Benchmarking]
    + [JMH the lesser of two evils]
    + [OpenJDK MicroBenchmarks](https://wiki.openjdk.java.net/display/HotSpot/MicroBenchmarks)
    + [Anatomy of a flawed benchmark](http://www.ibm.com/developerworks/java/library/j-jtp02225/index.html)

# Guidelines and Problems

Mainly Shipilevs ([JMH the lesser of two evils], [The Art of Java Benchmarking]) and Alexandrescu ([Writing Fast Code I], [Writing Fast Code II]) thoughts.

### Benchmarks are experiments

* Computer Science: Functional requirements, often very abstract
* Software (Performance) Engineering: Exploring complex interactions between hardware, software and data. Based on empirical evidence (real/natural science)

**Experiments requires control** and they require a model from which we derive our tests based on a hypothesis.
 
Eg: Based on our current understanding of the system we assume that X will help us achieve better latency. 
We try to control our environment and only change X.

We have to continuously understand, refine or reject our performance model.

### Performance is not composable

Say you have the performance of A and B. If you but those two together the performance is NOT A + B.
**We cannot derive what will be the performance** (It can be almost anything).

### Optimization is the problem

Optimizations distort the performance model. It is too complex to predict the performance of a complex system. 
There are to many unclear interdependencies (from hardware, OS, compiler, runtime, outside influences ...)

**Benchmarking is the (endless) fight against the optimizations** (therefore a good benchmarking harness must mange optimizations).

Example: Whats the performance of ```new Object()```?
Answer: You cannot really tell, it could be allocated in TLAB or need to go to LOB or maybe its scalarized or allocation can be completely eliminated by JIT.

Every new optimization can/will break our performance model.

### Use the minimum

The minimum contains to noise, it is the faster YOUR code can run.

### Have a baseline

Without a baseline you dont have anything to compare against.

### Use constant rates? Use percentiles. Forget the mean

### Warmup

Most benchmarks require warmup. Waiting for transient responses to settle down (JIT, OS scheduling, ...)

### Dont jump to conlcusion to fast

# Microbenchmarks

 * See [Microbenchmarking](microbench.md)

# Tooling

* [JMH]
* [Gatling]
* [wrk2](https://github.com/giltene/wrk2)
* [tcpkali](https://github.com/machinezone/tcpkali/blob/master/doc/tcpkali.man.md)
* [jrt-socket-response-tool](https://www.azul.com/products/open-source-tools/jrt-socket-response-tool/)

[The Art of Java Benchmarking]: https://vimeo.com/78900556
[JMH the lesser of two evils]: https://www.youtube.com/watch?v=VaWgOCDBxYw&t=518s
[Writing Fast Code I]: https://www.youtube.com/watch?v=vrfYLlR8X8k
[Writing Fast Code 2]: https://youtu.be/9tvbz8CSI8M
[JMH]: http://openjdk.java.net/projects/code-tools/jmh/
[Gatling]: http://gatling.io/
[CO_USER_GROUP]: https://groups.google.com/forum/#!msg/mechanical-sympathy/icNZJejUHfE/BfDekfBEs_sJ
[CO_PDF]: https://www.azul.com/files/HowNotToMeasureLatency_LLSummit_NYC_12Nov2013.pdf