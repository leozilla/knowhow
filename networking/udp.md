User Datagram Protocol
======================

User data is encapsulated in UDP *datagrams*. Length of a datagram is passed to the receiver along with the data this differs 
from TCP which is a byte stream protocol.
UDP is connectionless, there is no need for long-term relationship between UDP client and server.
Client can create socket and send datagram(s) to a server and immediately send another datagram on the same socket to 
a different server. Similarly, a UDP server can receive several datagrams on a single socket from different clients.

* [Great TCP-vs-UDP Debate]

[UDP vs TCP]: https://news.ycombinator.com/item?id=13272610
[Great TCP-vs-UDP Debate]: http://ithare.com/64-network-dos-and-donts-for-game-engines-part-iv-great-tcp-vs-udp-debate/
