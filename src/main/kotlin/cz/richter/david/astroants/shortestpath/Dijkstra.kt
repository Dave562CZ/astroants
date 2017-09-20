package cz.richter.david.astroants.shortestpath

import cz.richter.david.astroants.model.Astroants
import cz.richter.david.astroants.model.MapLocation
import cz.richter.david.astroants.model.Direction
import cz.richter.david.astroants.model.Sugar
import es.usc.citius.hipster.algorithm.Algorithm
import es.usc.citius.hipster.algorithm.Hipster
import es.usc.citius.hipster.graph.GraphSearchProblem
import cz.richter.david.astroants.shortestpath.ShortestPathFinder.Companion.convertCoordinatesToIndex
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class Dijkstra @Autowired constructor(
        @Qualifier("concurrentHipsterGraphCreator")
        private val hipsterGraphCreator: HipsterGraphCreator
) : ShortestPathFinder {

    override fun find(map: List<MapLocation>, astroants: Astroants, sugar: Sugar): List<Direction> {
        val graphSize = Math.sqrt(map.size.toDouble()).toInt()
        val graph = hipsterGraphCreator.constructHipsterGraph(map, graphSize)

        val problem = GraphSearchProblem
                .startingFrom(map[convertCoordinatesToIndex(astroants.x, astroants.y, graphSize)])
                .`in`(graph)
                .extractCostFromEdges { it.first.toDouble() }
                .build()

        val dijkstra = Hipster.createDijkstra(problem).search(map[convertCoordinatesToIndex(sugar.x, sugar.y, graphSize)])
        return Algorithm.recoverActionPath(dijkstra.goalNode).map { it.second }
    }


}

