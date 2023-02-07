# Java in containers and k8s

Guidelines for running JVM processes inside containers/kubernetes.

## Runtime

#### 1. Don't over-commit on heap memory relative to container memory limits

Example: container memory limit = 4GB

Depending on the app, use about 40-70% for heap memory. eg: `-Xmx` between `1200M-2800M` or better yet use `-XX:MinRAMPercentage=40` and `-XX:MaxRAMPercentage=70`.
The reason for this is that the JVM itself uses quite some memory, and some java libs might also use a lot of off-heap memory.
Especially networking or storage libs use a lot of off-heap memory, eg: netty, lucene, fst, ehcache ...
Those libs are found in most modern DB systems, eg: kafka, cassandra, elastic-search ...  

#### 2. Prefer `-XX:MinRAMPercentage` and `-XX:MaxRAMPercentage` over `-Xms` and `-Xmx`

Using a relative rather than absolute way of configuring heap size allows to change the memory size only in one place -> the container memory limit, 
and the JVM heap size will adjust accordingly.

#### 3. Be aware that containers that exceed memory limits might get killed

Example: container memory limit = 4GB

If u start your JVM with ```-Xmx12G``` its very likely that your process will get [OOM killed by k8s] or the [Linux OOM killer]. 
The time when the process gets killed is rather nondeterministic, it can be on memory allocation request or only at a later point in time when the system runs short on physical memory.

#### 4. Explicitly specify GC and memory pool sizes, dont rely on JVM ergonomics

Always configure GC and heap size. If those things are not configured, the JVM will choose some defaults.

Its not a good idea to rely on JVM ergonomics, for 2 reasons:
1. The JVM does not know your app as good as you do/should, and might therefor choose none optimal values
2. JVM ergonomics can be different depending on JVM version and vendor

See [JVM default ergonomics](https://learn.microsoft.com/en-us/azure/developer/java/containers/overview)

#### 5. Enable `-XX:+HeapDumpOnOutOfMemoryError` to take a heap dump on `OutOfMemoryError`

A very common source for `OutOfMemoryError` is a memory leak. Having a heap dump will almost always allow you to find this leak.
Keep in mind that taking a heap dump of a full heap will often take several minutes!

Here are some examples how to handle heap dump files in k8s: https://danlebrero.com/2018/11/20/how-to-do-java-jvm-heapdump-in-kubernetes/

#### 6. Take extra caution when taking heap dumps of large heaps

Keep in mind that full GC pauses or taking heap dumps might exceed health check timeouts. (eg: k8s liveness and readiness probes)

Rough estimate for taking heap dumps of a full! heap. 1GB = 10-60sec pause, depending on compute resources.

#### 7. Experiment with the granularity of JVMs in micro service architectures

The JVM has quite some CPU and especially memory overhead. Startup times are often rather slow and the JVM needs a couple of minutes for code to run efficiently.
Its especially bad in rapidly scalable systems which create and tear down containers a lot.
This makes the JVM not the best candidate for lots of small micro services.

Consider putting multiple micro services into a single JVM process. But notice that this does not mean to have multiple JVM processes in a container.
You should still have one JVM process in the container but the JVM hosts more than one of your micro services.
On a code level, those services are still independent from each other. It just a deployment decision to put the services together into one process.
It should still be easy to separate them into their own JVM.

## Config

#### Use environment variables

Env vars play very well with docker and k8s.
Make sure existing config files support env var substitution.

Example env var usage in a config file:
```hocon
my-app {
    # this setting can be overwritten by an env var
    grpc-port = 1234
    grpc-port = ${?MY_APP_GRPC_PORT}

    # stage is a mandatory env var
    stage = ${STAGE}
}
```

#### 5. Store config in *ConfigMaps* and credentials in *Secrets*

*ConfigMaps* are a great way of getting configuration injected in your container. 
There are several ways of creating *ConfigMaps* and there are several ways of mounting *ConfigMaps* and making the config values accessible in your java app.

Store passwords and other sensitive data in *Secrets*, this should be obvious but I still often see security sensitive data be stored in *ConfigMaps*.

A good practice is to have dedicated *ConfigMaps* and *Secrets* for each environment.

Lots of popular libraries have the ability to include/merge section of a config file from another config file. This works well with *ConfigMaps*. 
It allows to overwrite/include env specific configuration from dedicated *ConfigMaps* into one shared/common env agnostic *ConfigMap*.

Examples for this are: 
- `<include>` in logback or log4j2 config files, see example [logback.xml](logback-config-include/logback.xml)
- `include` feature of [Typesafe config]

#### 6. Use a powerful application config file format

Java property files, YAML or JSON files are not very powerful config formats. They lack important features.

Use file formats like [HOCON](https://github.com/lightbend/config/blob/main/HOCON.md) which have support for the following features:
- Environment variable lookup
- Optional variables
- Referencing and substitutions
- Comments
- Multi-line strings
- List and object merging
- Includes
- Compatibility with well known formats like JSON

Check out [Typesafe config]

## Logging

#### 7. Log environment and app info on app startup

Its very helpful to log the following information when your app starts:
- `Runtime.getRuntime().availableProcessors()` - to check if the JVM sees only those CPUs which are available for the container
- `Runtime.getRuntime().maxMemory()` - max heap size
- App version - the semantic version of the application
- Java version - the actual java version in use
- Build timestamp - when was the app built
- optional: Dependencies - list all dependencies and their versions

Libraries like [sbt-buildinfo](https://github.com/sbt/sbt-buildinfo) are very helpful here.

#### 7. Log important process lifecycle events

Its a good idea to add a `ShutdownHook` which produces some log message, as the very first thing in your `main` method. 
If you see this log message you know that the JVM is in the shutdown process.
Note that its still difficult to know if this is the first shutdown hook which gets executed but at least you know that the shutdown process has started.
```java
public static void main(String[] args) {
    Runtime.getRuntime().addShutdownHook(() -> {
        logger.info("Shutdown hook executing - either process completed or process received a termination signal")    
    });
    
    // ... remaining program ...
}
```

It can also be very helpful to have logging for each specific signal, eg: *INT*, *STOP*, *TERM* or *KILL*.
```java
Signal.handle(new Signal("TERM"), signalHandler);
Signal.handle(new Signal("STOP"), signalHandler);
```

#### 8. Include context into log messages

- Thread name
- Logger name
- Host name (if applicable)
- Distributed tracing info (eg: TraceId, SpanId)
- User defined `MDC` data (eg: UserId, Connection IP Address)

Refer to the [OpenTelemetry Spec](https://opentelemetry.io/docs/reference/specification/logs/data-model/)

#### 9. Make logging config updatable at runtime

Its immensely helpfully to turn on more verbose logging levels while your app is running.
Both `log4j2` and `logback` support hot config reload.

Again, use *ConfigMaps* to store the logging config and mount them as writable. See example [deployment.yaml](log4j2-hot-reload/deployment.yaml)

#### 10. Turn on GC logging

#### 11. Logging via *stdout*/*stderr*

Prefer to output your logging information to *stdout*/*stderr*. This allows for the greatest interoperability.

## Stability

#### Make good use of liveness, readiness and startup probes

Use startup probes if app startup is known to take long and it becomes unpractical to define readiness and liveness probes to handle those startup delays.
See: [Protect slow starting containers with startup probes](https://kubernetes.io/docs/tasks/configure-pod-container/configure-liveness-readiness-startup-probes/#define-startup-probes)

Liveness checks should be used to check if the app is in a broken state from which it cannot recover unless beeing restarted.
A failed liveness probe means that the app needs to be restarted.

Readiness probes should be used to check if the app is ready to receive traffic.
A failed readiness probe means that the app is temporarily unable to handle traffic.

Examples for liveness and readiness probes. Choose based on your app needs.
- Performing a [synthetic transaction](https://en.wikipedia.org/wiki/Synthetic_monitoring), which runs the code that a real user transaction would also run through -> getting the user data of a dummy user.
- A HTTP endpoint which performs a *SELECT 1* SQL query on the DB and produces a fake message into a dummy queue/topic in the message broker.
- Writing and reading to the file system
- Inspecting circuit breaker states

Bad practices include:
- Using a dedicated HTTP server to serve probe requests while production traffic goes through another code path
- Most users forget that readiness should not only check the readiness of the app itself but also if its dependencies can be reached. A simple HTTP GET Ping/Pong endpoint is not enough.
- Probes that accumulate data or change the state of the system in a disruptive way
- Probes that have significant overhead on the system
- Probes that are slow

#### 5. Ensure health check timeouts are longer than expected process pauses

Full GC pauses or taking heap dumps might exceed health check timeouts. (eg: k8s liveness and readiness probes)
Consider those pauses when configuring timeouts and grace periods. 

#### Ensure fast app startup and shutdown times

Its always good to have fast startup times, but fast shutdown times can be of equal importance. Slow shutdown of one service can delay the startup of new versions or replicas.

For startup there is a tradeoff between what you do already at startup and what you do on demand. 
The biggest impact on startup time is usually the amount of class files which get loaded, its therefor good to remove all classpath dependencies which are not needed.
There are also other things which effect/reduce startup times, eg: JIT compiler settings, Class data sharing, AOT compilation.

#### Use logical names and URLs for resources

Use URLs and DNS names for network resources incl. storage resources.
Using URLs instead of `java.nio.file.Path` in your code can become quite handy when switching from file based directories to cloud based object stores.
It can make configuration more uniform and sometimes even allows to make env changes w/o changing code.

#### Use the same container image for all environments

Its best to use a single image for both, *dev*, *stage* and *prod* instead of having env specific images.

Tools which are needed in *dev* or *stage* should either be put in separate images or if security policies allow it, they should be included in the single image which is used in all environments.

#### Make stateful app dependencies explicit

Examples:
- Make the location of filesystem resources configurable

#### Prefer one process per container

#### Make sure the JVM is container aware

Check the java version and ensure it supports/respects container CPU and memory limits.
Some old java versions require to use *-XX:+UnlockExperimentalVMOptions* and *-XX:+UseCGroupMemoryLimitForHeap*.  

Container aware versions include:
- Java 1.8.0_191 or later
- Java 11 or later

#### Handle OS signals correctly

When running an application in docker, the first application will run with a process ID of 1 (PID=1). 
The Linux kernel handles processes with a PID of 1 in a special way.
Usually, the process on a PID with process number 1 is the initialization process.

See also [Docker and the PID 1 zombie reaping problem](https://blog.phusion.nl/2015/01/20/docker-and-the-pid-1-zombie-reaping-problem/)

There are various solutions for this, one is to use a minimal init system for your container eg: [dumb-init](https://github.com/Yelp/dumb-init)
or use a dedicated docker image build tool for java apps eg: [jib](https://github.com/GoogleContainerTools/jib)

#### Explicitly specify config parameters via command line arguments, dont rely on JVM ergonomics

## Security

#### Run app/container as daemon user

#### Only expose ports that are required

Dont put `EXPOSE` command in your image definition, some users might not need some ports to be exposed.
Let users of the image allow themself what ports to expose when running the container.

Some ports dont need to be exposed at all, they might only be used for debugging purposes when running a shell in the container.

## Tooling

#### 12. Dont include tools in app image, create a dedicated image and run it as standalone container

Instead of putting tools in the app container image itself, create a dedicated (java) tooling image.
Run the tooling image on demand as a one shot container. 

#### 13. JMX

#### 14. Describe container metadata

Make use of k8s labels and annotations, consider adding the following:
- semantic version of application
- image tag
- full image digest
- app build timestamp 

See: [Recommended Labels](https://kubernetes.io/docs/concepts/overview/working-with-objects/common-labels/)

#### Implement admin, mgmt tasks as separate jobs or one shot containers

Following the single responsibility or separation of concerns design philosophy, 
its a good idea to separate tasks that are not part of the app business logic into their own image.

Tasks which are useful for many different apps can be implemented using the *Sidecar* pattern. Make sure they are small and modular.

Examples:
- Database backups
- Jobs that create statistics based on data stored in DB
- Shipping logs (eg: [fluentd](https://github.com/fluent/fluentd-kubernetes-daemonset) daemon)
- k8s [Init containers](https://kubernetes.io/docs/concepts/workloads/pods/init-containers/) 
- Config update (eg: checking an HTTP endpoint and updating the *ConfigMap* when a new config is available)

## Container Image

Refer to: [Docker Best Practices](../../os/linux/docker.md)

## Testing

#### Allow for pluggable dependency implementations

Changing to more easily usable dependencies can greatly simplify (local) testing. 
Using just local files instead of more heavyweight systems (eg: AWS localstack, GCP BigQuery) can greatly speed up development iterations. 

Example: In the prod system your app uses a kafka compacted topic for receiving config.

If you have abstracted the config backend, it should be possible to change the implementation to receive the config from the file system instead of kafka.
But keep in mind that you should still have a good set of integration tests, and that you should use the real/prod config impl when testing on *dev* and *stage* env. 

#### Provide automation for running your app locally in the container

Most developers only test their app by running it via the build tool (eg `./mvnw spring-boot:run` or `sbt run`) because this is available out of the box.
But this has the following important downsides:
- Problems/bugs which happen when running the app in the container are caught only later (sometimes a lot later). 
  It can take a lot more time when analyzing this problems when running the app in a cloud env instead of locally.
  Finding an fixing those problems on the local machine is usually much easier.
- If u run your app within the container you will immediately see some things which you need to make parameterizable/configurable.

If u have logic in your container (eg: startup scripts) you want to test this often and have an easy way to test it.
Try to get rid of any code outside of the container, eg: shell commands in k8s jobs before starting the container.

Running the app locally in the container should be as simple as running 2 commands:
```bash
sbt my-app/docker:publishLocal
docker run \
  -e STAGE=dev \
  -e MY_APP_DEBUG=true \
  my-app:latest \
  -c=thomas.muster -d=2021-01-01
```

#### Allow for running your system locally in a lightweight k8s env

If your whole system is not too large in size (eg: compute resources), it can help to have automation in place to run everything in minikube or k3d.
(If correctly configured) both minikube and k3d behave very closely to GKE or other managed k8s offerings.

Such a lightweight env can be used for all kinds of automated or manual testing scenarios.
A full system test run can be automated in just a few steps:
```bash
deploy/k3d/up.sh # start local k3d cluster

MY_APP_DOCKER_REGISTRY=k3d sbt my.app/docker:publish # necessary for images to be built and pushed to k3d registry
release/deploy.sh -a my-app

source system-tests/k3d-env.sh
sbt 'system-tests/e2e:testOnly *MyApp'
```


[Linux OOM killer]: https://www.kernel.org/doc/gorman/html/understand/understand016.html
[OOM killed by k8s]: https://kubernetes.io/docs/concepts/configuration/manage-resources-containers/#my-container-is-terminated
[Typesafe config]: https://github.com/lightbend/config