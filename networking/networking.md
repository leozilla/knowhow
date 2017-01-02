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

## IP

```bash
# Discover IP addresses in certain subnet
$ nmap 10.0.0.0/24
```

Its possible for apps to bypass the transport layer and use IP4/6 directly, this is called a *raw socket*.

## ICMP

Handles error and control info between routers and hosts.

## IGMP

Used with multicasting which is optional with IPv4.

## ARP and RARP

ARP maps IPv4 address into a hardware address, RARP does the reverse (eg when diskless node is booting).

## TCP

Connection-oriented, reliable, full-duplex byte streaming (*stream sockets*), no record/message markers (framing is up to the app).

### Terms

 * *Maximum segment size*: Maximum amount of data willing to accept in each TCP segment.

### Features

#### Reliability

After some number of retransmissions, TCP gives up, total time depends on the impl and varies between 1 - 10 minutes.
TCP provides reliable delivery of data *or* reliable notification of failure. Reliable delivery itself is impossible.

 * [TCP is not reliable](http://blog.h2o.ai/2013/08/tcp-is-not-reliable/)
 * [Ultimate SO_LINGER page](https://blog.netherlabs.nl/articles/2009/01/18/the-ultimate-so_linger-page-or-why-is-my-tcp-not-reliable)

#### RTT

TCP continuously estimates the *round-trip time* between client and server dynamically to know how long to wait for ACK.
This varies between milliseconds in LANs and seconds across a WAN.

#### Flow Control

TCP always tells its peer how many bytes it is willing to accept (free RCV buffer size). This is called the advertised *window*.
When buffer is full, it must wait for the app to read data from the buffer before it can accept/acknowledge any more data from the peer.

#### Full Duplex

Applications on both sides can send and receive data at any time. This does not mean the the underlying data link protocol is also 
full duplex or must be. UDP can be full-duplex.

### Problems

Head of line blocking, nagle+delayed ack, [TCP and heartbeats](http://250bpm.com/blog:22)

### Server

#### Establish

Setup TCP *listening descriptor* takes the steps: ```socket```, ```bind```, ```listen```.
```accept``` does then accept a new client connection request. 
When the handshake completes, ```accept``` returns the *connected descriptor*.
A new descriptor is returned by ```accept``` for each client that connects.

#### Terminate

The server closes its connection with the given client by calling ```close``` which initiates the normal TCP connection termination:
FIN sent in each direction and each FIN acked by the other end (4 messages in total).

* [UDP vs TCP]
* [Great TCP-vs-UDP Debate]
* [About TIME-WAIT state](https://vincent.bernat.im/en/blog/2014-tcp-time-wait-state-linux.html#about-time-wait-state)   

## UDP

User data is encapsulated in UDP *datagrams*. Length of a datagram is passed to the receiver along with the data this differs 
from TCP which is a byte stream protocol.
UDP is connectionless, there is no need for long-term relationship between UDP client and server.
Client can create socket and send datagram(s) to a server and immediately send another datagram on the same socket to 
a different server. Similarly, a UDP server can receive several datagrams on a single socket from different clients.

* [Great TCP-vs-UDP Debate]

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