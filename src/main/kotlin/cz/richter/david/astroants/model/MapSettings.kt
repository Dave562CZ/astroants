package cz.richter.david.astroants.model

import java.util.*

data class MapSettings(
        val weight: Int,
        val x: Int,
        val y: Int,
        val paths: EnumSet<Path>
)

