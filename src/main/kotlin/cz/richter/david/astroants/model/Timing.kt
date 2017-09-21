package cz.richter.david.astroants.model

class Timing {

    private var start: Long = 0
    private var worldObtained: Long = 0
    private var parsingDone: Long = 0
    private var shortestPathFound: Long = 0
    private var resultObtained: Long = 0

    fun start(): Timing {
        start = System.nanoTime()
        return this
    }

    fun worldObtained() {
        worldObtained = System.nanoTime()
    }

    fun parsingDone() {
        parsingDone = System.nanoTime()
    }

    fun shortestPathFound() {
        shortestPathFound = System.nanoTime()
    }

    fun resultObtained() {
        resultObtained = System.nanoTime()
    }

    fun worldObtainingTook() = toMs(worldObtained - start)

    fun parsingTook() = toMs(parsingDone - worldObtained)

    fun warmupParsingTook() = toMs(parsingDone - start)

    fun findingPathTook() = toMs(shortestPathFound - parsingDone)

    fun sendingResultTook() = toMs(resultObtained - shortestPathFound)

    fun totalTook() = toMs(resultObtained - start)

    fun warmupTook() = toMs(shortestPathFound - start)

    private fun toMs(nanos: Long) = nanos / 1000000
}