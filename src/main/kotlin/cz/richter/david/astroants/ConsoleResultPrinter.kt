package cz.richter.david.astroants

import cz.richter.david.astroants.model.TimedResponse
import cz.richter.david.astroants.model.Timing
import org.springframework.stereotype.Component

@Component
class ConsoleResultPrinter {
    fun printWarmup(i: Int, timing: Timing) {
        if (i == 1) println("\n\n")
        println("Warming up: %2d: parsing: %3d ms, finding path: %3d ms, total: %3d ms."
                .format(i, timing.warmupParsingTook(), timing.findingPathTook(), timing.warmupTook()))
    }

    fun printTimingResult(warmedUp: Boolean, timedResponse: TimedResponse) {
        val timing = timedResponse.timing
        if (warmedUp) {
            println()
            println("Partially Warmed up JVM run:")
            println("------------------------------------")
        } else {
            println("\n\n")
            println("Cold JVM run: ")
            println("------------------------------------")
        }
        println("World obtaining took:       ${timing.worldObtainingTook().toString().padStart(5)} ms")
        println("Parsing input took:         ${timing.parsingTook().toString().padStart(5)} ms")
        println("Finding shortest path took: ${timing.findingPathTook().toString().padStart(5)} ms")
        println("Sending result took:        ${timing.sendingResultTook().toString().padStart(5)} ms")
        println("Total time:                 ${timing.totalTook().toString().padStart(5)} ms")

        println("\nResult response is: ${timedResponse.resultResponse}")

        println("\nFor results with JVM properly warmed up see: PROJECT_ROOT/test/java/cz/richter/david/astroants/Benchmarks class")
        println("Note: Benchmark class does not use calls to REST because DDOS protection does not allow it")
        println("Warning: Real Benchmarks can took very long, on my machine it takes almost 1 hour")
        println("\n\n")
    }
}