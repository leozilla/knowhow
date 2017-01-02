Microservice Architecture
=========================

**Under construction - currently mostly brainstorming**

Microservices are nothing new, [modularization](https://www.cs.umd.edu/class/spring2003/cmsc838p/Design/criteria.pdf) 
is considered a good design practice since the 70s.

Microservices are the next step after SOA in the evolution of software architecture. 
Without the next level of [tooling](#tooling) and performance this architecture would not be possible.

# Differences to SOA

The main benefits and differences to 'just' SOA:

## A Microservice must be independently deployable

Microservices and modern software development is all about fast feedback cycles (Agile).
If you cannot deploy a new version of a service independently of the rest of your system, you loose most benefits of this architecture.
I would even go as far as saying that you should not buy into this architecture if you cannot satisfy this point.

## A Microservice must define strict boundaries

Boundaries should usually not allow sharing of mutable state. Messaging is a very natural way of achieving this separation.
REST is also a form of messaging.
This would also be possible with modularization concepts of modern platforms (.NET, JVM) 
but it seems that the average developer is not able to use them correctly therefor we now have to cope with distribution :-(

## A Microservice must have well defined interfaces

# Considerations

## Deployment
## Versioning
## Monitoring
## Configuration
## Integration
## Performance
## Fault Tolerance
## Scalability
## Security

# Tooling

Name | Responsibilty | Description |
-----|---------------|-------------|
[Mesos](http://mesos.apache.org/) | Cluster Management, Resource Allocation | Datacenter OS |
[Kubernetes](http://kubernetes.io/) | Container Orchestration, Deployment, Scheduling, Application Management |  |
[Marathon](https://mesosphere.github.io/marathon/) | Container Orchestration | |
[Docker](https://www.docker.com/) | Virtualization/Container | THE containerization technology | 
[ELK](https://www.elastic.co/de/products) | Logging | |
Ansible | Infrastructure managementm, Configuration, Deployment | |
Chef | Infrastructure managementm, Configuration, Deployment | |
Puppet | Infrastructure managementm, Configuration, Deployment | |
SALT | Infrastructure managementm, Configuration, Deployment | |
Zipkin | Distributed Tracing | |
Prometheus | Monitoring | |
Lagom | | |
Spring Boot | | |
Spring Cloud | | |

# Anti Patterns

My own anti patterns:

 1. Making every service transactional is an anti pattern
 2. Making a service just because you can or dont now better, is an anti pattern