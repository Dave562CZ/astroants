package cz.richter.david.astroants

import com.fasterxml.jackson.databind.ObjectMapper
import cz.richter.david.astroants.model.ResultPath
import cz.richter.david.astroants.model.TimedResponse
import cz.richter.david.astroants.model.Timing
import cz.richter.david.astroants.model.World
import cz.richter.david.astroants.parser.InputMapParser
import cz.richter.david.astroants.rest.RestClient
import cz.richter.david.astroants.shortestpath.ShortestPathFinder
import java.io.ObjectOutputStream

class Runner(
        private val restClient: RestClient,
        private val inputMapParser: InputMapParser,
        private val shortestPathFinder: ShortestPathFinder,
        private val consoleResultPrinter: ConsoleResultPrinter
) {
    private var warmedUp = false
    private var nullOutputStream = NullOutputStream()

    fun warmup(objectMapper: ObjectMapper) {
        warmedUp = true
        val world = objectMapper.readValue(javaClass.getResourceAsStream("/input.json"), World::class.java)
        for (i in 1..10) {
            val timing = Timing().start()
            val mapSettings = inputMapParser.parse(world.map)
            timing.parsingDone()
            val shortestPath = shortestPathFinder.find(mapSettings, world.astroants, world.sugar)
            timing.shortestPathFound()
            nullOutputStream.writeUnshared(shortestPath)//trying to prevent JVM from optimizing this loop out...
            consoleResultPrinter.printWarmup(i, timing)
        }
    }

    fun run() {
        val timing = Timing()
        timing.start()
        val world = restClient.getWorld()
        timing.worldObtained()
        val mapSettings = inputMapParser.parse(world.map)
        timing.parsingDone()
        val shortestPath = shortestPathFinder.find(mapSettings, world.astroants, world.sugar)
        timing.shortestPathFound()
        val response = restClient.putResult(world.id, ResultPath.fromPaths(shortestPath))
        timing.resultObtained()
        consoleResultPrinter.printTimingResult(warmedUp, TimedResponse(timing, response))
    }
}

private class NullOutputStream : ObjectOutputStream() {
    override fun writeUnshared(obj: Any?) {
    }
}