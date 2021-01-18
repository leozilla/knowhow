# KVM

```bash
virsh net-list --all # show all virtual networks
virsh dominfo ${vm}
virsh domifaddr ${vm} # show if addresses
virsh event --list ${vm} # list domain events
virsh destroy ${vm} # same as pulling the power from a real machine
virsh undefine ${vm} # will delete the vm as soon as its powered off
```