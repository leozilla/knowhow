Kubernetes
==========

| Command | Description |
|---------|-------------|
| ```$ kubectl diff -f service/```            | Show what changes would be applied to live objects |
| ```$ kubectl get componentstatuses```            | Show status of the Kuberentes components |
| ```$ kubectl get nodes     ```                   | List kubernetes nodes |       
| ```$ kubectl describe nodes MyNode```            | Describe the node with name MyNode |
| ```$ kubectl config set-context my-context --namespace=mystuff``` | Creates (but does not use) the context ```my-context``` which uses the namespace ```mystuff``` per default |
| ```$ kubectl config use-context my-context``` | Uses the context ```my-context``` |
| ```$ kubectl describe <resource-name> <obj-name>``` | Describes an object of a given resource type |
| ```$ kubectl get pods``` | Get all pods in the current namespace |
| ```$ kubectl get pods --show-labels``` | Get all pods in the current namespace and show their labels |
| ```$ kubectl get pods my-pod -o jsonpath --template={.status.podIP}``` | Gets only the pod IP from the pod ```my-pod``` |
| ```$ kubectl get pods --field-selector status.phase=Running``` | Get all pods where status.phase equals Running |
| ```$ kubectl apply -f obj.yaml``` | Creates the object(s) in the given yaml file |
| ```$ kubectl edit <resource-name> <obj-name>``` | Interactively edits the object of the given resource type |
| ```$ kubectl delete -f obj.yaml``` | Deletes the object(s) in the given yaml file |
| ```$ kubectl delete <resource-name> <obj-name>``` | Delets an object of the given resource type |
| ```$ kubectl label pods bar color=red``` | Adds the label ```color=red``` to the pod named ```bar``` |
| ```$ kubectl label pods bar -color``` | Removes the ```color``` label from the pod named ```bar``` |
| ```$ kubectl logs <pod-name>``` | Get The logs of a given pod |
| ```$ kubectl exec -it <pod-name> -- bash``` | Execute the ```bash``` command inside the first container of the pod. Use ```--container``` to specify the container |
| ```$ kubectl cp <pod-name>:/path/to/remote/file /path/to/local/file``` | Copy files to and from a container |
| ```$ kubectl port-forward kuard 8080:8080``` | Forward from local machine port 8080 to container port 8080 |
| ```$ kubectl delete rs/x --cascase=false``` | Delete ReplicaSet ```x``` without deleting the associated pods |

FQDN for namespace: `<service-name>.<namespace-name>.svc.cluster.local

# Concepts

## Labels

Labels are intended to be used to specify identifying attributes of objects that are meaningful and relevant to users, 
but do not directly imply semantics to the core system.

Example labels:

 * "release" : "stable", "release" : "canary"
 * "environment" : "dev", "environment" : "qa", "environment" : "production"
 * "tier" : "frontend", "tier" : "backend", "tier" : "cache"

Labels allow for efficient queries and watches and are ideal for use in UIs and CLIs. 
Non-identifying information should be recorded using annotations.

Unlike names and UIDs, labels do not provide uniqueness. In general, we expect many objects to carry the same label(s).

Example annotations:

 * Build, release, or image information like timestamps, release IDs, git branch, PR numbers, image hashes, and registry address. 
 * Pointers to logging, monitoring, analytics, or audit repositories.
 * Phone or pager numbers of persons responsible 

[Common Labels](https://kubernetes.io/docs/concepts/overview/working-with-objects/common-labels/)

## Annotations

Annotations are not used to identify and select objects. The metadata in an annotation can be small or large, structured or unstructured, 
and can include characters not permitted by labels.

## Nodes

If the Status of the Ready condition remains Unknown or False for longer than the pod-eviction-timeout (an argument passed to the kube-controller-manager), 
all the Pods on the node are scheduled for deletion by the node controller. 
The default eviction timeout duration is five minutes. 
In some cases when the node is unreachable, the API server is unable to communicate with the kubelet on the node. 
The decision to delete the pods cannot be communicated to the kubelet until communication with the API server is re-established. 
In the meantime, the pods that are scheduled for deletion may continue to run on the partitioned node.
The node controller checks the state of each node every --node-monitor-period seconds.

## Images

[Updating images](https://kubernetes.io/docs/concepts/containers/images/#updating-images)
The default pull policy is IfNotPresent which causes the kubelet to skip pulling an image if it already exists.

## Pods

Each Pod has a routable IP address assigned to it, not to the containers running within it. 
Having a shared network space for all containers means that the containers inside can communicate with one another
over the localhost address, a feature not present in traditional Docker networking.
Currently when a pod is created, its hostname is the Pod's `metadata.name` value.

### Termination of Pods

Typically, the container runtime sends a TERM signal to the main process in each container. 
Once the grace period has expired, the KILL signal is sent to any remaining processes, and the Pod is then deleted from the API Server.

### Pod Topology Spread Constraints

You can use topology spread constraints to control how Pods are spread across your cluster among failure-domains such as regions, zones, nodes, and other user-defined topology domains. 
This can help to achieve high availability as well as efficient resource utilization.

## Pod disruption budget

A PDB limits the number of Pods of a replicated application that are down simultaneously from voluntary disruptions.

## Networking

 * Any Pod can communicate with any other Pod without the use of network address translation (NAT). 
   To facilitate this, Kubernetes assigns each Pod an IP address that is routable within the cluster.
 * A node can communicate with a Pod without the use of NAT.
 * A Pod's awareness of its address is the same as how other resources see the address. 
   The host's address doesn't mask it.

The `kubelet` manages the hosts file for each container of the Pod to prevent Docker from modifying the file after the containers have already been started.

## Workload Resources

### Deployments

A Deployment provides declarative updates for Pods and ReplicaSets.

Deployment ensures that only a certain number of Pods are down while they are being updated. 
By default, it ensures that at least 75% of the desired number of Pods are up (25% max unavailable).

It is generally discouraged to make label selector updates and it is suggested to plan your selectors up front.

RollingUpdate Deployments support running multiple versions of an application at the same time.

You can monitor the progress for a Deployment by using `kubectl rollout status`.

### ReplicaSet

A ReplicaSet's purpose is to maintain a stable set of replica Pods running at any given time. 
Use Deployments instead of directly using ReplicaSets, unless you require custom update orchestration or don't require updates at all.

A ReplicaSet is linked to its Pods via the Pods' metadata.ownerReferences field, which specifies what resource the current object is owned by.

You can remove Pods from a ReplicaSet by changing their labels. This technique may be used to remove Pods from service for debugging, data recovery, etc. 
Pods that are removed in this way will be replaced automatically (assuming that the number of replicas is not also changed).

### StatefulSets

StatefulSet is the workload API object used to manage stateful applications.
Manages the deployment and scaling of a set of Pods, and provides guarantees about the ordering and uniqueness of these Pods.
Unlike a Deployment, a StatefulSet maintains a sticky identity for each of their Pods. 
These pods are created from the same spec, but are not interchangeable: each has a persistent identifier that it maintains across any rescheduling.

## Services, Load Balancing, and Networking

### Service

An abstract way to expose an application running on a set of Pods as a network service.
With Kubernetes you don't need to modify your application to use an unfamiliar service discovery mechanism. 
Kubernetes gives Pods their own IP addresses and a single DNS name for a set of Pods, and can load-balance across them.

Virtual IP: When we create a Service with type: ClusterIP, it gets a stable virtual IP address. 
However, this IP address does not correspond to any network interface and doesn’t exist in reality. 
It is the kube-proxy that runs on every node that picks this new Service and updates the iptables of the node with rules to catch the network packets destined for this virtual IP address
and replaces it with a selected Pod IP address. 
The rules in the iptables do not add ICMP rules, but only the protocol specified in the Service definition, such as TCP or UDP. 
As a consequence, it is not possible to ping the IP address of the Service as that operation uses ICMP protocol. 
However, it is of course possible to access the Service IP address via TCP (e.g., for an HTTP request).
To ensure each Service receives a unique IP, an internal allocator atomically updates a global allocation map in etcd prior to creating each Service.

#### Headless

Not handled by kube-proxy, dont provide load-balancing and a Cluster IP.
You can use a headless Service to interface with other service discovery mechanisms, without being tied to Kubernetes implementation.

#### Service Discovery Mechanisms

| Name         | Configuration                          | Client type | Summary                            |
|--------------|----------------------------------------|-------------|------------------------------------|
| ClusterIP    | type: ClusterIP, .spec.selector        | Internal    | The most common internal discovery mechanism |
| ManualIP     | type: ClusterIP, kind: Endpoints       | Internal    | External IP discovery                        |
| Manual FQDN  | type: ExternalName, .spec.externalName | Internal    | External FQDN discovery                      |
| Headless     | type: ClusterIP, .spec.clusterIP: None | Internal    | DNS-based discovery without a virtual IP     |
| NodePort     | type: NodePort                         | External    | Preferred for non-HTTP traffic               |
| LoadBalancer | type: LoadBalancer                     | External    | Requires supporting cloud infrastructure     |
| Ingress      | type: Ingress                          | External    | L7/HTTP-based smart routing mechanism        |

### Service Topology

Service Topology enables a service to route traffic based upon the Node topology of the cluster. 
For example, a service can specify that traffic be preferentially routed to endpoints that are on the same Node as the client, or in the same availability zone.

### DNS

[Specification](https://github.com/kubernetes/dns/blob/master/docs/specification.md)

#### Service

The default internal domain name for a cluster is `cluster.local`. 
When you create a Service, it assembles a subdomain of `namespace.svc.cluster.local` (where namespace is the namespace in which the service is running) 
and sets its name as the hostname. Example of a service: `nginx.default.svc.cluster.local`.

Assume a Service named `foo` in the Kubernetes namespace `bar`. 
A Pod running in namespace `bar` can look up this service by simply doing a DNS query for `foo`. 
A Pod running in namespace `quux` can look up this service by doing a DNS query for `foo.bar`.

`SRV` Records are created for named ports that are part of normal or Headless Services.

For each named port, the SRV record would have the form `_my-port-name._my-port-protocol.my-svc.my-namespace.svc.cluster-domain.example`.
For a regular service, this resolves to the port number and the domain name: `my-svc.my-namespace.svc.cluster-domain.example`.
For a headless service, this resolves to multiple answers, one for each pod that is backing the service, 
and contains the port number and the domain name of the pod of the form `auto-generated-name.my-svc.my-namespace.svc.cluster-domain.example`.

##### Normal (Not headless)

DNS `A/AAAA` records for a name of the form `my-svc.my-namespace.svc.cluster-domain.example` are created which resolve to the ClusterIP of the Service.

##### Headless

Same as for normal service, DNS `A/AAAA` records are created but those resolve to the set of IPs of the pods selected by the Service.
Clients are expected to consume the set or else use standard round-robin selection from the set.

With selectors: `Endpoints` objects are created and modifies DNS config to return records that point directly to the Pods backing the Service.

Without selectors: No `Endpoints` objects are created. However, the DNS system looks for `CNAME` or `A` records.

#### Pod

##### A/AAAA records

In general a pod has the following DNS resolution: `pod-ip-address.my-namespace.pod.cluster-domain.example` -> `172-17-0-3.default.pod.cluster.local`.

##### Pod DNS Policy

The kubelet passes DNS resolver information to each container with the `--cluster-dns=<dns-service-ip> flag.

Default DNS policy is `ClusterFirst`: 
Any DNS query that does not match the configured cluster domain suffix, such as "www.kubernetes.io", is forwarded to the upstream nameserver inherited from the node. 
Cluster administrators may have extra stub-domain and upstream DNS servers configured.

#### Debugging resolution

Use image `gcr.io/kubernetes-e2e-test-images/dnsutils:1.3` if container has no dns utils installed.

Check DNS resolver: `kubectl exec -it my_pod -- nslookup kubernetes.default`
Check local DNS config: `kubectl exec -it my_pod -- cat /etc/resolv.conf`
Check if DNS pod running: `kubectl get pods --namespace=kube-system -l k8s-app=kube-dns`
Check for errors in DNS pod: `kubectl logs --namespace=kube-system -l k8s-app=kube-dns`
Check if DNS service is up: `kubectl get svc --namespace=kube-system | grep kube-dns`
Are DNS endpoints exposed: `kubectl get endpoints kube-dns --namespace=kube-system`
Are DNS queries received/processed: Edit `coredns` config and add the `log` plugin, after 2 minutes check logs again.

[Known issues](https://kubernetes.io/docs/tasks/administer-cluster/dns-debugging-resolution/#known-issues)

#### Ingress

A `defaultBackend` is often configured in an Ingress controller to service any requests that do not match a path in the spec.

__Name based virtual hosting__: Routing based on the HTTP `Host` header.

Use _Readiness probes_ for health checking backends.

# Best Practices

It is not necessary to use multiple namespaces just to separate slightly different resources, such as different versions of the same software: 
use labels to distinguish resources within the same namespace.

It is generally discouraged to make label selector updates and it is suggested to plan your selectors up front.

[Common Labels](https://kubernetes.io/docs/concepts/overview/working-with-objects/common-labels/)

https://www.openservicebrokerapi.org/

# Configuration

## Best Practices

 * Configuration files should be stored in version control before being pushed to the cluster.
 * Put object descriptions in annotations, to allow better introspection.
 * Define and use labels that identify semantic attributes of your application or Deployment, such as
   `{ app: myapp, tier: frontend, phase: test, deployment: v3 }`
 * To make sure the container always uses the same version of the image, you can specify its digest
 * [Well-Known Labels, Annotations and Taints](https://kubernetes.io/docs/reference/kubernetes-api/labels-annotations-taints/)

## Config Maps

Mounted ConfigMaps are updated automatically.
ConfigMaps consumed as environment variables are not updated automatically and require a pod restart.

## Pod Priority and Preemption

Pods can have priority. Priority indicates the importance of a Pod relative to other Pods. 
If a Pod cannot be scheduled, the scheduler tries to preempt (evict) lower priority Pods to make scheduling of the pending Pod possible.

# Scheduling

In a cluster, Nodes that meet the scheduling requirements for a Pod are called feasible nodes.
The scheduler finds feasible Nodes for a Pod and then runs a set of functions to score the feasible Nodes and 
picks a Node with the highest score among the feasible ones to run the Pod.

_Node affinity_: a property of Pods that _attracts_ them to a set of nodes (either as a preference or a hard requirement).
_Taints_: the opposite of node affinity -- they allow a node to repel a set of pods.
_Tolerations:_ applied to pods, and allow (but do not require) the pods to schedule onto nodes with matching taints.

## Assigning Pods to Nodes

You can constrain a Pod to only be able to run on particular Node(s), or to prefer to run on particular nodes. 
There are several ways to do this, and the recommended approaches all use label selectors to make the selection.
`nodeSelector` is the simplest recommended form of node selection constraint.

Prefer [Well-Known Labels, Annotations and Taints](https://kubernetes.io/docs/reference/kubernetes-api/labels-annotations-taints/)

## Eviction Policy

The `kubelet` proactively monitors for and prevents total starvation of a compute resource. 
In those cases, the `kubelet` can reclaim the starved resource by failing one or more Pods.

# Storage

## Volumes



# Administration

## Cluster Networking

