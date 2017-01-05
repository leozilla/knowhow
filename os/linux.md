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

## Input and Output

Send output from a command to a file, create or re-create file from output of command
```bash
$ command > file
```

Pipe standard out from one command to input of another
```bash
$ head /proc/cpuinfo | tr a-z
```

Pipe standard out and standard error
```bash
$ ls /notexisting > stdout-sink 2> stderr-sink
```

Send standard error to same place as standard out
```bash
$ ls /notexisting > stdout-sink 2>&1
```

**Stream Ids**

 * stdout: 1
 * stderr: 2

Send file to program stdin
```bash
$ head < /proc/cpuinfo
$ head lscpu # more common
```

## Assignment and Variables

```$$``` a shell variable that evaluated to the current shells pid

Assign the return value of a shell command to a variable

```bash
$ JETT=$(ps -f -C java | grep jetty | awk '{ print $2 }')
```

# Memory

```bash
cat /proc/meminfo
```

# Filesystem

![Hierarchy](http://www.blackmoreops.com/wp-content/uploads/2015/06/Linux-file-system-hierarchy-v2.0-2480px-blackMORE-Ops.png)

## Home

Configuration files called dot files .bashrc .login or directories .ssh
Use ```.[]*``` or ```.??*``` to get all dot files except the current and parent directories. 

## proc

## Mouting

```sudo blkid``` Get UUID of partition

## Modes and Permission

![Permissions](http://linuxcommand.org/images/permissions_diagram.gif)

Permissions for all other users are also called *world* permissions.
Each read, write and execute permission is called a permission bit.

Add group and world read permissions
```bash
$ chmod g+r file
$ chmod o+r file
# or
$ chmod go+r file
# or
$ chmod 644 file
```

Listing contents of a directory requires read permission, accessing a file in a dir requires execute permission for the dir.

## Links

Offer quick access to dir paths.
Links can also point to other links (chained sym links).

```bash
$ ln -s target linkname
```

# Commands and Tools

 * https://blog.serverdensity.com/80-linux-monitoring-tools-know/
 * ls, cp, mv, rm, cat, touch, cd, mkdir, rmdir
 * grep, less, pwd, diff, file, find, head, tail, sort
 * top,fg,bg
 
## Processes

### ps

3 different styles: Unix, BSD, GNU

Find PID of all java processes where jetty is included in the command line.
```bash
$ ps -f -C java | grep jetty | awk '{ print $2 }'
```

Show all of your running processes
```bash
$ ps a
```

Show all processes on the system, not just the ones you own
```bash
$ ps ax
```

Include more info, often used as ```ps aux```
```bash
$ ps u
```

Show full command line
```bash
$ ps w
```

### pgrep

Find process id of last started java process

```bash
$ pgrep -n java
```

### top

### kill

Default TERM [signal](http://man7.org/linux/man-pages/man7/signal.7.html)
```bash
$ kill pid
$ kill -TERM pid
$ kill -SIGTERM pid
$ kill -15 pid
```

Freece a process via STOP signal and continue it later via CONT
```bash
$ kill -STOP pid
$ kill -CONT pid
```

Sends an interrupt signal, same as using CTRL+C to terminate a process that is running in the current terminal
```bash
$ kill -INT pid
$ kill -SIGINT pid
$ kill -2 pid
```

Most brutal way, does not give process time to 'clean up'. OS terminates the process forcibly removing it from memory.
```bash
$ kill -KILL pid
$ kill -SIGKILL pid
$ kill -9 pid
```

 * http://bencane.com/2014/04/01/understanding-the-kill-command-and-how-to-terminate-processes-in-linux

### taskset

## Common

### grep

### tail

### awk

### sed

## Job Control

Normally when running a unix command from the shell the command runs to completion while blocking the current terminal.

Detach a program via adding ```&``` (will continue running even if user logs out).

```bash
david@david-ubuntu:~$ date; sleep 50 &
Don Jän  5 17:51:10 CET 2017
[1] 19698
```

Be aware that some programs expect input via stdin or terminal, those programs will effectively freeze.
Also it might print to stdout which suddenly shows up when you run a different program in foreground.
Best way is to redirect the output (and possibly input).

Send TSTP (stop) via CTRL+Z and later bring to foreground ```fg``` or background ```bg```

### fg

Continues a stopped job by running it in the foreground.

### bg

## Filesystem

### df

### dh

### tar

```bash
$ tar cvf archive-to-create.tar file1 file2
$ tar xvf archive-to-extract.tar
```

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