# Minikube

## Recommended setup

Config
```bash
minikube config set cpus 8
minikube config set memory 12500
minikube config set disk-size 100GB
# minikube config set feature-gates=AllAlpha

minikube config set kubernetes-version latest
```

Addons
```bash
minikube addons enable metrics-server
minikube addons enable dashboard
minikube addons enable logviewer
minikube addons enable efk
```

Misc
```bash
minikube ssh
# in case u dont trust the metrics server or want better visibility
sudo apt-get update && sudo apt-get install htop
```

