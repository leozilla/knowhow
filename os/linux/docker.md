Docker Linux
============

Running docker on linux.

* http://phusion.github.io/baseimage-docker/#intro

| Command | Description |
|---------|-------------|
| ```$ docker build --no-cache -t mytag .``` | Rebuild w/o cache |
| ```$ docker run --rm CONTAINER_ID``` | After running the container deletes it and its associated file system when container exits |
| ```$ docker ps -a```                 | Get a list of all containers including stopped ones |
| ```$ docker ps -f name=^k8s_nginx* -q``` | Get only the container id of the container(s) matching the name filter. |
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

Advanced commands:

```bash
# List IP addresses of all containers
docker ps -q | xargs -n 1 docker inspect --format '{{ .Name }} {{range .NetworkSettings.Networks}} {{.IPAddress}}{{end}}' | sed 's#^/##'
```

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

# Best practices

### 1. Minimize the docker build context 
 
This can be done 
- by excluding files via the `.dockerignore` file, see: [.dockerignore file](https://docs.docker.com/engine/reference/builder/#dockerignore-file)
- by controlling the *PATH/URL* which is passed to the `docker build` command, see: []()

Reducing the build context has the following advantages:
- sending the context from the docker client to the docker daemon can be much faster
- you dont accidentally add unwanted files into the docker image (eg: log, tmp or even credential files)

See: [Build context](https://docs.docker.com/build/building/context/)

### 2. Make image builds deterministic

Always specify a version, and avoid using `latest` or any other mutable version or tag.

This includes 2 things:
- always use the same base image
- always use the same version of software installed in the image

About the base image:  

Avoid `FROM maven` or `FROM maven:3.6.3`.

Use the most specific tag `FROM maven:3.6.3-jdk-11-slim`
or even better use `FROM maven:3.6.3-jdk-11-slim@sha256:68ce1cd457891f48d1e137c7d6a4493f60843e84c9e2634e3df1d3d5b381d36c`

About specifying versions:  

Avoid `apk add --no-cache git`

Use `apk add --no-cache git=2.8.6-r0`

### 3. Use multi stage builds

The simplest and often the best way to reduce the layers in the final image and overall image size is to use [Multi-stage builds](https://docs.docker.com/build/building/multi-stage/).

### 4. Use a small base images

Good starting points are [alpine](https://www.alpinelinux.org/), [distroless](https://github.com/GoogleContainerTools/distroless) or images tagged as *slim*.

### 5. Install only what is needed

Make the image is small as feasible/practical.

Examples:
- Use flag `apt-get --no-install-recommends xxx`
- Remove cache of your apt manager after installation
- Group together `RUN` commands to reduce image layers

Good practice:
```
RUN apt-get update \
 && apt-get -y install --no-install-recommends xxx
 && rm -rf /var/lib/apt/lists/*
```

### 6. Make use of the image layer cache

See: [Optimizing builds with cache](https://docs.docker.com/build/cache/)

Order layers in a way that things that change often are put last in the image.

Pseudo example:
```
FROM scratch

RUN add changes-rarely
RUN add changes-sometimes
RUN add changes-often
```

Avoid wildcards in `ADD` or `COPY` commands cos they break the image cache.
Use `COPY sample-runner /deployment/app` instead of `COPY *-runner /deployment/`.

#### 7. Ensure OS signals are correctly handled


### 8. Dont run the container as *root*

Use/create a *daemon*  user with minimal privileges which runs your container. Never run your container as *root*.


# Books

Name | Author | Rating | Description |
-----|--------|--------|-------------|
The Docker Book: Containerization is the new virtualization | James Turnbull | 8 | First book i read about docker, i liked it |

# Videos

Name | Speaker | Conference | Rating | Description |
-----|---------|------------|--------|-------------|
[Docker and the JVM, a good idea?] | Chris Batey | Devvox Poland 2016 | 9 | Common pitfalls about cgroups, quite nice |

[Docker and the JVM, a good idea?]: https://www.youtube.com/watch?v=Vt4G-pHXfs4
