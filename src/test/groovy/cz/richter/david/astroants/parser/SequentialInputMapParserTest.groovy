package cz.richter.david.astroants.parser

import cz.richter.david.astroants.model.InputMapSettings
import cz.richter.david.astroants.model.MapLocation
import spock.lang.Specification
import spock.lang.Unroll

import static cz.richter.david.astroants.model.Direction.*

class SequentialInputMapParserTest extends Specification {

    @SuppressWarnings("GroovyAssignabilityCheck")
    @Unroll("test correct computation of indexes for #input")
    def "test correct parsing of map strings"() {
        given:
        def mapSettingsParser = Mock(MapLocationParser.class)
        def mapStringsParserImpl = new SequentialInputMapParser(mapSettingsParser)

        when:
        mapStringsParserImpl.parse(new InputMapSettings(input))

        then:
        for (int i = 0; i < input.size(); i++) {
            int xCoord = location[i].x
            int yCoord = location[i].y
            String str = input[i]
            1 * mapSettingsParser.parse(xCoord, yCoord, str) >>> location
        }

        where:
        input << [
                ["1-RD"],
                ["1-R", "2-D", "3-U", "4-L"]
        ]
        location << [
                [new MapLocation(1, 0, 0, [RIGHT, DOWN])],
                [
                        new MapLocation(1, 0, 0, [RIGHT]),
                        new MapLocation(2, 1, 0, [DOWN]),
                        new MapLocation(3, 0, 1, [UP]),
                        new MapLocation(4, 1, 1, [LEFT])
                ]
        ]
    }

    def "test correct sorting of resulting list"() {
        given:
        def mapStringsParserImpl = new SequentialInputMapParser(new MapLocationParserImpl())
        InputMapSettings input = new InputMapSettings(["1-R", "2-D", "3-U", "4-L"])
        when:
        List<MapLocation> result = mapStringsParserImpl.parse(input)
        then:
        result == [
                new MapLocation(1, 0, 0, [RIGHT]),
                new MapLocation(2, 1, 0, [DOWN]),
                new MapLocation(3, 0, 1, [UP]),
                new MapLocation(4, 1, 1, [LEFT]),
        ]
    }
}
