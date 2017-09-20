package cz.richter.david.astroants.model

import com.fasterxml.jackson.annotation.JsonProperty

data class InputMapSettings(
        @JsonProperty("areas")
        val areas: List<String>
)