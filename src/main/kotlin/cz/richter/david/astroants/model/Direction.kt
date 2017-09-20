package cz.richter.david.astroants.model

enum class Direction {
    LEFT, RIGHT, UP, DOWN;

    companion object {
        fun fromChar(string: Char): Direction {
            return when (string.toLowerCase()) {
                'l' -> LEFT
                'r' -> RIGHT
                'u' -> UP
                'd' -> DOWN
                else -> throw IllegalArgumentException("Wrong input string")
            }
        }
    }

    fun toResult(): Char {
        return name[0].toUpperCase()
    }
}