package cz.richter.david.astroants.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ResultPath(
        @JsonProperty("path")
        val path: String
) {
    companion object {
        fun fromPaths(paths: List<Path>) {
            paths.asSequence()
                    .map { it.toResult() }
                    .joinToString("")
        }
    }
}