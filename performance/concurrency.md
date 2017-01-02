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

* Safety
* Liveness

# Laws

* Amdahls Law
* USL
* Littles Law
* Queing Theory

# Solutions

* Locking (Mutex, Semaphore)
* Lock free

# Java

* Final keyword semantics
* [Dealing with InterruptedException](https://www.ibm.com/developerworks/library/j-jtp05236/)

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
[JMM Pragmatics] | Shipilev | Java | 9 | Very (very) good but requires a lot of time to read and understand |
[Tenes Developer JMM Cookbook] | Tene | - | Very practical, boils done to one simple matrix |
[Doug Leas JMM Cookbook] | Lea | ? | Not fully read |
http://www.azulsystems.com/blog/cliff/2010-01-09-biased-locking | Cliff Click | Java | 9 | If you want to know how biased locking actual works |
http://joeduffyblog.com/2015/11/19/asynchronous-everything/ | Joe Duffy | .NET | 9 | Highly recommended |
http://joeduffyblog.com/2016/11/30/15-years-of-concurrency/ | Joe Duffy | .NET | 9 | |

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