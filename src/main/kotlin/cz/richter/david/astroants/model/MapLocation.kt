package cz.richter.david.astroants.model

data class MapLocation(
        val terrainDifficulty: Int,
        val x: Int,
        val y: Int,
        val possibleDirections: Collection<Direction>
)

