Docker Linux
============

Running docker on linux.

* http://phusion.github.io/baseimage-docker/#intro

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

### Images

Show image layers

```bash
$ docker history foo-containerid
```


# Books

Name | Author | Rating | Description |
-----|--------|--------|-------------|
The Docker Book: Containerization is the new virtualization | James Turnbull | 8 | First book i read about docker, i liked it |

# Videos

Name | Speaker | Conference | Rating | Description |
-----|---------|------------|--------|-------------|
[Docker and the JVM, a good idea?] | Chris Batey | Devvox Poland 2016 | 9 | Common pitfalls about cgroups, quite nice |

[Docker and the JVM, a good idea?]: https://www.youtube.com/watch?v=Vt4G-pHXfs4