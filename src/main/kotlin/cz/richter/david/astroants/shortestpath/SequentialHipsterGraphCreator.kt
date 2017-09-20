package cz.richter.david.astroants.shortestpath

import cz.richter.david.astroants.indexedArrayListSpliterator
import cz.richter.david.astroants.model.MapLocation
import cz.richter.david.astroants.model.Direction
import es.usc.citius.hipster.graph.HashBasedHipsterDirectedGraph
import es.usc.citius.hipster.graph.HipsterDirectedGraph
import cz.richter.david.astroants.shortestpath.HipsterGraphCreator.Companion.computeDestinationIndex
import org.springframework.stereotype.Component
import java.util.stream.StreamSupport

@Component
class SequentialHipsterGraphCreator : HipsterGraphCreator {

    override fun constructHipsterGraph(map: List<MapLocation>, graphEdgeSize: Int): HipsterDirectedGraph<MapLocation, Pair<Int, Direction>> {
        val graph = HashBasedHipsterDirectedGraph.create<MapLocation, Pair<Int, Direction>>()
        StreamSupport.stream(indexedArrayListSpliterator(map.withIndex(), map.size), false)
                .forEach { (index, mapSettings) ->
                    for (path in mapSettings.possibleDirections) {
                        val destIndex = computeDestinationIndex(path, index, graphEdgeSize)
                        val destSettings = map[destIndex]
                        graph.add(mapSettings)
                        graph.add(destSettings)
                        graph.connect(mapSettings, destSettings, destSettings.terrainDifficulty.to(path))
                    }
                }
        return graph
    }
}