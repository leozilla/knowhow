Linux
=====

# Readings

* https://lwn.net/

# Streams

CTRL+D: Stops current standard input entry from the terminal?
CTRL+C: Terminate a program, signal?

# Booting

* fu*ked up grub? -> [bootrepair](https://help.ubuntu.com/community/Boot-Repair)

# Terminals

 * http://superuser.com/questions/421463/why-does-ctrl-v-not-paste-in-bash-linux-shell

# Shell

## Globbing/Wildcards

Substitution happens before the command is run.
 
 * ```*``` match any chars
 * ```?``` match exactly one arbitrary char
 * to prevent globs, enclose in quotes like ```'*'```

## Assignment

Assign the return value of a shell command to a variable

```bash
$ JETT=$(ps -f -C java | grep jetty | awk '{ print $2 }')
```

# Memory

```bash
cat /proc/meminfo
```

# Filesystem

## Home

Configuration files called dot files .bashrc .login or directories .ssh
Use ```.[]*``` or ```.??*``` to get all dot files except the current and parent directories. 

## proc

## Mouting

```sudo blkid``` Get UUID of partition

# Commands and Tools

 * https://blog.serverdensity.com/80-linux-monitoring-tools-know/
 * ls, cp, mv, rm, cat, touch, cd, mkdir, rmdir
 * grep, less, pwd, diff, file, find, head, tail, sort
 * top,fg,bg
 
## Processes

### ps

Find PID of all java processes where jetty is included in the command line.

```bash
$ ps -f -C java | grep jetty | awk '{ print $2 }'
```

### pgrep

Find process id of last started java process

```bash
$ pgrep -n java
```

### top

### taskset

### fg

Continues a stopped job by running it in the foreground

## Common

### grep

### tail

### awk

### sed

## Filesystem

### df

### dh

### tar

## Networking

Get current state of connections

```bash
$ ss -tan
```

Unix always closes all open descriptors when a process terminates. [UNP]

Linux ephemeral ports

```bash
pi@raspberrypi:~ $ cat /proc/sys/net/ipv4/ip_local_port_range
32768   60999
```

## Performance

### Perf Events

 * ```perf list``` list all possible event types
 * ```perf stats``` counts events (uses efficient in kernel counter)
 * ```perf record``` samples events (higher overhead relative to the rate of events), use -g to include call graph/stack trace
 * ```perf report``` report from a perf.data file
 * ```perf script``` dump a perf.data file as text

### Flame Graphs

 * http://techblog.netflix.com/2015/07/java-in-flames.html
 * http://www.brendangregg.com/FlameGraphs/cpuflamegraphs.html#Java

See:

 * https://www.youtube.com/watch?v=_Ik8oiQvWgo&t=727s
 * Getting symbols: http://www.brendangregg.com/perf.html#symbols
 
[UNP]: http://www.unpbook.com/