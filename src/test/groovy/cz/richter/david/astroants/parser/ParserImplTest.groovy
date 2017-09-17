package cz.richter.david.astroants.parser

import spock.lang.Specification

class ParserImplTest extends Specification {

    def "test"() {
        when:
        final list = ["5-R", "1-RDL", "10-DL", "2-RD", "1-UL", "1-UD", "2-RU", "1-RL", "2-UL"]
        list.eachWithIndex { item, index ->
            println "${index % 3} : ${index.intdiv(3)} - $item"
        }
        then:
        true

    }
}
