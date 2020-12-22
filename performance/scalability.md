Scalability
===========

 * http://berb.github.io/diploma-thesis/original/042_serverarch.html
 
# Problems

 * [c10K](http://www.kegel.com/c10k.html)
 * [c10M](http://highscalability.com/blog/2013/5/13/the-secret-to-10-million-concurrent-connections-the-kernel-i.html)
 * Multi threading vs multi core programming [1](http://blog.erratasec.com/2013/02/multi-core-scaling-its-not-multi.html#.WI3KSBvhBaQ)

# Caching

 * Read-Through: If cache does not contain X, forward request to backing store
 * Write-Through: If X is added to cache its also added to the backing store. 
   Client only gets the write response once X was also written to the backing store.
 * Write-Behind: Same as Write-Through but client gets the write response once X was written to the cache, 
   backing store is updated async.
 * Refresh-Ahead: Automatically and asynchronously reload/refresh any recently accessed cache entry from the cache loader before its expiration.

[Example Oracle Coherence](https://docs.oracle.com/cd/E15357_01/coh.360/e15723/cache_rtwtwbra.htm#COHDG5177)

## HTTP Caching

## Caching Objects

Types:
 * client-side caches 
 * local caches
 * distributed object caches
   * memcached
   * redis 