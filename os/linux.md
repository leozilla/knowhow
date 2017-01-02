Linux
=====

# Readings

* https://lwn.net/

# Booting

* fu*ked up grub? -> [bootrepair](https://help.ubuntu.com/community/Boot-Repair)

# Filesystem

# proc

# Commands and Tools

 * https://blog.serverdensity.com/80-linux-monitoring-tools-know/

## ps

## top

## grep

## tail

## awk

## sed

## df

## dh

## tar

# taskset

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