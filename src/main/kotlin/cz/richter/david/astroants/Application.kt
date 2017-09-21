package cz.richter.david.astroants

import com.fasterxml.jackson.databind.ObjectMapper
import cz.richter.david.astroants.parser.ConcurrentInputMapParser
import cz.richter.david.astroants.parser.MapLocationParserImpl
import cz.richter.david.astroants.parser.SequentialInputMapParser
import cz.richter.david.astroants.rest.SpringRestClient
import cz.richter.david.astroants.shortestpath.AStar
import cz.richter.david.astroants.shortestpath.ConcurrentHipsterGraphCreator
import cz.richter.david.astroants.shortestpath.Dijkstra
import cz.richter.david.astroants.shortestpath.SequentialHipsterGraphCreator
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate
import java.net.URI


/**
 * Runs the program :D
 */
fun main(args: Array<String>) {
    @Suppress("UnnecessaryVariable")
    val arguments = args
//    val arguments = arrayOf(WARMUP_ARG)
    SpringApplicationBuilder(Application::class.java)
            .web(false)
            .run(*arguments)
}

private val WARMUP_ARG = "warmup"



/**
 * Class which start Spring boot application and solves the task of searching the shortest path for astroants
 * using provided endpoints with input settings and solution verification
 * By default system does not warm up JVM so results are not optimal.
 * For better results you can pass argument warmup which does a little warmup
 * by running the process of parsing input and 10 times before real run
 */
@SpringBootConfiguration
@EnableAutoConfiguration
open class Application() : CommandLineRunner {

    override fun run(vararg args: String?) {
        val runner = Runner(restClient(), concurrentInputMapParser(), aStarConcurrent(), consoleResultPrinter())
        if (args.size > 0 && args[0] == WARMUP_ARG)
            runner.warmup(objectMapper())
        runner.run()
    }

    @Bean
    open fun dijkstraSequential() = Dijkstra(sequentialGraphCreator())

    @Bean
    open fun dijkstraConcurrent() = Dijkstra(concurrentGraphCreator())

    @Bean
    open fun aStarSequential() = AStar(sequentialGraphCreator())

    @Bean
    open fun aStarConcurrent() = AStar(concurrentGraphCreator())

    @Bean
    open fun sequentialGraphCreator() = SequentialHipsterGraphCreator()

    @Bean
    open fun concurrentGraphCreator() = ConcurrentHipsterGraphCreator()

    @Bean
    open fun sequentialInputMapParser() = SequentialInputMapParser(mapLocationParser())

    @Bean
    open fun mapLocationParser() = MapLocationParserImpl()

    @Bean
    open fun concurrentInputMapParser() = ConcurrentInputMapParser(mapLocationParser())

    @Bean
    open fun endpointUri() = URI("http://tasks-rad.quadient.com:8080/task")

    @Bean
    open fun restTemplate() = RestTemplate()

    @Bean
    open fun restClient() = SpringRestClient(endpointUri(), restTemplate())

    @Bean
    open fun objectMapper() = ObjectMapper()

    @Bean
    open fun consoleResultPrinter() = ConsoleResultPrinter()
}

