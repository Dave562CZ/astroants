package cz.richter.david.astroants.shortestpath

import cz.richter.david.astroants.input.SpringRestClient
import cz.richter.david.astroants.model.Astroants
import cz.richter.david.astroants.model.MapSettings
import cz.richter.david.astroants.model.Settings
import cz.richter.david.astroants.model.Sugar
import cz.richter.david.astroants.parser.MapSettingsParserImpl
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class DijkstraTest extends Specification {

    def "test"() {
        given:
        def strings = ["5-R", "1-RDL", "10-DL", "2-RD", "1-UL", "1-UD", "2-RU", "1-RL", "2-UL"]
        expect:
        new Dijkstra().find(getMap(strings), new Astroants(1, 0), new Sugar(2, 1))
    }

    def "integration"() {
        expect:
        def client = new SpringRestClient(new URI("http://tasks-rad.quadient.com:8080/task"), new RestTemplate())
        def settings = client.settings
        new Dijkstra().find(getMap(settings.map.areas), settings.astroants, settings.sugar)

    }

    List<MapSettings> getMap(List<String> strings) {
        def impl = new MapSettingsParserImpl()
        def result = []
        def sqrt = Math.sqrt(strings.size()).toInteger()
        for (int i = 0; i < strings.size(); i++) {
            result << impl.parse((i % sqrt).toInteger(), i.intdiv(sqrt).toInteger(), strings[i])
        }
        return result
    }
}
