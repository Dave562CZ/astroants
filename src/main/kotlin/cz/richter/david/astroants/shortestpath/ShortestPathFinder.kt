package cz.richter.david.astroants.shortestpath

import cz.richter.david.astroants.model.Astroants
import cz.richter.david.astroants.model.MapSettings
import cz.richter.david.astroants.model.Path
import cz.richter.david.astroants.model.Sugar

interface ShortestPathFinder {
    fun find(map: List<MapSettings>, astroants: Astroants, sugar: Sugar): List<Path>
}