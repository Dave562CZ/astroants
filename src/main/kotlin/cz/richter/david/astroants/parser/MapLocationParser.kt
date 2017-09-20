package cz.richter.david.astroants.parser

import cz.richter.david.astroants.model.MapLocation

interface MapLocationParser {
    fun parse(x: Int, y: Int, string: String): MapLocation
}