package cz.richter.david.astroants.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Astroants(
        @JsonProperty("x")
        val x: Int,
        @JsonProperty("y")
        val y: Int
)