Kubernetes
==========

| Command | Description |
|---------|-------------|
| ```$ kubectl get componentstatuses```            | Show status of the Kuberentes components |
| ```$ kubectl get nodes     ```                   | List kubernetes nodes |       
| ```$ kubectl describe nodes MyNode```            | Describe the node with name MyNode |
| ```$ kubectl config set-context my-context --namespace=mystuff``` | Creates (but does not use) the context ```my-context``` which uses the namespace ```mystuff``` per default |
| ```$ kubectl config use-context my-context``` | Uses the context ```my-context``` |
| ```$ kubectl describe <resource-name> <obj-name>``` | Describes an object of a given resource type |
| ```$ kubectl get pods``` | Get all pods in the current namespace |
| ```$ kubectl get pods my-pod -o jsonpath --template={.status.podIP}``` | Gets only the pod IP from the pod ```my-pod``` |
| ```$ kubectl apply -f obj.yaml``` | Creates the object(s) in the given yaml file |
| ```$ kubectl edit <resource-name> <obj-name>``` | Interactively edits the object of the given resource type |
| ```$ kubectl delete -f obj.yaml``` | Deletes the object(s) in the given yaml file |
| ```$ kubectl delete <resource-name> <obj-name>``` | Delets an object of the given resource type |
| ```$ kubectl label pods bar color=red``` | Adds the label ```color=red``` to the pod named ```bar``` |
| ```$ kubectl label pods bar -color``` | Removes the ```color``` label from the pod named ```bar``` |
| ```$ kubectl logs <pod-name>``` | Get The logs of a given pod |
| ```$ kubectl exec -it <pod-name> -- bash``` | Execute the ```bash``` command inside the given pod |
| ```$ kubectl cp <pod-name>:/path/to/remote/file /path/to/local/file``` | Copy files to and from a container |
| ```$ kubectl port-forward kuard 8080:8080``` | Forward from local machine port 8080 to container port 8080 |