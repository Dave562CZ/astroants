package cz.richter.david.astroants

import cz.richter.david.astroants.model.ResultPath
import cz.richter.david.astroants.parser.InputMapParser
import cz.richter.david.astroants.rest.RestClient
import cz.richter.david.astroants.shortestpath.ShortestPathFinder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate
import java.net.URI

@SpringBootApplication
open class Application : CommandLineRunner {

    @Autowired
    private lateinit var restClient: RestClient
    @Autowired
    @Qualifier("concurrentInputMapParser")
    private lateinit var inputMapParser: InputMapParser
    @Autowired
    @Qualifier("AStar")
    private lateinit var shortestPathFinder: ShortestPathFinder

    @Bean
    open fun endpointUri(): URI = URI("http://tasks-rad.quadient.com:8080/task")

    @Bean
    open fun restTemplate(): RestTemplate = RestTemplate()

    override fun run(vararg args: String?) {
        val settings = restClient.getSettings()
        val mapSettings = inputMapParser.parse(settings.map)
        val shortestPath = shortestPathFinder.find(mapSettings, settings.astroants, settings.sugar)
        println("\n\n")
        println(restClient.putResult(settings.id, ResultPath.fromPaths(shortestPath)))
        println("\n\n")
    }

}

fun main(args: Array<String>) {
    SpringApplicationBuilder(Application::class.java)
            .web(false)
            .run(*args)
}