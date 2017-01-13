Transmission Control Protocol
=============================

Connection-oriented, reliable, full-duplex byte streaming (*stream sockets*), no record/message markers (framing is up to the app).

## Terms

 * *Maximum segment size*: Maximum amount of data willing to accept in each TCP segment.

## Features

### Reliability

After some number of retransmissions, TCP gives up, total time depends on the impl and varies between 1 - 10 minutes.
TCP provides reliable delivery of data *or* reliable notification of failure. Reliable delivery itself is impossible.

 * [TCP is not reliable](http://blog.h2o.ai/2013/08/tcp-is-not-reliable/)
 * [Ultimate SO_LINGER page](https://blog.netherlabs.nl/articles/2009/01/18/the-ultimate-so_linger-page-or-why-is-my-tcp-not-reliable)

### RTT

TCP continuously estimates the *round-trip time* between client and server dynamically to know how long to wait for ACK.
This varies between milliseconds in LANs and seconds across a WAN.

### Flow Control

TCP always tells its peer how many bytes it is willing to accept (free RCV buffer size). This is called the advertised *window*.
When buffer is full, it must wait for the app to read data from the buffer before it can accept/acknowledge any more data from the peer.

### Full Duplex

Applications on both sides can send and receive data at any time. This does not mean the the underlying data link protocol is also 
full duplex or must be. UDP can be full-duplex.

## Problems

Head of line blocking, nagle+delayed ack, [TCP and heartbeats](http://250bpm.com/blog:22)

## Server

### Establish

Setup TCP *listening descriptor* takes the steps: ```socket```, ```bind```, ```listen```.
```accept``` does then accept a new client connection request. 
When the handshake completes, ```accept``` returns the *connected descriptor*.
A new descriptor is returned by ```accept``` for each client that connects.

### Terminate

The server closes its connection with the given client by calling ```close``` which initiates the normal TCP connection termination:
FIN sent in each direction and each FIN acked by the other end (4 messages in total).

* [UDP vs TCP]
* [Great TCP-vs-UDP Debate]
* [About TIME-WAIT state](https://vincent.bernat.im/en/blog/2014-tcp-time-wait-state-linux.html#about-time-wait-state)   

[UDP vs TCP]: https://news.ycombinator.com/item?id=13272610
[Great TCP-vs-UDP Debate]: http://ithare.com/64-network-dos-and-donts-for-game-engines-part-iv-great-tcp-vs-udp-debate/
