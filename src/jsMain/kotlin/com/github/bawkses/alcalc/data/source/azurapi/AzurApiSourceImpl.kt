package com.github.bawkses.alcalc.data.source.azurapi

import com.github.bawkses.alcalc.data.source.azurapi.io.output.CharacterOutput
import dev.fritz2.remote.getBody
import dev.fritz2.remote.http
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class AzurApiSourceImpl : AzurApiSource {
    companion object {
        private val request = http(AzurApiSource.BASE_URL)
    }

    override suspend fun retrieveCharacters(): List<CharacterOutput> = request
        .get(AzurApiSource.PATH_CHARACTERS)
        .getBody()
        .let {
            Json {
                ignoreUnknownKeys = true
            }.decodeFromString(it)
        }
}
