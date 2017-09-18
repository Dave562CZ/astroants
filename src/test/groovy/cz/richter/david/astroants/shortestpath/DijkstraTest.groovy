package cz.richter.david.astroants.shortestpath

import com.fasterxml.jackson.databind.ObjectMapper
import cz.richter.david.astroants.input.SpringRestClient
import cz.richter.david.astroants.model.Astroants
import cz.richter.david.astroants.model.MapSettings
import cz.richter.david.astroants.model.Settings
import cz.richter.david.astroants.model.Sugar
import cz.richter.david.astroants.parser.MapSettingsParserImpl
import cz.richter.david.astroants.parser.MapStringsParserImpl
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class DijkstraTest extends Specification {

    def "test"() {
        given:
//        def strings = ["5-R", "1-RDL", "10-DL", "2-RD", "1-UL", "1-UD", "2-RU", "1-RL", "2-UL"]
        def stream =getClass().getResourceAsStream("/input.json")
        ObjectMapper mapper = new ObjectMapper()
        def value = stream.withCloseable {
            mapper.readValue(stream, Settings.class)
        }
        expect:
//        println new Dijkstra(new HipsterGraphCreator()).find(getMap(strings), new Astroants(1, 0), new Sugar(2, 1))
        println new Dijkstra(new HipsterGraphCreator()).find(new MapStringsParserImpl(new MapSettingsParserImpl()).parse(value.map), value.astroants, value.sugar)
    }

    def "integration"() {
        expect:
        def client = new SpringRestClient(new URI("http://tasks-rad.quadient.com:8080/task"), new RestTemplate())
        def settings = client.settings
        new Dijkstra(new HipsterGraphCreator()).find(getMap(settings.map.areas), settings.astroants, settings.sugar)

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
