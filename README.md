# Astroants

solution for http://quadientevents.cz/assets/quadient-soutez.pdf

this solution is implemented using [Hipster4J](http://www.hipster4j.org/) library for finding shortest path
and implements some classes for concurrent construction of Graphs

solution can be altered to use different combination of sequential/parallel approach
using different beans then default

Default solution provides best results on my computer when using sample input json located in test/resources/input.json file.
But it can be different on every computer

### Running

Solution can be run using main method in class cz.richter.david.astroants.Application#main
it runs the solution with Cold JVM, or JVM can be partially warmed up by passing `warmup` argument to main method
which performs basic warm up by running sample data through algorithm 10 times and then running the solution

Benchmark tests can be run using [JMH](http://openjdk.java.net/projects/code-tools/jmh/) framework (for example using [IntelliJ IDEA plugin](https://plugins.jetbrains.com/plugin/7529-jmh-plugin))

Additionally all tests in test/groovy can be run including integration tests in classes:
* cz.richter.david.astroants.shortestpath.DijkstraTest
* cz.richter.david.astroants.shortestpath.AStarTest

these tests are essentially same as program in run by Application but does not measure time.



### Benchmark results

| Benchmark                           | Mode | Cnt |   Score ± Error | Units |
|:------------------------------------|:----:| ---:| ---------------:| -----:|
| Benchmarks.aStarConcurrent          | avgt | 200 | 115,765 ± 2,673 | ms/op |
| Benchmarks.aStarSequential          | avgt | 200 | 203,498 ± 3,691 | ms/op |
| Benchmarks.concurrentGraphCreator   | avgt | 200 |  33,302 ± 0,934 | ms/op |
| Benchmarks.concurrentInputMapParser | avgt | 200 |   8,681 ± 0,275 | ms/op |
| Benchmarks.dijkstraConcurrent       | avgt | 200 | 116,223 ± 1,989 | ms/op |
| Benchmarks.dijkstraSequential       | avgt | 200 | 202,239 ± 3,281 | ms/op |
| Benchmarks.sequentialGraphCreator   | avgt | 200 | 125,642 ± 2,102 | ms/op |
| Benchmarks.sequentialInputMapParser | avgt | 200 |  17,166 ± 0,188 | ms/op |

The Benchmarks with results are located in src/test/java/cz/richter/david/astroants/Benchmarks.java class 
and whole output of benchmark run in test/resources/benchmark.out file
