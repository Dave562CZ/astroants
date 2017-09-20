package cz.richter.david.astroants.rest

import cz.richter.david.astroants.model.ResultPath
import cz.richter.david.astroants.model.ResultResponse
import cz.richter.david.astroants.model.World
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.io.IOException
import java.net.URI

/**
 * Implementation of [RestClient] using spring [RestTemplate] to call the REST endpoint and map input json to [World] object.
 * and in [putResult] function to map [ResultPath] to json and PUT to REST endpoint and get [ResultResponse]
 */
@Component
class SpringRestClient @Autowired constructor(private val sourceUri: URI, private val restTemplate: RestTemplate) : RestClient {

    override fun getSettings(): World = restTemplate.getForObject(sourceUri, World::class.java)

    override fun putResult(id: String, result: ResultPath): ResultResponse {
        val requestEntity = HttpEntity<ResultPath>(result)
        val responseEntity = restTemplate.exchange("$sourceUri/$id", HttpMethod.PUT, requestEntity, ResultResponse::class.java)
        if (responseEntity.statusCode != HttpStatus.OK)
            throw IOException("Invalid response received $responseEntity")
        return responseEntity.body
    }
}