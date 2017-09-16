package cz.richter.david.astroants.input

import cz.richter.david.astroants.model.Settings
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.net.URI

@Component
class SpringRestClient @Autowired constructor(private val sourceUri: URI, private val restTemplate: RestTemplate) : RestClient {
    override fun getSettings(): Settings = restTemplate.getForObject(sourceUri, Settings::class.java)
}