Bench
=====

Load generator: [wrk2](https://github.com/giltene/wrk2)
Target: [Raspberry Pi 3 Model B](../../../env/home-pi3)

```bash
david@david-ubuntu:~/projects/knowhow/performance$ ~/clones/wrk2/wrk -t2 -c10 -d60s -R100 --latency http://10.0.0.200:80/index.html > 10c100reqs
david@david-ubuntu:~/projects/knowhow/performance$ ~/clones/wrk2/wrk -t2 -c20 -d60s -R200 --latency http://10.0.0.200:80/index.html > 20c200reqs
david@david-ubuntu:~/projects/knowhow/performance$ ~/clones/wrk2/wrk -t2 -c50 -d60s -R500 --latency http://10.0.0.200:80/index.html > 50c500reqs
david@david-ubuntu:~/projects/knowhow/performance$ ~/clones/wrk2/wrk -t2 -c75 -d60s -R750 --latency http://10.0.0.200:80/index.html > 75c750reqs
david@david-ubuntu:~/projects/knowhow/performance$ ~/clones/wrk2/wrk -t2 -c80 -d60s -R800 --latency http://10.0.0.200:80/index.html > 80c800reqs
david@david-ubuntu:~/projects/knowhow/performance$ ~/clones/wrk2/wrk -t2 -c100 -d60s -R1000 --latency http://10.0.0.200:80/index.html > 100c1000reqs
david@david-ubuntu:~/projects/knowhow/performance$ ~/clones/wrk2/wrk -t2 -c150 -d60s -R1500 --latency http://10.0.0.200:80/index.html > 150c1500reqs
```

