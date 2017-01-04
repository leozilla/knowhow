# My first flame graph

See: [Flame Graphs](../../../performance.md#flame-graphs)

Java app must be started with ```-XX:+PreserveFramePointer``` (expect up to ~2% performance degradation)

```
$ cd ~/tools
$ git clone https://github.com/brendangregg/FlameGraph
$ git clone https://github.com/jrudolph/perf-map-agent
$ git clone https://github.com/leozilla/benchmarketing
$ export FLAMEGRAPH_DIR=~/tools/FlameGraph
$ mvn install package
$ cd perf-map-agent
$ cmake .
$ make
$ cd benchmarketing/ws-echo-bench-jetty-vanilla
$ java -jar target/ws-echo-bench-jetty-vanilla-1.0-SNAPSHOT-jar-with-dependencies.jar -XX:+PreserveFramePointer
$ JETTY_PID=`ps -f -C java | grep jetty | awk '{ print $2 }'`
$ tcpkali -c 50 -r 5000 -T 60s --websocket --message "ping" localhost:8080 # apply some load
$ bin/perf-java-flames $JETTY_PID
```
First FlameGraph (click to zoom):
[![First FlameGraph](https://cdn.rawgit.com/leozilla/knowhow/master/performance/experiments/home-ws/flamegraph/first_flamegraph.svg)](https://cdn.rawgit.com/leozilla/knowhow/master/performance/experiments/home-ws/flamegraph/first_flamegraph.svg)