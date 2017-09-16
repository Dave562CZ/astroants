package cz.richter.david.astroants.input

import cz.richter.david.astroants.model.Settings

interface RestClient {
    fun getSettings(): Settings
}