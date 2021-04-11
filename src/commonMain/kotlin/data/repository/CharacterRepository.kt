package data.repository

import data.entity.Character
import data.entity.HullType
import data.source.azurapi.AzurApiSource

class CharacterRepository(
    private val azurApiSource: AzurApiSource
) {
    suspend fun retrieveCharacters(): List<Character> = azurApiSource
        .retrieveCharacters()
        .asSequence()
        .flatMap { output ->
            sequence {
                val hullType = output.hullType
                    .toHullType()
                    ?.let {
                        // does not exist; is returned as destroyer
                        if (output.name.value == "Kashino") HullType
                            .FrontLine.MunitionShip
                        else
                            it
                    }

                val hullTypeRetrofit = output.hullTypeRetrofit
                    ?.toHullType()

                if (hullType != null) {
                    if (!output.hasRetrofit ||
                        output.hasRetrofit && hullType != hullTypeRetrofit
                    ) yield(
                        Character(
                            wikiId = output.wikiId,
                            wikiUrl = output.wikiUrl,
                            hullName = output.name.value,
                            hullClass = output.hullClass,
                            hullType = hullType,
                            isRetrofitted = false,
                            health = output.stats.values.health,
                            evasion = output.stats.values.evasion,
                            accuracy = output.stats.values.accuracy,
                            luck = output.stats.values.luck
                        )
                    )

                    if (output.hasRetrofit) yield(
                        Character(
                            wikiId = output.wikiRetrofitId!!,
                            wikiUrl = output.wikiUrl,
                            hullName = output.name.value,
                            hullClass = output.hullClass,
                            hullType = hullTypeRetrofit!!,
                            isRetrofitted = true,
                            health = output.stats.values.health,
                            evasion = output.stats.values.evasion,
                            accuracy = output.stats.values.accuracy,
                            luck = output.stats.values.luck
                        )
                    )
                }
            }
        }
        .sortedWith { a, b ->
            a.hullType
                .compareTo(b.hullType)
                .let { symbolComparison ->
                    // lazy but works; wiki ids are weird and not numerical
                    if (symbolComparison == 0) a.wikiId.length
                        .compareTo(b.wikiId.length)
                        .let { idLengthComparison ->
                            if (idLengthComparison == 0) a.wikiId
                                .compareTo(b.wikiId)
                            else
                                idLengthComparison
                        }
                    else
                        symbolComparison
                }
        }
        .toList()

    private fun String.toHullType() = when (this) {
        "Destroyer" -> HullType
            .FrontLine.Destroyer
        "Light Cruiser" -> HullType
            .FrontLine.LightCruiser
        "Heavy Cruiser" -> HullType
            .FrontLine.HeavyCruiser
        "Large Cruiser" -> HullType
            .FrontLine.LargeCruiser
        "Monitor" -> HullType
            .BackLine.Monitor
        "Battlecruiser" -> HullType
            .BackLine.Battlecruiser
        "Battleship" -> HullType
            .BackLine.Battleship
        "Aviation Battleship" -> HullType
            .BackLine.AviationBattleship
        "Light Carrier" -> HullType
            .BackLine.LightAircraftCarrier
        "Aircraft Carrier" -> HullType
            .BackLine.AircraftCarrier
        "Submarine" -> HullType
            .Submarine.Default
        "Submarine Carrier" -> HullType
            .Submarine.AircraftCarrier
        "Repair" -> HullType
            .FrontLine.Repair
        "Munition Ship" -> HullType
            .FrontLine.MunitionShip
        else ->
            null
    }
}