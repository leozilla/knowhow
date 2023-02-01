Design
===============

 * [The Law of Leaky Abstractions](https://www.joelonsoftware.com/2002/11/11/the-law-of-leaky-abstractions/)
 * [Domain Events vs Integration Events](https://blogs.msdn.microsoft.com/cesardelatorre/2017/02/07/domain-events-vs-integration-events-in-domain-driven-design-and-microservices-architectures/)
 * Correlation in message flows (inter-process aka distributed tracing and intra-process aka stack traces and thread handovers) basically beeing able to construct the whole call graph end-2-end
 * [Reactive Manifesto](https://www.reactivemanifesto.org/en)

# Strategies

Source: https://static.googleusercontent.com/media/research.google.com/en//people/jeff/stanford-295-talk.pdf

 * Back of the Envelope Calculations 
 * Design for growth
 * API first
 * Semantic versioning

# Things to consider from the start

 * Correlation is super important: pass context along with messages and method calls 
   - inter-process = distributed tracing
   - intra-process = stack traces (pass context with method calls) and thread handovers (pass context from one thread to another)