package cz.richter.david.astroants.parser

import cz.richter.david.astroants.model.MapLocation
import cz.richter.david.astroants.model.Direction
import org.springframework.stereotype.Component

@Component
class MapLocationParserImpl : MapLocationParser {

    override fun parse(x: Int, y: Int, string: String): MapLocation {
        val splitted = string.split("-")
        val weight = splitted[0].toInt()
        val paths = splitted[1].toCharArray().map { Direction.fromChar(it) }
        return MapLocation(weight, x, y, paths)
    }


}

