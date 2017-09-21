package cz.richter.david.astroants.shortestpath

import cz.richter.david.astroants.model.MapLocation
import cz.richter.david.astroants.model.Direction
import es.usc.citius.hipster.graph.HipsterDirectedGraph

/**
 * Interface for constructing [HipsterDirectedGraph] out of [List]<[MapLocation]>
 */
interface HipsterGraphCreator {

    /**
     * This function constructs [HipsterDirectedGraph] using [List]<[MapLocation]>
     *
     * IMPORTANT NOTE: for correct functionality of this function the [map] parameter must be sorted in order according to rows in grid
     * so every item index can be obtained using this function item.x + item.y * [graphEdgeSize]
     * and Math.sqrt(map.size) must be integer value i. e. the map must be square
     */
    fun constructHipsterGraph(map: List<MapLocation>, graphEdgeSize: Int): HipsterDirectedGraph<MapLocation, Pair<Int, Direction>>

    companion object {
        fun computeDestinationIndex(direction: Direction, index: Int, graphEdgeSize: Int): Int {
            return when (direction) {
                Direction.UP -> index - graphEdgeSize
                Direction.DOWN -> index + graphEdgeSize
                Direction.LEFT -> index - 1
                Direction.RIGHT -> index + 1
            }
        }
    }
}