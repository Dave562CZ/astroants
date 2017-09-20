package cz.richter.david.astroants.parser

import cz.richter.david.astroants.indexedArrayListSpliterator
import cz.richter.david.astroants.model.MapLocation
import cz.richter.david.astroants.model.InputMapSettings
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.Collectors
import java.util.stream.StreamSupport

/**
 * Implementation of [InputMapParser] which uses parallel stream processing to parse all [MapLocation]s from [String]s
 * Usage of parallel stream allows for better scalability and bigger inputs to be parsed faster and more efficiently
 */
@Component
class ConcurrentInputMapParser @Autowired constructor(private val mapLocationParser: MapLocationParser) : InputMapParser {
    override fun parse(inputMapSettings: InputMapSettings): List<MapLocation> {
        val gridSize = Math.sqrt(inputMapSettings.areas.size.toDouble()).toInt()
        return StreamSupport.stream(indexedArrayListSpliterator(inputMapSettings.areas.withIndex(), inputMapSettings.areas.size), true)
                .map { mapLocationParser.parse(it.index % gridSize, it.index / gridSize, it.value) }
                .sorted(Comparator.comparing { location: MapLocation ->  location.x % gridSize + location.y * gridSize  })
                .collect(Collectors.toList())
    }

}