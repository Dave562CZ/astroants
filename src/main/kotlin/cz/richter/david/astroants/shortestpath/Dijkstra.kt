package cz.richter.david.astroants.shortestpath

import cz.richter.david.astroants.model.Astroants
import cz.richter.david.astroants.model.MapSettings
import cz.richter.david.astroants.model.Path
import cz.richter.david.astroants.model.Sugar
import es.usc.citius.hipster.algorithm.Algorithm
import es.usc.citius.hipster.algorithm.Hipster
import es.usc.citius.hipster.graph.GraphSearchProblem
import es.usc.citius.hipster.graph.HipsterDirectedGraph

class Dijkstra(private val hipsterGraphCreator: HipsterGraphCreator) : ShortestPathFinder {

    override fun find(map: List<MapSettings>, astroants: Astroants, sugar: Sugar): List<Path> {
        val graphSize = Math.sqrt(map.size.toDouble()).toInt()
        val graph: HipsterDirectedGraph<MapSettings, Pair<Int, Path>> = hipsterGraphCreator.constructHipsterGraph(map, graphSize)

        val problem = GraphSearchProblem
                .startingFrom(map[convertCoordinatesToIndex(astroants.x, astroants.y, graphSize)])
                .`in`(graph)
                .extractCostFromEdges { it.first.toDouble() }
                .build()

        val dijkstra = Hipster.createDijkstra(problem).search(map[convertCoordinatesToIndex(sugar.x, sugar.y, graphSize)])
        println("dijkstra = $dijkstra")

        val astar = Hipster.createAStar(problem).search(map[convertCoordinatesToIndex(sugar.x, sugar.y, graphSize)])
        println("astar = $astar")

        return Algorithm.recoverActionPath(dijkstra.goalNode).map { it.second }
    }

    private fun convertCoordinatesToIndex(x: Int, y: Int, graphSize: Int) =
            x % graphSize + y * graphSize

}

