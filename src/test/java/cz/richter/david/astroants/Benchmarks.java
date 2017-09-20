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
 # Run complete. Total time: 00:57:04

 Benchmark                            Mode  Cnt    Score   Error  Units
 Benchmarks.aStarConcurrent           avgt  200  114,804 ± 0,870  ms/op
 Benchmarks.aStarSequential           avgt  200  194,227 ± 2,069  ms/op
 Benchmarks.concurrentGraphCreator    avgt  200   32,150 ± 0,354  ms/op
 Benchmarks.dijkstraConcurrent        avgt  200  122,661 ± 1,369  ms/op
 Benchmarks.dijkstraSequential        avgt  200  209,201 ± 2,550  ms/op
 Benchmarks.sequentialGraphCreator    avgt  200  128,193 ± 1,819  ms/op
 Benchmarks.concurrentInputMapParser  avgt  200    9,294 ± 0,065  ms/op
 Benchmarks.sequentialInputMapParser  avgt  200   17,552 ± 0,067  ms/op
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
     128,193 ±(99.9%) 1,819 ms/op [Average]
     (min, avg, max) = (117,949, 128,193, 155,633), stdev = 7,704
     CI (99.9%): [126,374, 130,013] (assumes normal distribution)
     */
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public HipsterDirectedGraph<MapLocation, Pair<Integer, Direction>> sequentialGraphCreator(MyState state) {
        return state.sequentialCreator.constructHipsterGraph(state.mapSettings, state.gridSize);
    }

    /**
     Result "cz.richter.david.astroants.Benchmarks.concurrentGraphCreator":
     32,150 ±(99.9%) 0,354 ms/op [Average]
     (min, avg, max) = (29,760, 32,150, 37,414), stdev = 1,497
     CI (99.9%): [31,796, 32,504] (assumes normal distribution)
     */
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public HipsterDirectedGraph<MapLocation, Pair<Integer, Direction>> concurrentGraphCreator(MyState state) {
        return state.concurrentCreator.constructHipsterGraph(state.mapSettings, state.gridSize);
    }

    /**
     Result "cz.richter.david.astroants.Benchmarks.dijkstraSequential":
     209,201 ±(99.9%) 2,550 ms/op [Average]
     (min, avg, max) = (194,478, 209,201, 242,666), stdev = 10,796
     CI (99.9%): [206,652, 211,751] (assumes normal distribution)
     */
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public List<Direction> dijkstraSequential(MyState state) {
        return state.dijkstraSequential.find(state.mapSettings, state.astroants, state.sugar);
    }

    /**
     Result "cz.richter.david.astroants.Benchmarks.aStarSequential":
     194,227 ±(99.9%) 2,069 ms/op [Average]
     (min, avg, max) = (177,668, 194,227, 220,101), stdev = 8,762
     CI (99.9%): [192,158, 196,297] (assumes normal distribution)
     */
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public List<Direction> aStarSequential(MyState state) {
        return state.aStarSequential.find(state.mapSettings, state.astroants, state.sugar);
    }

    /**
     Result "cz.richter.david.astroants.Benchmarks.dijkstraConcurrent":
     122,661 ±(99.9%) 1,369 ms/op [Average]
     (min, avg, max) = (107,681, 122,661, 141,795), stdev = 5,796
     CI (99.9%): [121,293, 124,030] (assumes normal distribution)
     */
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public List<Direction> dijkstraConcurrent(MyState state) {
        return state.dijkstraConcurrent.find(state.mapSettings, state.astroants, state.sugar);
    }

    /**
     Result "cz.richter.david.astroants.Benchmarks.aStarConcurrent":
     114,804 ±(99.9%) 0,870 ms/op [Average]
     (min, avg, max) = (107,392, 114,804, 123,658), stdev = 3,683
     CI (99.9%): [113,934, 115,673] (assumes normal distribution)
     */
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public List<Direction> aStarConcurrent(MyState state) {
        return state.aStarConcurrent.find(state.mapSettings, state.astroants, state.sugar);
    }

    /**
     Result "cz.richter.david.astroants.Benchmarks.sequentialInputMapParser":
     17,552 ±(99.9%) 0,067 ms/op [Average]
     (min, avg, max) = (17,009, 17,552, 18,319), stdev = 0,283
     CI (99.9%): [17,485, 17,619] (assumes normal distribution)
     */
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public List<MapLocation> sequentialInputMapParser(MyState state) {
        return state.sequentialInputMapParser.parse(state.world.getMap());
    }

    /**
     Result "cz.richter.david.astroants.Benchmarks.concurrentInputMapParser":
     8,670 ±(99.9%) 0,098 ms/op [Average]
     (min, avg, max) = (7,864, 8,670, 10,462), stdev = 0,415
     CI (99.9%): [8,572, 8,768] (assumes normal distribution)
     */
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public List<MapLocation> concurrentInputMapParser(MyState state) {
        return state.concurrentInputMapParser.parse(state.world.getMap());
    }

}
