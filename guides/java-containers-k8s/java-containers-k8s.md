# Java in containers and k8s

Guidelines for running JVM processes inside containers or kubernetes.

## Memory

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

#### 4. Enable `-XX:+HeapDumpOnOutOfMemoryError` to take a heap dump on `OutOfMemoryError`

A very common source for `OutOfMemoryError` is a memory leak. Having a heap dump will almost always allow you to find this leak.
Keep in mind that taking a heap dump of a full heap will often take several minutes!

Here are some examples how to handle heap dump files in k8s: https://danlebrero.com/2018/11/20/how-to-do-java-jvm-heapdump-in-kubernetes/

#### 5. Take extra caution when taking heap dumps of large heaps

Keep in mind that full GC pauses or taking heap dumps might exceed health check timeouts. (eg: k8s liveness and readiness probes)

Rough estimate for taking heap dumps of a full! heap. 1GB = 10-60sec pause, depending on compute resources.

## Config

#### Prefer using environment variables

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

#### 6. Use a powerful application config file format in combination with environment variables

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
- Max heap size via `Runtime.getRuntime().maxMemory()`
- App version - the semantic version of the application
- Java version - the actual java version in use
- Build timestamp - when was the app built
- (Dependencies) - list all dependencies and their versions

Libraries like [sbt-buildinfo](https://github.com/sbt/sbt-buildinfo) are very helpful here.

#### 8. Include context into log messages (headers)

- Thread name
- Logger name
- Host name (if applicable)
- Distributed tracing info (eg: TraceId, SpanId)
- User defined `MDC` data (eg: UserId, Connection IP Address)

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

Readiness probes should only fail if there is an unexpected long pause. XXX

#### Ensure fast app startup and shutdown times

There is a tradeoff between what you do already at startup and what you do on demand when needed.

#### Use logical names and URLs for resources and dependencies

Use URLs and DNS names for network resources incl. storage resources. This also includes container directories.
Use `file:///$APP_ROOT/mystore` instead of `/app/mystore`. This makes configuration more uniform and sometimes even allows to make env changes w/o changing code.

#### Use the same container image for all environments

Its best to use a single image for both, *dev*, *stage* and *prod* instead of having env specific images.

Tools which are needed in *dev* or *stage* should either be put in separate images or if security policies allow it, they should be included in the single image which is used in all environments.

#### Make stateful app dependencies explicit

Example:
- Make the location of filesystem resources configurable
- XXX

## Security

#### Run app/container as daemon user

#### Only expose ports that are required

Dont put `EXPOSE` command in your image definition, some users might not need some ports to be exposed.
Let users of the image allow themself what ports to expose when running the container.

Some ports dont need to be exposed at all, they might only be used for debugging purposes when running a shell in the container.

## Tooling

#### 12. Dont include tools in app image, create a dedicated image and run it as standalone container

Instead of putting tools like `jcmd`, `hprof`, `` in the app container image itself, create a dedicated java tooling image for this purpose.
Run the tooling image on demand as a one shot container "next" to the app container. 

#### 13. JMX

#### 14. Describe container metadata

Make use of k8s labels and annotations, eg:
- semantic version of application
- image tag
- full image digest
- app build timestamp 

See: [Recommended Labels](https://kubernetes.io/docs/concepts/overview/working-with-objects/common-labels/)

#### Implement admin, mgmt tasks as separate jobs or one shot containers

Examples:
- Database backups
- Jobs that create statistics based on data stored in DB

## Testing

#### Allow for pluggable dependencies

This can greatly simplify (local) testing.

Example: In the prod system your app uses a kafka compacted topic for receiving config.

If you have abstracted the config API, it should be possible to change the implementation to receive the config from the file system instead of kafka.
This makes local testing a lot easier.

#### Provide a local setup for running your app in the container

Most developers only test their app by running it via their build tool (eg `./mvnw spring-boot:run` or `sbt run`).
This has the following downsides:
- they dont catch the problems which happen when running the app in the actual runtime env which is the container
- they dont catch environment problems
- they dont see that some things should rather be made configurable based on the environment

#### Provide a lightweight k8s setup

If your whole project/product is not to large in size (eg: mem and compute resource consumption), it helps to have a lightweight setup for 
running all your services in XXX

[Linux OOM killer]: https://www.kernel.org/doc/gorman/html/understand/understand016.html
[OOM killed by k8s]: https://kubernetes.io/docs/concepts/configuration/manage-resources-containers/#my-container-is-terminated
[Typesafe config]: https://github.com/lightbend/config