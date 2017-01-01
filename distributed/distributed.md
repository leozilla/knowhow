Distributed
===========

**Under construction - currently only brainstorming**

# Theory

* 2 Generals problem
* LWP Proof
* [CAP](http://henryr.github.io/cap-faq/)
* [A critique of the CAP theorem](https://www.cl.cam.ac.uk/research/dtg/www/files/publications/public/mk428/cap-critique.pdf)
* [Fallacies of distributed computing](https://en.wikipedia.org/wiki/Fallacies_of_distributed_computing)
* End to end argument for systems design
* [Stop calling databases CP or AP](https://martin.kleppmann.com/2015/05/11/please-stop-calling-databases-cp-or-ap.html)
* Dynamo paper
* Consistency Models
   + Eventual Consistency

# Practice

* Network partitions = network link misconfiguration, application pauses by GC etc
* RPC = CORBA, DCOM, .NET Remoting, gRPC, Thrift, Finagle

## Verification

* Jepsen

# Concepts

## Consistency Models

## ACID

## BASE

## Atomic Commitment and Consensus

### 2PC

### 3PC

### Paxos

### Raft

### ZAB

# People to learn from

 * Leslie Lamport - no comment
 * The Erlang Team (Joe Armstrong etc)
 * The Akka Team (Boner, Kuhn, Klang)
 * Aphyr
 * Peter Bailis
 * Caitie McCaffrey
 * Martin Kleppmann

# Literature

Name | Author | Platform | Rating | Description |
-----|--------|----------|--------|-------------|
[There is No Now] | Justin Sheehy | - | 10 | Time in distributed systesm - you know |
[What we talk about when we talk about distributed systems] | - | - | - | - |
https://github.com/aphyr/distsys-class | Aphyr | - | 8+ | Aphyr's DS class |
[PBS or How eventual is eventual consistency] | Bailis | - | 10 | Finally something practical about EC | 

# Videos

* Protocols of Interaction - https://www.infoq.com/presentations/protocols-microservices

[There is No Now]: https://queue.acm.org/detail.cfm?id=2745385
[What we talk about when we talk about distributed systems]: http://videlalvaro.github.io/2015/12/learning-about-distributed-systems.html
[PBS or How eventual is eventual consistency]: http://pbs.cs.berkeley.edu/#demo