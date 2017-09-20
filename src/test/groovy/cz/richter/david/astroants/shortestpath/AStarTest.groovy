package cz.richter.david.astroants.shortestpath

import cz.richter.david.astroants.model.Astroants
import cz.richter.david.astroants.model.InputMapSettings
import cz.richter.david.astroants.model.Direction
import cz.richter.david.astroants.model.ResultPath
import cz.richter.david.astroants.model.ResultResponse
import cz.richter.david.astroants.model.Sugar
import cz.richter.david.astroants.rest.SpringRestClient
import cz.richter.david.astroants.parser.MapLocationParserImpl
import cz.richter.david.astroants.parser.ConcurrentInputMapParser
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import java.util.stream.Collectors

import static cz.richter.david.astroants.model.Direction.*

class AStarTest extends Specification {

    def "sample map test"() {
        given:
        def mapStrings = new InputMapSettings(["5-R", "1-RDL", "10-DL", "2-RD", "1-UL", "1-UD", "2-RU", "1-RL", "2-UL"])
        def mapSettings = new ConcurrentInputMapParser(new MapLocationParserImpl()).parse(mapStrings)

        when:
        List<Direction> paths = new AStar(new ConcurrentHipsterGraphCreator()).find(mapSettings, new Astroants(1, 0), new Sugar(2, 1))

        then:
        paths == [DOWN, LEFT, DOWN, RIGHT, RIGHT, UP]
    }

    def "integration test with calling rest endpoints"() {
        given:
        sleep(2000)//give it 2 sec to avoid ddos attack protection
        def client = new SpringRestClient(new URI("http://tasks-rad.quadient.com:8080/task"), new RestTemplate())
        def settings = client.settings
        def mapSettings = new ConcurrentInputMapParser(new MapLocationParserImpl()).parse(settings.map)
        when:
        List<Direction> paths = new AStar(new ConcurrentHipsterGraphCreator()).find(mapSettings, settings.astroants, settings.sugar)
        def result = client.putResult(settings.id, getResultPath(paths))
        println result
        then:
        result == new ResultResponse(true, true, "You completed your task successfully and in time :-)")
    }

    private static ResultPath getResultPath(List<Direction> paths) {
        // this does not work for some reason in Groovy
        // groovy.lang.MissingMethodException:
        // No signature of method: static cz.richter.david.astroants.model.ResultPath$Companion.fromPaths() is
        // applicable for argument types: (java.util.ArrayList)
        //return ResultPath.Companion.fromPaths(possibleDirections)
        return new ResultPath(paths.stream().map { it.name()[0] }.collect(Collectors.joining("")))
    }
}
