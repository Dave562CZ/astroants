package cz.richter.david.astroants.parser

import cz.richter.david.astroants.model.MapSettings
import cz.richter.david.astroants.model.MapStrings
import cz.richter.david.astroants.model.Path
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.StreamSupport

@Component
class MapSettingsParserImpl : MapSettingsParser {

    override fun parse(x: Int, y: Int, string: String): MapSettings {
        val splitted = string.split("-")
        val weight = splitted[0].toInt()
        val paths = splitted[1].toCharArray().map { Path.fromChar(it) }
        return MapSettings(weight, x, y, paths)
    }


}

