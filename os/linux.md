Linux
=====

# Readings

* https://lwn.net/

# Streams

CTRL+D: Stops current standard input entry from the terminal?
CTRL+C: Terminate a program, signal?

# Booting

* fu*ked up grub? -> [bootrepair](https://help.ubuntu.com/community/Boot-Repair)

# Shell

## Globbing/Wildcards

Substitution happens before the command is run.
 
 * ```*``` match any chars
 * ```?``` match exactly one arbitrary char
 * to prevent globs, enclose in quotes like ```'*'```

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

## ps

## top

## grep

## tail

## awk

## sed

## df

## dh

## tar

## taskset

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

[UNP]: http://www.unpbook.com/