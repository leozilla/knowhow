Concurrency
===========

**Under construction - currently mostly brainstorming**

* [Problems](#problems)
* [Solutions](#solutions)
* [Literature](#literature)
* [Videos](#videos)

# Problems

* Mutual Exclusion (Critical Section)
* Readers Writers
* Producer Consumer

Starvation free, Fairness, Obstruction free, False Sharing, Cache Coherency 

# Concepts

* Safety
* Liveness
* [Execution Model](http://joeduffyblog.com/2015/11/19/asynchronous-everything/)
* Process:
* Thread: A stream of executable code within a process that has the ability to be scheduled [1](http://www.informit.com/articles/article.aspx?p=169479)

# Laws

* [Amdahls Law](law/amdahls.md)
* USL
* Littles Law
* Queing Theory

# Models

## Thread based

 * Thread per Request [1](http://berb.github.io/diploma-thesis/original/042_serverarch.html#thread)
 * Process per Request (old UNIX style) [1](http://berb.github.io/diploma-thesis/original/042_serverarch.html#mp)
 * Request dispatch to Thread Pool [1](http://berb.github.io/diploma-thesis/original/042_serverarch.html#mt)

[Considerations](http://berb.github.io/diploma-thesis/original/042_serverarch.html#scalemt)

## Event based

 * Event Model, Event Loop
 
[Considerations](http://berb.github.io/diploma-thesis/original/042_serverarch.html#scaleev)
 
## Combination

### SEDA [1](http://berb.github.io/diploma-thesis/original/042_serverarch.html#seda)

> If I were to design SEDA today, I would decouple stages (i.e., code modules) from queues and thread pools (i.e., concurrency boundaries)

Matt Welsh: [Retrospective on SEDA](http://matt-welsh.blogspot.co.at/2010/07/retrospective-on-seda.html)

 * Actor
 * CSP

# Implementation

 * Locking (Mutex, Semaphore)
 * Lock free

## Thread Models

 * http://www.cs.indiana.edu/classes/b534-plal/ClassNotes/thread-design-patterns4.pdf
 * [Beyond Threads And Callbacks](http://highscalability.com/blog/2013/3/18/beyond-threads-and-callbacks-application-architecture-pros-a.html)
 * [Why Events Are A Bad Idea (for high-concurrency servers)](https://people.eecs.berkeley.edu/~brewer/papers/threads-hotos-2003.pdf): imo outdated for really high number of concurrent connections

## Patterns

 * Reactor [1](http://berb.github.io/diploma-thesis/original/042_serverarch.html#reactor)
 * Proactor [1](http://berb.github.io/diploma-thesis/original/042_serverarch.html#proactor)

## Memory Barriers

https://mechanical-sympathy.blogspot.co.at/2011/07/memory-barriersfences.html

# Java

* Final keyword semantics
* [Dealing with InterruptedException](https://www.ibm.com/developerworks/library/j-jtp05236/)
* http://g.oswego.edu/dl/concurrency-interest/
* lazySet for not immediately flushing store buffers

## JMM

 * Java [synchronization actions](https://docs.oracle.com/javase/specs/jls/se7/html/jls-17.html#jls-17.4.2): 
   + ```volatile``` r/w
   + Locking and unlocking a monitor:  ```synchronized``` enter/exit 
   + Actions that start a thread or detect that a thread has terminated: ```Thread.start```, ```Thread.interrupted```, ```Thread.isInterrupted```, ```Thread.isALive```, ```Thread.join```
   + The (synthetic) first and last action of a thread
 * CPU level: lock
 * Cache level: MESI 
   + http://gvsmirnov.ru/blog/tech/2014/02/10/jmm-under-the-hood.html
   + https://mechanical-sympathy.blogspot.co.at/2013/02/cpu-cache-flushing-fallacy.html

## Testing

 * [jcstress](http://openjdk.java.net/projects/code-tools/jcstress/) best learned via [Close encounters of JMM kind]

# .NET

 * http://igoro.com/archive/volatile-keyword-in-c-memory-model-explained/

# People to learn from

* Gil Tene, Nitsan Wakart, Cliff Click, basically everyone from [Azul Systems](https://www.azul.com/)
* Lamport, Hoare, Knuth, Dijkstra
* Martin Thomson
* Doug Lea
* Brian Goetz
* Aleksey Shipilev
* Norman Maurer
* Todd L. Montgomery
* Jonas Boner, Viktor Klang, Roland Kuhn, Konrad Malawski, people from Typesafe/Lightbend
* Andrei Alexandrescu
* Herb Sutter
* Scott Meyers
* Joe Duffy

# Code to learn from

* JCTools
* Disruptor
* Fast Flow
* Aeron
* Netty
* Vertx
* RabbitMQ
* NodeJS?
* Akka
* Erlang, Clojure, Haskell
* Learn how hardware works - its super parallel
* Some GPU stuff, havent looked into

# Blogs

* [Mechanical Sympathy](https://mechanical-sympathy.blogspot.co.at/)
* [Concurrency Freaks](http://concurrencyfreaks.blogspot.co.at/)
* [Nitsans Blog](http://psy-lob-saw.blogspot.co.at/)
* [1024 Cores](http://www.1024cores.net/)
* [Shipilevs Blog](https://shipilev.net/)
* [Cliff Clicks Blog](http://www.cliffc.org/blog/)
* [Joe Armstrongs Blog](http://armstrongonsoftware.blogspot.co.at/2006/08/concurrency-is-easy.html)

# Literature

Name | Author | Language/Platform | Rating | Description |
-----|--------|-------------------|--------|-------------|
The art of multiprocessor programming | Herlihy, Shavit | Theory, Java | 9 | Tough but very good |
7 concurrency models in 7 weeks | Paul Butcher | Java, Clojure, GPU, etc | 9 | Very good read! |
Erlang and Akka Documentation | - | Erlang, Java, Scala | 9 | Very good explaination of actor concurrency model |
Java Concurrency in Practice | Brian Goetz etc | Java | 9 | Classic Java Concurrency book, must read |
Concurrent Programming in Java | Doug Lea etc | Java | 8 | Little outdated but still classic Java Concurrency book, (must) read |
[Java Memory Model Spec] | JSE | Java | Dont ask | Its the spec, must read |
[JMM Pragmatics] | Shipilev | Java | 10 | Very (very) good but requires a lot of time to read and understand |
[Close encounters of JMM kind] | Shipilev | Java | 10! | Best article about the JMM |
[Tenes Developer JMM Cookbook] | Tene | - | Very practical, boils done to one simple matrix |
[Doug Leas JMM Cookbook] | Lea | ? | Not fully read |
http://www.azulsystems.com/blog/cliff/2010-01-09-biased-locking | Cliff Click | Java | 9 | If you want to know how biased locking actual works |
http://joeduffyblog.com/2015/11/19/asynchronous-everything/ | Joe Duffy | .NET | 9 | Highly recommended |
http://joeduffyblog.com/2016/11/30/15-years-of-concurrency/ | Joe Duffy | .NET | 9 | |
http://highscalability.com/blog/2013/3/18/beyond-threads-and-callbacks-application-architecture-pros-a.html | HighScalability | - | - | - |

# Videos

Most videos have strong focus on performance because concurrency and performance are strongly related

Name | Recorded At | Speaker | Language/Platform | Rating | Description |
-----|-------------|---------|-------------------|--------|-------------|
[A Crash Course in Modern Hardware] | Devoxx | Cliff Click | HW,OS,JVM,Java | 8 | Really a crash course but still quite good |
[CPU caches and why you care] | code::dive conference 2014 | Scott Meyers | HW,C++ | 9 | Classic one about caches, must watch |
[History of Memory Models]  | MIT course | ? | Theory | ? | Not complete watched yet |
[Concurrency is Not Parallelism] | ? | Rob Pike | Go | 9 | A classic one |
[From Concurrent to Parallel] | JFokus 2016 | Brian Goetz | Java | 8 | Short+good explaination about streams and parallelism |
[Deep Dive Performance] | ? | Nitsan Wakart | Java | 6 | 3 talks - some good some not so good |
[CON1517 An Introduction to JVM Performance] | JavaOne 2015 | Rafael Winterhalter | Java | 9 | Very good and practical |
[CON1521 The Java Memory Model for Practitioners] | JavaOne 2015 | Rafael Winterhalter | Java | 9 | Very good and practical |
[Lock-Free Programming Part1] | CppCon 2014 | Herb Sutter | C++ | ? | Not fully watched yet |
[Lock-Free Programming Part2] | CppCon 2014 | Herb Sutter | C++ | ? | Not fully watched yet |
[The Illusion of Execution] | JFokus 2015 | Nitsan Wakart | Java | 9 | Nice deep dive |
[Finding Subtle but Common Concurrency Issues in Java Programs] | ? | ? | Java | 5 | Basics but with good visualization |
[Adventures with Concurrent Programming in Java] | Code Mesh 2015 | Martin Thompson | Java | 9 | Martins talks are always good |
[Aeron: Open-source high-performance messaging] | Strangeloop 2014 | Martin Thompson | Java | 8 | High level overview of Aeron |
[Advanded Topics: The Java Memory Model] | Google Talks 2007 | ? | Java | 7 | Fairly good introduction to JMM |
[Locks? We Don't Need No Stinkin' Locks!] | JAX 2012 | Barker | Java | 8 | Classic LMAX talk, nice low level |
[Understanding the Disruptor] | JAX 2011 | Barker, Gee | Java | 8 | Good introduction to the Disruptor |
[atomic<> Weapons, 1 of 2] | C++ and Beyond 2012 | Sutter | C++ | 9 | Very good and low level |  
[atomic<> Weapons, 2 of 2] | C++ and Beyond 2012 | Sutter | C++ | 8 | Still very good and low level |

[A Crash Course in Modern Hardware]: https://www.youtube.com/watch?v=OFgxAFdxYAQ
[Concurrency is Not Parallelism]: https://www.youtube.com/watch?v=cN_DpYBzKso
[CPU caches and why you care]: https://www.youtube.com/watch?v=WDIkqP4JbkE
[History of Memory Models]: https://www.youtube.com/watch?v=3e1ZF1L1VhY&t
[From Concurrent to Parallel]: https://www.youtube.com/watch?v=NsDE7E8sIdQ
[Deep Dive Performance]: https://www.youtube.com/watch?v=1Uc3M9YK5Tg&t
[CON1517 An Introduction to JVM Performance]: https://www.youtube.com/watch?v=q8-10v15sZE&t
[CON1521 The Java Memory Model for Practitioners]: https://www.youtube.com/watch?v=XgiXKPEILoc
[Lock-Free Programming Part1]: https://www.youtube.com/watch?v=c1gO9aB9nbs&t
[Lock-Free Programming Part2]: https://www.youtube.com/watch?v=CmxkPChOcvw&t
[The Illusion of Execution]: https://www.youtube.com/watch?v=rzuLbvT5354
[Finding Subtle but Common Concurrency Issues in Java Programs]: https://www.youtube.com/watch?v=Oi6-pXX11qw&t
[Aeron: Open-source high-performance messaging]: https://www.youtube.com/watch?v=tM4YskS94b0
[Adventures with Concurrent Programming in Java]: https://www.youtube.com/watch?v=eKVpea51tvo
[Understanding the Disruptor]: https://www.youtube.com/watch?v=DCdGlxBbKU4
[Advanded Topics: The Java Memory Model]: https://www.youtube.com/watch?v=WTVooKLLVT8
[Locks? We Don't Need No Stinkin' Locks!]: https://www.youtube.com/watch?v=VBnLW9mKMh4
[Java Memory Model Spec]: http://docs.oracle.com/javase/specs/jls/se8/html/jls-17.html
[atomic<> Weapons, 1 of 2]: https://channel9.msdn.com/Shows/Going+Deep/Cpp-and-Beyond-2012-Herb-Sutter-atomic-Weapons-1-of-2
[atomic<> Weapons, 2 of 2]: https://channel9.msdn.com/Shows/Going+Deep/Cpp-and-Beyond-2012-Herb-Sutter-atomic-Weapons-2-of-2
[JMM Pragmatics]: https://shipilev.net/blog/2014/jmm-pragmatics/
[Tenes Developer JMM Cookbook]: http://giltene.github.io/DeveloperJMMCookbook/
[Doug Leas JMM Cookbook]: http://g.oswego.edu/dl/jmm/cookbook.html
[Close encounters of JMM kind]: https://shipilev.net/blog/2016/close-encounters-of-jmm-kind/