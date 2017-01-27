Systems Performance
===================

Mainly contains data from Brendan Greggs 

 * [Blog](http://www.brendangregg.com/)
 * Books
    + [System Performance Enterprise and Cloud](http://www.brendangregg.com/sysperfbook.html)
 * Podcast
    + [Se-Radio-Episode-255](http://www.se-radio.net/2015/04/se-radio-episode-225-brendan-gregg-on-systems-performance/)

Anything in the data path, software, hardware, is included, as it can affect performance.
For distributed systems you should have a diagram of your environment showing data paths.

# Methodologies

A methodology is a systematic way of approaching a performance problem.

## Workload characterization method

## USE method

## Anit methodologies

### Random change

### Blame someone else

# Tipps

**80/20 Rules**

 * For all problems not related to latency (outlyers): 80% of the problems are in the app 20% are in everything else 
 * For latency outlyers: 20% of the problems are in the app and 80% of the problems are in everything else
 
https://youtu.be/FJW8nGV4jxY?t=2436

 * CPUs: kernel can be gracefull, can take low prio work of CPU
* Disks: kernel has not so much control, work which is queued will usually need to be handled in that order, irelevant what the prio is. usually 60% utilization is already problematic

 * strace - super slow! be very carefull
 * tcpdump - overhead significant https://youtu.be/FJW8nGV4jxY?t=2691, is not scaleable
 * pidstat -t 1
 * pidstat -d 1 -> block device usage
 * lsof -iTCP -sTCP:ESTABLISHED -> file descriptors
 * sar -n TCP,ETCP,DEV 1
 * ss -map -> socket statistics
 * iptraf -> histograms packet size
 * iotop -> readable io stats
 * slabtop -> kernel slab allocator memory usage
 * pcstat -> page cache
 * tiptop -> IPC by process, very low level, IPC .. instructions per cycle
 * Flame graphs: https://www.youtube.com/watch?v=BHA65BqlqSk
 * ip route get ipAddr -> give ip address and it tells where it goes
 
# Tools

 * top
 * htop
 * atop
 * dstat
 * vmstat
 * iostat
 * netstat
 * ifstat

# Benchmarking
 
Results are often misleading: you benchmark A, but actually stress B and conclude you measured C
 
**Mistakes:**
 * testing the wrong target: FS disk cache instead of actual disk
 * choosing wrong target: testing disk instead of FS cache (actuall apps should hit FS cache more often than disk, does not reassemble real app behaviour)
 
 Active benchmarking - analyze them while running
 
 * lmbench -> cache, hardware analysis
 * fio --name=seqwrite --rw=write --bs=128k --size=5000m
 
static tools
 * check static state of a system witout any workload