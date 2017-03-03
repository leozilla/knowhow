Error Handling
===============

Named error handling although it includes fault tolerance, fault prevention, error handling, error mitigation and error recovery.

**Under construction - currently only brainstorming**

* [The Error Model](http://joeduffyblog.com/2016/02/07/the-error-model/): Thanks to [Domo](https://github.com/Domo42) for finding+sharing this
  + The way errors are communicated and dealt with is fundamental to any language
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
* [Rust Error Handling](https://doc.rust-lang.org/book/error-handling.html)

## What is an Error

Big challenge "define what an error actually is".
Most languages lump bugs and recoverable errors into the same category, and use the same facilities to deal with them.  
A null dereference or out-of-bounds array access is treated the same way as a network connectivity problem or parsing error.

 * On one hand, you had fail-fast – we called it abandonment – for programming bugs. 
 * And on the other hand, you had statically checked exceptions for recoverable errors.
 
## Ambitions and Learning

### Principles

 * **Usable**: It must be easy for developers to do the “right” thing in the face of error
 * **Concurrent**
 * **Diagnosable**: Debugging failures, either interactively or after-the-fact, needs to be productive and easy.
 * **Composable**
 
### Error Codes

error codes can have good reliability, but many programmers find them error prone to use; further, it’s easy to do the wrong thing – like forget to check one – which clearly violates the “pit of success”
caller must check it
constants representing the set of error codes
impressive track record going for them
functional languages use return codes disguised in monads and named things like Option<T>, Maybe<T>, or Error<T> coupled with a dataflow-style of programming and pattern matching, feel far more natural

baggage:

    * Performance can suffer.
    * Programming model usability can be poor.
    * The biggie: You can accidentally forget to check for errors
    
Midori: For what it’s worth, forgetting to use return values in our language was a compile time error. You had to explicitly ignore them.

Programming Model:

It’s common that many errors in a given function should share some recovery or remediation logic. C uses goto. In java you use finally blocks to encode this “before scope exits” pattern more directly.

Returns two values, one via return code the other via pointer parameter.
Return a structure that can contain either success or error value.

Go has multi valued returns.
Rust uses monadic error values. [Rust Error Handling](https://doc.rust-lang.org/book/error-handling.html)

### Exceptions

[Exception Handling: Issues and a Proposed Notation](https://www.cs.virginia.edu/~weimer/2006-615/reading/goodenough-exceptions.pdf)
Sadly, the most commonplace model – unchecked exceptions – is the worst you can do in this dimension.
For these reasons, most reliable systems use return codes instead of exceptions. They make it possible to locally reason about and decide how best to react to error conditions.
[The trouble with checked Exceptions](http://www.artima.com/intv/handcuffs.html)
[Exception Safety](https://en.wikipedia.org/wiki/Exception_safety)

For robust systems programs, don’t use exceptions.

**Unchecked Exceptions**

You throw and catch exceptions, without it being part of the type system or a function’s signature.
It’s difficult for anyone to reason about a program’s state at the time of the throw, the state changes that occur while that exception is propagated up the call stack – 
and possibly across threads in a concurrent program – and the resulting state by the time it gets caught or goes unhandled.

**Checked Exceptions**

Fail:

 * Exceptions are used to communicate unrecoverable bugs, like null dereferences, divide-by-zero
 * You don’t actually know everything that might be thrown, thanks to our little friend RuntimeException
 * versioning your interfaces is a pain in the ass
 
The resulting model seems to be the worst of both worlds. It doesn’t help you to write bulletproof code and it’s hard to use.

**Universal Problems with Exceptions**

 * throwing an exception is usually ridiculously expensive
 * Not having static type system information makes it hard to model control flow in the compiler, which leads to overly conservative optimizers.
 * encouraging too coarse a granularity of handling errors. huge catch blocks without carefully reacting to individual failures
 * control flow for throws is usually invisible. Silent control flow is just as bad as goto, or setjmp/longjmp, and makes writing reliable code very difficult.

![Summary](2016-02-07-the-error-model-1.png)

## Bugs Aren’t Recoverable Errors

 * Recoverable error: 

**Principles i established over the last 10 years**

 * Code "blocks" that mutate state should be as small as possible
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