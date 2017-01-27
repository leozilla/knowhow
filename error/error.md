Error Handling
===============

Named error handling although it includes fault tolerance, fault prevention, error handling, error mitigation and error recovery.

**Under construction - currently only brainstorming**

* [The Error Model](http://joeduffyblog.com/2016/02/07/the-error-model/): Thanks to [Domo](https://github.com/Domo42) for finding+sharing this 
* [On Erlang, State and Crashes aka Error kernel](http://jlouisramblings.blogspot.co.at/2010/11/on-erlang-state-and-crashes.html)
* Exceptions
  + [Vexing exceptions](https://blogs.msdn.microsoft.com/ericlippert/2008/09/10/vexing-exceptions/)
  + Dont use exceptions for flow control
* Null - just dont use it  
* [Considerations for Building Reliable Systems](http://bravenewgeek.com/take-it-to-the-limit-considerations-for-building-reliable-systems/): Unbounded anything is an anti pattern
* Erlang Supervisors, Links, Monitors
* Akka
* Crash only software
* Fail Fast
* Failure Modes
* Availability
* Reliability
* Failure masking
* Fault, Error, Failure
* Stability Patterns
* http://highscalability.com/blog/2013/2/27/42-monster-problems-that-attack-as-loads-increase.html

**Principles i established over the last 10 years**

 * Code blocks that mutate state should be as small as possible
 * Dont use *null*
 * Make side effects explicit, if possible via dedicated types: Future, Optional, ResultObject
 * Use the type system
 * Dont work with threads, use higher level constructs like Queues, Actors, Event Loops, Futures etc
 * Define a threading model and know all your threads by name and what objects they are touching
 * Dont let other unexperienced people work with threads
 * Dont block threads if possible
 * Prefer immutability
 * Prefer result objects over checked exceptions in java
 * Dont use exceptions for flow control
 * Use exceptions for programming errors (NPE, index out of bounds)
 * Define exception boundaries
 * Design for restartable or rollback/rollforward

# Papers, Articels etc

Name | Author | Rating | Description |
-----|--------|--------|-------------|
[Making reliable distributed systems in the presence of sodware errors] | Joe Armstrong | 9 | Very good read |
[My presentation about fault tolerance](building-reliable-software.pptx) | David Leonhartsberger | 7 | My first presentation |

# Books

Name | Author | Rating | Description |
-----|--------|--------|-------------|
Release it! | Nygard | 9 | Classic one about software FT |
Patterns for Fault Tolerant Software | Hammer | 9 | Favorite ones about software FT |

# Videos



[Making reliable distributed systems in the presence of sodware errors]: http://erlang.org/download/armstrong_thesis_2003.pdf