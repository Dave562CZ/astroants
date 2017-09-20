package cz.richter.david.astroants.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ResultResponse(
        @JsonProperty("valid")
        val valid: Boolean,
        @JsonProperty("inTime")
        val inTime: Boolean,
        @JsonProperty("message")
        val message: String
)

