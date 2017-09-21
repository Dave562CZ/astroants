package cz.richter.david.astroants.shortestpath

import cz.richter.david.astroants.model.Astroants
import cz.richter.david.astroants.model.MapLocation
import cz.richter.david.astroants.model.Direction
import cz.richter.david.astroants.model.Sugar

interface ShortestPathFinder {
    fun find(map: List<MapLocation>, astroants: Astroants, sugar: Sugar): List<Direction>
    companion object {
        fun convertCoordinatesToIndex(x: Int, y: Int, graphSize: Int) =
                x + y * graphSize
    }
}