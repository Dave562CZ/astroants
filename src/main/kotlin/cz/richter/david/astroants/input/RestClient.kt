package cz.richter.david.astroants.input

import cz.richter.david.astroants.model.ResultPath
import cz.richter.david.astroants.model.Settings

interface RestClient {
    fun getSettings(): Settings
    fun putResult(id: String, result: ResultPath)
}