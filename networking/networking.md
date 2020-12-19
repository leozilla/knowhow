Networking
==========

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

## SSH Tunnels

[Examples](https://www.ssh.com/ssh/tunneling/example)

### Reverse tunnel

How to make `db.mycompany.com:5432` go to my home computer's `localhost:3000`?

Assuming you connect from home to `db`, you need a reverse tunnel.
```
ssh -R 5432:localhost:3000 db.mycompany.com
```
This will enable processes running at `db` to connect to `localhost:8080` and actually speak to your home computer at port 3000.

### HTTP proxy with ssh

If at home, I cannot access `http://s3.mycompany.com`, but `db` at work can access `s3`, 
how to make the home computer able to access `http://s3.mycompany.com`?

The best way to create a http proxy with ssh is with socks.
```
ssh -D 8888 db.company.com
```
Go to your browser connection settings and enable proxy connection, choose socks4/5 and host: localhost, port 8888. 
Then just type `http://s3.mycompany.com` in the browser.

### Local port forward

If at home, I cannot access `db.mycompany.com`, but `web` server at work can, how to make it possible to access `db.mycompany.com` also using ssh tunnel.
```
ssh -L 3333:db.mycompany.com:3306 web.mycompany.com
```
This means that you will be able to connect at `localhost:3333` from your home computer and everything will be forwarded to `db.mycompany.com:3306` 
as if the connection was made by `web.mycompany.com`. 
Host `db` will see `web` as the client connecting, so you need to login with the same username and password you use when working from `web`.

## Proxy

### Anonymous proxy

This server reveаls іts іdentіty as а proxy server, but does not disclose the originating IP аddress of the client. 
Although this type of server can be discovered easily, іt cаn be benefіcіаl for some users as іt hіdes the originating IP address.

### Transparent proxy

This server not only іdentіfіes іtself as a proxy server, but with the support of HTTP heаder fields such as `X-Forwarded-For`, 
the originating IP аddress cаn be retrieved as well. 
The mаіn benefіt of usіng this type of server is іts аbіlіty to cаche a websіte for faster retrieval.

### Reverse proxy

A type of proxy server that retrieves resources on behalf of a client from one or more servers.
These resources are then returned to the client, appearing as if they originated from the reverse proxy server itself.

Typical use cases:

 * In the case of secure websites, a web server may not perform TLS encryption itself, but instead offload the task to a reverse proxy.
 * Optimize content by compressing it in order to speed up loading times.
 * A reverse proxy can reduce load on its origin servers by caching static content and dynamic content, known as web acceleration.

## Bridge

## Load Balancer

Most commonly deployed when a site needs multiple servers because the volume of requests is too much for a single server to handle efficiently.

# Tooling

## WANEm

## NMap

## iptables

## tcpdump

## iwconfig

## nc — arbitrary TCP and UDP connections and listens

| Command | Description |
|---------|-------------|
| ```$ nc -v DESTINATION_IP PORT`` | Listen with verbose output |


## netstat

[UDP vs TCP]: https://news.ycombinator.com/item?id=13272610
[Great TCP-vs-UDP Debate]: http://ithare.com/64-network-dos-and-donts-for-game-engines-part-iv-great-tcp-vs-udp-debate/
[Network Programming]: http://ithare.com/network-programming-socket-peculiarities-threads-and-testing/
[64 Network DO’s and DON’Ts]: http://ithare.com/64-network-dos-and-donts-for-game-engine-developers-part-i-client-side/
[UNP]: http://www.unpbook.com/
[MTU]: https://en.wikipedia.org/wiki/Maximum_transmission_unit
[2 Generals problem]: https://en.wikipedia.org/wiki/Two_Generals'_Problem
[FLP Proof]: http://www.cs.yale.edu/homes/aspnes/pinewiki/FischerLynchPaterson.html