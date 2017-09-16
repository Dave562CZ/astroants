package cz.richter.david.astroants.model

enum class Path {
    LEFT, RIGHT, UP, DOWN;

    companion object {
        fun fromChar(string: Char): Path {
           return when (string.toLowerCase()) {
               'l' -> LEFT
               'r' -> RIGHT
               'u' -> UP
               'd' -> DOWN
               else -> throw IllegalArgumentException("Wrong input string")
           }
        }
    }
}