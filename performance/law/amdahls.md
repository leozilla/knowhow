Amdahls Law
===========

Models system scalability, accounting for serial components of workloads that do not scale in parallel (contention).

If I know the size of the part I optimize and the expected speedup I can infer the speedup for the whole system.
This law does not only apply to optimizations via parallelization. See [Performance Methodology I 30:00](https://youtu.be/Zw_z7pjis7k?t=1779)

```
C( N) = N/ 1 + α( N - 1)
```

N = number of added resources
α = Amdahl parameter, degree of linear work (0 ... 1)

## Application

 * Collect data for N either by observation of an existing system or experimentally using micro-benchmarking or load generators
 * Perform regression analysis to determine (α); this may be done using statistical software, such as gnuplot or R
 * The collected data points can be plotted along with the model function to predict scaling and reveal differences between the data and the model

## Examples


