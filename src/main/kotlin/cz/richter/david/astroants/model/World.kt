package cz.richter.david.astroants.model

import com.fasterxml.jackson.annotation.JsonProperty

data class World(
        @JsonProperty("id")
        val id: String,
        @JsonProperty("map")
        val map: InputMapSettings,
        @JsonProperty("astroants")
        val astroants: Astroants,
        @JsonProperty("sugar")
        val sugar: Sugar,
        @JsonProperty("startedTimestamp")
        val startedTimestamp: Long
)

