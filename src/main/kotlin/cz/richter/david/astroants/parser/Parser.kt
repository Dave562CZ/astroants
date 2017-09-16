package cz.richter.david.astroants.parser

import cz.richter.david.astroants.model.MapSettings
import cz.richter.david.astroants.model.MapStrings

interface Parser {
    fun parse(x: Int, y: Int, string: String): MapSettings
}