package cz.richter.david.astroants;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.richter.david.astroants.model.*;
import cz.richter.david.astroants.parser.MapLocationParserImpl;
import cz.richter.david.astroants.parser.ConcurrentInputMapParser;
import cz.richter.david.astroants.parser.SequentialInputMapParser;
import cz.richter.david.astroants.shortestpath.*;
import es.usc.citius.hipster.graph.HipsterDirectedGraph;
import kotlin.Pair;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 # Run complete. Total time: 00:57:36

 Benchmark                            Mode  Cnt    Score   Error  Units
 Benchmarks.aStarConcurrent           avgt  200  115,765 ± 2,673  ms/op
 Benchmarks.aStarSequential           avgt  200  203,498 ± 3,691  ms/op
 Benchmarks.concurrentGraphCreator    avgt  200   33,302 ± 0,934  ms/op
 Benchmarks.concurrentInputMapParser  avgt  200    8,681 ± 0,275  ms/op
 Benchmarks.dijkstraConcurrent        avgt  200  116,223 ± 1,989  ms/op
 Benchmarks.dijkstraSequential        avgt  200  202,239 ± 3,281  ms/op
 Benchmarks.sequentialGraphCreator    avgt  200  125,642 ± 2,102  ms/op
 Benchmarks.sequentialInputMapParser  avgt  200   17,166 ± 0,188  ms/op

 Note: written in Java because IntelliJ IDEA JMH plugin does not support any other language
 */
public class Benchmarks {

    @State(Scope.Thread)
    public static class MyState {
        HipsterGraphCreator sequentialCreator;
        HipsterGraphCreator concurrentCreator;
        ShortestPathFinder dijkstraSequential;
        ShortestPathFinder dijkstraConcurrent;
        ShortestPathFinder aStarSequential;
        ShortestPathFinder aStarConcurrent;
        List<MapLocation> mapSettings;
        Astroants astroants;
        Sugar sugar;
        int gridSize;
        ConcurrentInputMapParser concurrentInputMapParser;
        SequentialInputMapParser sequentialInputMapParser;
        World world;

        @Setup(Level.Trial)
        public void doSetup() throws IOException {
            sequentialCreator = new SequentialHipsterGraphCreator();
            concurrentCreator = new ConcurrentHipsterGraphCreator();
            dijkstraSequential = new Dijkstra(sequentialCreator);
            aStarSequential = new AStar(sequentialCreator);
            dijkstraConcurrent = new Dijkstra(concurrentCreator);
            aStarConcurrent = new AStar(concurrentCreator);
            concurrentInputMapParser = new ConcurrentInputMapParser(new MapLocationParserImpl());
            sequentialInputMapParser = new SequentialInputMapParser(new MapLocationParserImpl());
            ObjectMapper mapper = new ObjectMapper();
            try (InputStream stream = getClass().getResourceAsStream("/input.json")) {
                world = mapper.readValue(stream, World.class);
            }
            astroants = world.getAstroants();
            sugar = world.getSugar();
            mapSettings = concurrentInputMapParser.parse(world.getMap());
            gridSize = (int) Math.sqrt(mapSettings.size());
        }
    }

    /**
     Result "cz.richter.david.astroants.Benchmarks.sequentialGraphCreator":
     125,642 ±(99.9%) 2,102 ms/op [Average]
     (min, avg, max) = (115,606, 125,642, 170,237), stdev = 8,899
     CI (99.9%): [123,540, 127,744] (assumes normal distribution)
     */
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public HipsterDirectedGraph<MapLocation, Pair<Integer, Direction>> sequentialGraphCreator(MyState state) {
        return state.sequentialCreator.constructHipsterGraph(state.mapSettings, state.gridSize);
    }

    /**
     Result "cz.richter.david.astroants.Benchmarks.concurrentGraphCreator":
     33,302 ±(99.9%) 0,934 ms/op [Average]
     (min, avg, max) = (29,135, 33,302, 51,585), stdev = 3,955
     CI (99.9%): [32,368, 34,236] (assumes normal distribution)
     */
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public HipsterDirectedGraph<MapLocation, Pair<Integer, Direction>> concurrentGraphCreator(MyState state) {
        return state.concurrentCreator.constructHipsterGraph(state.mapSettings, state.gridSize);
    }

    /**
     Result "cz.richter.david.astroants.Benchmarks.dijkstraSequential":
     202,239 ±(99.9%) 3,281 ms/op [Average]
     (min, avg, max) = (184,939, 202,239, 263,239), stdev = 13,893
     CI (99.9%): [198,958, 205,520] (assumes normal distribution)
     */
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public List<Direction> dijkstraSequential(MyState state) {
        return state.dijkstraSequential.find(state.mapSettings, state.astroants, state.sugar);
    }

    /**
     Result "cz.richter.david.astroants.Benchmarks.aStarSequential":
     203,498 ±(99.9%) 3,691 ms/op [Average]
     (min, avg, max) = (185,484, 203,498, 276,027), stdev = 15,629
     CI (99.9%): [199,807, 207,190] (assumes normal distribution)
     */
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public List<Direction> aStarSequential(MyState state) {
        return state.aStarSequential.find(state.mapSettings, state.astroants, state.sugar);
    }

    /**
     Result "cz.richter.david.astroants.Benchmarks.dijkstraConcurrent":
     116,223 ±(99.9%) 1,989 ms/op [Average]
     (min, avg, max) = (104,341, 116,223, 150,994), stdev = 8,423
     CI (99.9%): [114,234, 118,212] (assumes normal distribution)
     */
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public List<Direction> dijkstraConcurrent(MyState state) {
        return state.dijkstraConcurrent.find(state.mapSettings, state.astroants, state.sugar);
    }

    /**
     Result "cz.richter.david.astroants.Benchmarks.aStarConcurrent":
     115,765 ±(99.9%) 2,673 ms/op [Average]
     (min, avg, max) = (104,227, 115,765, 168,011), stdev = 11,316
     CI (99.9%): [113,093, 118,438] (assumes normal distribution)
     */
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public List<Direction> aStarConcurrent(MyState state) {
        return state.aStarConcurrent.find(state.mapSettings, state.astroants, state.sugar);
    }

    /**
     Result "cz.richter.david.astroants.Benchmarks.sequentialInputMapParser":
     17,166 ±(99.9%) 0,188 ms/op [Average]
     (min, avg, max) = (16,304, 17,166, 21,290), stdev = 0,796
     CI (99.9%): [16,978, 17,354] (assumes normal distribution)
     */
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public List<MapLocation> sequentialInputMapParser(MyState state) {
        return state.sequentialInputMapParser.parse(state.world.getMap());
    }

    /**
     Result "cz.richter.david.astroants.Benchmarks.concurrentInputMapParser":
     8,681 ±(99.9%) 0,275 ms/op [Average]
     (min, avg, max) = (7,849, 8,681, 18,952), stdev = 1,162
     CI (99.9%): [8,406, 8,955] (assumes normal distribution)
     */
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public List<MapLocation> concurrentInputMapParser(MyState state) {
        return state.concurrentInputMapParser.parse(state.world.getMap());
    }

}
