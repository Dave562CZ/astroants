package cz.richter.david.astroants.parser

import cz.richter.david.astroants.model.MapLocation
import cz.richter.david.astroants.model.Direction
import spock.lang.Specification
import spock.lang.Unroll

import static cz.richter.david.astroants.model.Direction.*

class MapLocationParserImplTest extends Specification {

    @Unroll("when #string is passed #expected is returned")
    def "strings are correctly parsed"() {
        expect:
        new MapLocationParserImpl().parse(0, 0, string) == expected
        where:
        string | expected
        "1-RD" | settings(1, RIGHT, DOWN)
        "10-D" | settings(10, DOWN)
        "2-"   | settings(2)
    }

    private static MapLocation settings(int weight, Direction... paths) {
        return new MapLocation(weight, 0, 0, Arrays.asList(paths))
    }
}