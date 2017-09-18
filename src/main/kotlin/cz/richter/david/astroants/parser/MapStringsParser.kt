package cz.richter.david.astroants.parser

import cz.richter.david.astroants.model.MapSettings
import cz.richter.david.astroants.model.MapStrings

interface MapStringsParser {
    fun parse(mapStrings: MapStrings): List<MapSettings>
}