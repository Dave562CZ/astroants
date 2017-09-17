package cz.richter.david.astroants.shortestpath

import cz.richter.david.astroants.model.Astroants
import cz.richter.david.astroants.model.MapSettings
import cz.richter.david.astroants.model.Path
import cz.richter.david.astroants.model.Sugar
import es.usc.citius.hipster.algorithm.Hipster
import es.usc.citius.hipster.graph.GraphSearchProblem
import es.usc.citius.hipster.graph.HashBasedHipsterDirectedGraph
import kotlin.system.measureNanoTime

class Dijkstra : ShortestPathFinder {
    override fun find(map: List<MapSettings>, astroants: Astroants, sugar: Sugar): List<MapSettings> {
        val graph = HashBasedHipsterDirectedGraph.create<MapSettings, Int>()
        val graphSize = Math.sqrt(map.size.toDouble()).toInt()
        val graphConstructionNanos = measureNanoTime {
            for (mapSettings in map) {
                for (path in mapSettings.paths) {
                    val destIndex = when (path) {
                        Path.UP -> convertCoordinatesToIndex(mapSettings.x, mapSettings.y - 1, graphSize)
                        Path.DOWN -> convertCoordinatesToIndex(mapSettings.x, mapSettings.y + 1, graphSize)
                        Path.LEFT -> convertCoordinatesToIndex(mapSettings.x - 1, mapSettings.y, graphSize)
                        Path.RIGHT -> convertCoordinatesToIndex(mapSettings.x + 1, mapSettings.y, graphSize)
                    }
                    val destSettings = map[destIndex]
                    graph.add(mapSettings)
                    graph.add(destSettings)
                    graph.connect(mapSettings, destSettings, destSettings.weight)
                }
            }
        }
        println(graphConstructionNanos)
        val problem = GraphSearchProblem
                .startingFrom(map[convertCoordinatesToIndex(astroants.x, astroants.y, graphSize)])
                .`in`(graph)
                .takeCostsFromEdges()
                .build()
        val dijkstra = Hipster.createDijkstra(problem).search(map[convertCoordinatesToIndex(sugar.x, sugar.y, graphSize)])
        println("dijkstra = ${dijkstra}")
        val astar = Hipster.createAStar(problem).search(map[convertCoordinatesToIndex(sugar.x, sugar.y, graphSize)])
        println("astar = ${astar}")
        val bellmanFord = Hipster.createBellmanFord(problem).search(map[convertCoordinatesToIndex(sugar.x, sugar.y, graphSize)])
        println("bellmanFord = ${bellmanFord}")


        return dijkstra.optimalPaths.first()
    }

    private fun convertCoordinatesToIndex(x: Int, y: Int, graphSize: Int) =
            x % graphSize + y * graphSize

}

private val MapSettings.coordinates: String
    get() = "${this.x}x${this.y}"

