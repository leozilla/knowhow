Linux Control Groups
====================

 * Processes inside a cgroup see ALL the memory and all the cores of the host machine<a name="see-all"></a>

Java Impacts (see [Docker and the JVM, a good idea?]):

Because processes inside a cgroup see all the resources of the host

 * JVM and java library thread pool sizes should be adjusted accordingly - reduce threads
 * Also min and max heap should usually be set explicitly

Videos:

 * [Docker and the JVM, a good idea?]

[Docker and the JVM, a good idea?]: https://www.youtube.com/watch?v=Vt4G-pHXfs4