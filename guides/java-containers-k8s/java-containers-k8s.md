# Java in containers and k8s

Guidelines for running JVM processes inside containers or kubernetes.

## Memory

#### 1. Dont over-commit on heap memory relative to container memory limits

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

#### 4. Enable `-XX:+HeapDumpOnOutOfMemoryError` to taking heap dumps on `OutOfMemoryError`

A very common source for `OutOfMemoryError` is a memory leak. Having a heap dump will almost always allow you to find this leak.
Keep in mind that taking a heap dump of a full heap will often take several minutes!

Here are some examples how to handle heap dump files in k8s: https://danlebrero.com/2018/11/20/how-to-do-java-jvm-heapdump-in-kubernetes/

## Config

#### 5. Store config in *ConfigMaps* and credentials in *Secrets*

*ConfigMaps* are a great way of getting configuration injected in your container. 
There are several ways of creating *ConfigMaps* and there are several ways of mounting *ConfigMaps* and making the config values accessible in your java app.

Store passwords and other sensitive data in *Secrets*, this should be obvious but I still often see security sensitive data be stored in *ConfigMaps*.

A good practice is to have dedicated *ConfigMaps* and *Secrets* for each environment.

#### 6. Use a more powerful application config file format in combination with environment variables

Check out [Typesafe config](https://github.com/lightbend/config) and the [HOCON](https://github.com/lightbend/config/blob/main/HOCON.md) file format.

## Logging

#### 6. Log environment info on app startup

Its very helpfull to log the following information when your app starts:
- `Runtime.getRuntime().availableProcessors()` - to see if the JVM sees only those CPUs which are available in the container
- `` -
- `` -

#### 7. Include context into log messages

- Thread name
- Logger name
- Host name (if applicable)
- Distributed tracing info (eg: TraceId, SpanId)
- User defined `MDC` data (eg: UserId, )

#### 8. Make logging config updatable at runtime

Its immensely helpfully to turn on more verbose logging levels during application runtime.
Both `log4j2` and `logback` support hot config reload.

#### 9. Turn on GC logging

#### 10. Logging via *stdout*/*stderr*

Prefer to output your logging information to *stdout*/*stderr*. This allows for the greatest interoperability.

## Tooling

#### 3. Dont include tools in app image, run them as standalone container

Instead of putting tools like `jcmd`, `hprof`, `` in the app container image itself, create a dedicated java tooling image for this purpose.
Run the tooling image on demand as a one shot container "next" to the app container. 

#### 7. Expose JMX




#### 2. Ensure health checks are longer than expected process pauses

Example: k8s liveness and readiness probes

Keep in mind that full GC pauses or taking heap dumps might exceed those probe timeouts.

Rough estimate for taking heap dumps of a full! heap. 1GB = 10-60sec pause, depending on compute resources.

#### 4. Container metadata

Example: k8s lables and annotations

See: [Recommended Labels](https://kubernetes.io/docs/concepts/overview/working-with-objects/common-labels/)

- semantic version of application
- image tag

#### Use the same container image for all environments

Its best to use a single image for both, *dev*, *stage* and *prod*.
If this is not feasable for any reason, than at least use the same base image for all environments and only make additions to specific env related images.

#### Make stateful app dependencies explicit

#### Use logical names for resources

#### Make good use of liveness and readiness probes

#### Ensure fast app startup and shutdown times

#### Implement admin, mgmt tasks as separate jobs or one shot containers

#### Run container as daemon user

## Testing

#### Allow for pluggable dependencies

This can greatly simplify local testing.

Example: In the prod system your app uses a kafka compacted topic for receiving config.

If you have abstracted the config API, it should be possible to change the implementation to receive the config from the file system instead of kafka.
This makes local testing a lot easier.

#### Prefer using environment variables and mounted volumes

#### Provide a local setup for running your app in the container

#### Provide a lightweight k8s setup

[Linux OOM killer]: https://www.kernel.org/doc/gorman/html/understand/understand016.html
[OOM killed by k8s]: https://kubernetes.io/docs/concepts/configuration/manage-resources-containers/#my-container-is-terminated