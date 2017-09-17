package cz.richter.david.astroants.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Settings(
        @JsonProperty("astroants")
        val astroants: Astroants,
        @JsonProperty("id")
                    val id: String,
        @JsonProperty("map")
                    val map: MapStrings,
        @JsonProperty("sugar")
                    val sugar: Sugar,
        @JsonProperty("startedTimestamp")
                    val startedTimestamp: Long)

