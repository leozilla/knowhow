Benchmark
=========

 * Constant rate
 * Throughput at given latency
 * Latency at given throughput
 * [Latency for a set Throughput](https://vanilla-java.github.io/2016/07/20/Latency-for-a-set-Throughput.html)
 * Pitfalls
    + Coordinated Omission: [CO_USER_GROUP], [CO_PDF]
    + [Why don't I get the throughput I benchmarked?](https://vanilla-java.github.io/2016/07/23/Why-dont-I-get-the-throughput-I-benchmarked.html)
 * [Synthetic benchmarking actually sucks](https://youtu.be/M9o1LVfGp2A?t=2901) - best data is obtained from production load 
 * Always use [JMH] for micro benchmarks, always!
    + [The Art of Java Benchmarking]
    + [OpenJDK MicroBenchmarks](https://wiki.openjdk.java.net/display/HotSpot/MicroBenchmarks)
    + [Anatomy of a flawed benchmark](http://www.ibm.com/developerworks/java/library/j-jtp02225/index.html)
    
# Microbenchmarks

 * My [Microbenchmarking](microbench.md)

# Tooling

* [JMH]
* [Gatling]
* [wrk2](https://github.com/giltene/wrk2)
* [tcpkali](https://github.com/machinezone/tcpkali/blob/master/doc/tcpkali.man.md)
* [jrt-socket-response-tool](https://www.azul.com/products/open-source-tools/jrt-socket-response-tool/)

[The Art of Java Benchmarking]: https://vimeo.com/78900556
[JMH]: http://openjdk.java.net/projects/code-tools/jmh/
[Gatling]: http://gatling.io/
[CO_USER_GROUP]: https://groups.google.com/forum/#!msg/mechanical-sympathy/icNZJejUHfE/BfDekfBEs_sJ
[CO_PDF]: https://www.azul.com/files/HowNotToMeasureLatency_LLSummit_NYC_12Nov2013.pdf