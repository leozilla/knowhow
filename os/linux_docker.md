Docker Linux
============

Running docker on linux.

# Use cases

## Container lifecylce

### Starting a stopped container

```bash
$ docker start foo-containerid
```

### Kill or stop the last started container

```bash
$ docker kill `docker ps -lq`
```

### Attaching bash to a running container

Allows to attach multiple shells

```bash
$ docker exec -it foo-containerid /bin/bash
```

Can only attach one shell

```bash
$ docker attach foo-containerid
```

```bash
pi@raspberrypi:~ $ docker ps
CONTAINER ID        IMAGE                                   COMMAND                  CREATED             STATUS              PORTS                                                                                            NAMES
39242156c56b        darkeye9/rpi2-graphite-grafana-statsd   "/bin/sh -c 'supervis"   31 minutes ago      Up 31 minutes       0.0.0.0:2003->2003/tcp, 0.0.0.0:3000->3000/tcp, 0.0.0.0:8125->8125/tcp, 0.0.0.0:8125->8125/udp   stats
pi@raspberrypi:~ $ docker ps -q
39242156c56b
pi@raspberrypi:~ $ docker attach 39242156c56b
```
