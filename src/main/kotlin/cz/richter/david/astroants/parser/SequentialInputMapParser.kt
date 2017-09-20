package cz.richter.david.astroants.parser

import cz.richter.david.astroants.indexedArrayListSpliterator
import cz.richter.david.astroants.model.InputMapSettings
import cz.richter.david.astroants.model.MapLocation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.Collectors
import java.util.stream.StreamSupport

/**
 * Implementation of [InputMapParser] which uses sequential stream processing to parse all [MapLocation]s from [String]s
 * Sequential stream usage unlike parallel does not need sorting after parsing [String]s
 */
@Component
class SequentialInputMapParser @Autowired constructor(private val mapLocationParser: MapLocationParser) : InputMapParser {

    override fun parse(inputMapSettings: InputMapSettings): List<MapLocation> {
        val gridSize = Math.sqrt(inputMapSettings.areas.size.toDouble()).toInt()
        return StreamSupport.stream(indexedArrayListSpliterator(inputMapSettings.areas.withIndex(), inputMapSettings.areas.size), false)
                .map { mapLocationParser.parse(it.index % gridSize, it.index / gridSize, it.value) }
                .collect(Collectors.toList())
    }

}