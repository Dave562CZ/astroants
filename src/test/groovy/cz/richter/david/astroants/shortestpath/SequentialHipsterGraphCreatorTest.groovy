package cz.richter.david.astroants.shortestpath

import cz.richter.david.astroants.model.Direction
import cz.richter.david.astroants.model.MapLocation
import es.usc.citius.hipster.graph.HipsterDirectedGraph
import kotlin.Pair
import spock.lang.Specification

import static cz.richter.david.astroants.model.Direction.*

class SequentialHipsterGraphCreatorTest extends Specification {
    def "sample map test"() {
        given:
        def mapLocations = [
                new MapLocation(5, 0, 0, [RIGHT]),
                new MapLocation(1, 1, 0, [RIGHT, DOWN, LEFT]),
                new MapLocation(10, 2, 0, [DOWN, LEFT]),
                new MapLocation(2, 0, 1, [RIGHT, DOWN]),
                new MapLocation(1, 1, 1, [UP, LEFT]),
                new MapLocation(1, 2, 1, [UP, DOWN]),
                new MapLocation(2, 0, 2, [RIGHT, UP]),
                new MapLocation(1, 1, 2, [RIGHT, LEFT]),
                new MapLocation(2, 2, 2, [UP, LEFT])
        ]
        def creator = new SequentialHipsterGraphCreator()

        when:
        HipsterDirectedGraph<MapLocation, Pair<Integer, Direction>> result = creator.constructHipsterGraph(mapLocations, 3)

        then:
        result.vertices().toSet() == mapLocations.toSet()
        for (MapLocation location  : mapLocations) {
            result.outgoingEdgesOf(location).collect { it.edgeValue.second } == location.possibleDirections
        }
    }
}
