package data.source.azurapi

import data.source.azurapi.io.output.CharacterOutput

interface AzurApiSource {
    companion object {
        protected const val BASE_URL = "https://raw.githubusercontent.com/AzurAPI/azurapi-js-setup/master"
        protected const val PATH_CHARACTERS = "ships.json"

    }

    suspend fun retrieveCharacters(): List<CharacterOutput>
}
