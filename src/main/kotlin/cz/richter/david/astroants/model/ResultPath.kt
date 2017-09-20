package cz.richter.david.astroants.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ResultPath(
        @JsonProperty("path")
        val path: String
) {
    companion object {
        fun fromPaths(directions: List<Direction>): ResultPath {
            return ResultPath(
                    directions.asSequence()
                            .map { it.toResult() }
                            .joinToString("")
            )
        }
    }
}