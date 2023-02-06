# Kafka

## Producer

Be aware of possible messages delivered out-of-order if retries and max inflight requests are enabled.
Be aware of sync cluster metadata lookup.

## Consumer



## Performance

- Ensure kafka uses fast disks (low latency for writes, SSDs)
- Delete unused topics