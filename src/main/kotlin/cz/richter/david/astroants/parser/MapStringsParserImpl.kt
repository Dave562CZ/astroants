package cz.richter.david.astroants.parser

import cz.richter.david.astroants.model.MapSettings
import cz.richter.david.astroants.model.MapStrings
import java.util.stream.Collectors
import java.util.stream.StreamSupport

class MapStringsParserImpl(private val mapSettingsParser: MapSettingsParser) : MapStringsParser {
    override fun parse(mapStrings: MapStrings): List<MapSettings> {
        val gridSize = Math.sqrt(mapStrings.areas.size.toDouble()).toInt()
        return StreamSupport.stream(mapStrings.areas.withIndex().spliterator(), true)
                .map { mapSettingsParser.parse(it.index % gridSize, it.index / gridSize, it.value) }
                .collect(Collectors.toList())
    }
}