package cz.richter.david.astroants.parser

import cz.richter.david.astroants.model.MapSettings

interface MapSettingsParser {
    fun parse(x: Int, y: Int, string: String): MapSettings
}