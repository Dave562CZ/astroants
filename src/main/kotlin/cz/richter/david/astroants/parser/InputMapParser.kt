package cz.richter.david.astroants.parser

import cz.richter.david.astroants.model.MapLocation
import cz.richter.david.astroants.model.InputMapSettings

/**
 * Interface for parsing [InputMapSettings] List with [String] representation to [List]<[MapLocation]> with parsed properties
 */
interface InputMapParser {
    fun parse(inputMapSettings: InputMapSettings): List<MapLocation>
}