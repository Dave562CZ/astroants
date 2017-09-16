package cz.richter.david.astroants.model

data class Settings(val astroants: Astroants,
               val id: String,
               val map: MapStrings,
               val sugar: Sugar,
               val startedTimestamp: Long)

