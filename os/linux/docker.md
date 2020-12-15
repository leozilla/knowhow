Docker Linux
============

Running docker on linux.

* http://phusion.github.io/baseimage-docker/#intro

| Command | Description |
|---------|-------------|
| ```$ docker build --no-cache -t mytag .``` | Rebuild w/o cache |
| ```$ docker run --rm CONTAINER_ID``` | After running the container deletes it and its associated file system when container exits |
| ```$ docker ps -a```                 | Get a list of all containers including stopped ones |
| ```$ docker inspect CONTAINER_ID``` | Get all info about container |
| ```$ docker inspect --format {{.NetworkSettings.IPAddress}} CONTAINER_ID``` | Inspect with Go template |
| ```$ docker diff CONTAINER_ID``` | Shows modified files in container |
| ```$ docker logs CONTAINER_ID``` | List of everything thats happened inside the container |
| ```$ docker start CONTAINER_ID```                 | Starting a stopped container |
| ```$ docker kill `docker ps -lq` ``` | Kill the last started container |
| ```$ docker exec -it CONTAINER_ID /bin/bash``` | Interactively execute a command in the container |
| ```$ docker container stop $(docker container ls -a -q)``` | Stop all containers |
| ```$ docker history CONTAINER_ID``` | Show image layers |
| ```$ docker rm CONTAINER_ID``` | Delete a container |
| ```$ docker rm $(docker ps -a -q)``` | Delete all stopped containers |
| ```$ docker rm -v $(docker ps -aq -f status=exited)``` | Delete all stopped containers and their volumes which are not used by other containers |
| ```$ docker rmi $(docker images -f “dangling=true" -q) && docker images prune -a``` | Remove dangling images |
| ```$ docker images --no-trunc --format '{{.ID}}' | xargs docker rmi``` | Remove cached docker layers |
| ```$ docker container stop $(docker container ls -a -q) && docker system prune -a -f --volumes``` | Complete Docker system clean |

```bash
pi@raspberrypi:~ $ docker ps
CONTAINER ID        IMAGE                                   COMMAND                  CREATED             STATUS              PORTS                                                                                            NAMES
39242156c56b        darkeye9/rpi2-graphite-grafana-statsd   "/bin/sh -c 'supervis"   31 minutes ago      Up 31 minutes       0.0.0.0:2003->2003/tcp, 0.0.0.0:3000->3000/tcp, 0.0.0.0:8125->8125/tcp, 0.0.0.0:8125->8125/udp   stats
pi@raspberrypi:~ $ docker ps -q
39242156c56b
pi@raspberrypi:~ $ docker attach 39242156c56b
```

Because unnecessary layers bloat images (and the AUFS filesystem has a hard limit of
127 layers), many Dockerfiles try to minimize the number of layers by specifying several 
UNIX commands in a single `RUN` instruction.

A container exits when its main process exits.

 - keep images explicitly versioned and reference them by version instead of `latest`
 - make it possible to reference the git commit of a running container, possibly by using the git hash as the image version
 - make sure your processes in the container return the correct exit code

[Best practices (docker)]: https://docs.docker.com/develop/dev-best-practices/
[Best practices (google)]: https://cloud.google.com/solutions/best-practices-for-building-containers
[Performance Improvements]: https://stackify.com/docker-performance-improvement-tips-and-tricks/

# Books

Name | Author | Rating | Description |
-----|--------|--------|-------------|
The Docker Book: Containerization is the new virtualization | James Turnbull | 8 | First book i read about docker, i liked it |

# Videos

Name | Speaker | Conference | Rating | Description |
-----|---------|------------|--------|-------------|
[Docker and the JVM, a good idea?] | Chris Batey | Devvox Poland 2016 | 9 | Common pitfalls about cgroups, quite nice |

[Docker and the JVM, a good idea?]: https://www.youtube.com/watch?v=Vt4G-pHXfs4
