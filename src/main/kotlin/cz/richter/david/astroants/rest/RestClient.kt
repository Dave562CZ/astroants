package cz.richter.david.astroants.rest

import cz.richter.david.astroants.model.ResultPath
import cz.richter.david.astroants.model.ResultResponse
import cz.richter.david.astroants.model.World

/**
 * Interface used for getting [World] from REST endpoint
 * and putting [ResultPath] to REST endpoint
 */
interface RestClient {
    fun getWorld(): World
    fun putResult(id: String, result: ResultPath): ResultResponse
}