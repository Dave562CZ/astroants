package cz.richter.david.astroants.shortestpath

import cz.richter.david.astroants.model.MapSettings
import cz.richter.david.astroants.model.Path
import es.usc.citius.hipster.graph.HashBasedHipsterDirectedGraph
import es.usc.citius.hipster.graph.HipsterDirectedGraph
import java.util.stream.Collectors
import java.util.stream.StreamSupport
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

class HipsterGraphCreator {

    fun constructHipsterGraph(map: List<MapSettings>, graphEdgeSize: Int): HipsterDirectedGraph<MapSettings, Pair<Int, Path>> {
        val graph =
//                ConcurrentHashBasedHipsterDirectedGraph<MapSettings, Pair<Int, Path>>()
                HashBasedHipsterDirectedGraph.create<MapSettings, Pair<Int, Path>>()
        val time = measureNanoTime {
//            StreamSupport.stream(map.withIndex().spliterator(), true)
//                    .forEach { (index, mapSettings) ->
//                        for (path in mapSettings.paths) {
//                            val destIndex = when (path) {
//                                Path.UP -> index - graphEdgeSize
//                                Path.DOWN -> index + graphEdgeSize
//                                Path.LEFT -> index - 1
//                                Path.RIGHT -> index + 1
//                            }
//                            val destSettings = map[destIndex]
//                            graph.add(mapSettings)
//                            graph.add(destSettings)
//                            graph.connect(mapSettings, destSettings, destSettings.weight.to(path))
//                        }
//                    }
            for ((index, mapSettings) in map.withIndex()) {
                for (path in mapSettings.paths) {
                    val destIndex = when (path) {
                        Path.UP -> index - graphEdgeSize
                        Path.DOWN -> index + graphEdgeSize
                        Path.LEFT -> index - 1
                        Path.RIGHT -> index + 1
                    }
                    val destSettings = map[destIndex]
                    graph.add(mapSettings)
                    graph.add(destSettings)
                    graph.connect(mapSettings, destSettings, destSettings.weight.to(path))
                }
            }
        }
        println(time / 1000000)
        return graph
    }
}