package cz.richter.david.astroants

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication
open class Application {

}

fun main(args: Array<String>) {
    SpringApplicationBuilder(Application::class.java)
            .build(*args)
}