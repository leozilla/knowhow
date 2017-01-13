Networking
==========

**Under construction - currently mostly brainstorming**

# General

Usually there is a distinction between client and server and the fact that the client always initiates a requests 
tends to simplify the protocol as well as the programs.
Client and server are typically user processes, while the TCP/IP protocols are normally part of the kernel protocol stack.
[UNP]

Why do sockets provide the interface from the upper three OSI layers into the transport? [UNP]

 1. The upper layers handle all the details of the app (FTP, Telnet, HTTP) and know little about the communication details
    The lower layers know little about the app, but handle sending data, waiting for ack, sequencing, verifying checksums.
 2. Upper layers often form a **user process** while the lower four layers are normally provided by the OS. 

# Programming

Unix always closes all open descriptors when a process terminates. [UNP]
In C the global variable ```errno``` is set to the exit/status code of the last system call.

# Protocols

## Ethernet

[MTU] of 1500 byte

## [IP](ip.md)

## ICMP

Handles error and control info between routers and hosts.

## IGMP

Used with multicasting which is optional with IPv4.

## ARP and RARP

ARP maps IPv4 address into a hardware address, RARP does the reverse (eg when diskless node is booting).

## [TCP](tcp.md)

## [UDP](udp.md)

## SCTP

Multi homing, message oriented, can provide multiple streams between connection endpoints (reduces head of line blocking), 
length of record written by sender is preserved/passed to receiver.

## SIP

## HTTP

## HTTP 2

## HTTP WS

## AMQP

# Design

* [64 Network DO’s and DON’Ts]

# Programming

* [Network Programming]

# Concepts

## NAT and IP Masquerade

## Firewall

## Proxy

## Bridge

# Tooling

## WANEm

## NMap

## iptables

## tcpdump

## iwconfig

## netstat

[UDP vs TCP]: https://news.ycombinator.com/item?id=13272610
[Great TCP-vs-UDP Debate]: http://ithare.com/64-network-dos-and-donts-for-game-engines-part-iv-great-tcp-vs-udp-debate/
[Network Programming]: http://ithare.com/network-programming-socket-peculiarities-threads-and-testing/
[64 Network DO’s and DON’Ts]: http://ithare.com/64-network-dos-and-donts-for-game-engine-developers-part-i-client-side/
[UNP]: http://www.unpbook.com/
[MTU]: https://en.wikipedia.org/wiki/Maximum_transmission_unit
[2 Generals problem]: https://en.wikipedia.org/wiki/Two_Generals'_Problem
[FLP Proof]: http://www.cs.yale.edu/homes/aspnes/pinewiki/FischerLynchPaterson.html