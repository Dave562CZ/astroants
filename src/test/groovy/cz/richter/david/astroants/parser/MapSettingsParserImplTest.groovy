package cz.richter.david.astroants.parser

import cz.richter.david.astroants.model.MapSettings
import cz.richter.david.astroants.model.Path
import spock.lang.Specification
import spock.lang.Unroll

import static cz.richter.david.astroants.model.Path.*

class MapSettingsParserImplTest extends Specification {

    @Unroll("when #string is passed #expected is returned")
    def "strings are correctly parsed"() {
        expect:
        new MapSettingsParserImpl().parse(0, 0, string) == expected
        where:
        string | expected
        "1-RD" | settings(1, RIGHT, DOWN)
        "10-D" | settings(10, DOWN)
        "2-"   | settings(2)
    }

    private static MapSettings settings(int weight, Path... paths) {
        if (paths.size() == 0) return new MapSettings(weight, 0, 0, EnumSet.noneOf(Path.class))
        return new MapSettings(weight, 0, 0, EnumSet.copyOf(Arrays.asList(paths)))
    }
}