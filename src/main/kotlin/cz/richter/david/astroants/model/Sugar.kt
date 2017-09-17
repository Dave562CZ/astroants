package cz.richter.david.astroants.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Sugar(
        @JsonProperty("x")
        val x: Int,
        @JsonProperty("y")
        val y: Int)
