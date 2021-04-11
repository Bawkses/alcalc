package data.repository

import data.entity.Character
import data.entity.Faction
import data.entity.HullType
import data.source.azurapi.AzurApiSource
import kotlinx.serialization.SerialName

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
                            faction = output.faction.toFaction(),
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
                            faction = output.faction.toFaction(),
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
                .let { hullTypeComparison ->
                    if (hullTypeComparison == 0) a.faction
                        .compareTo(b.faction)
                        .let { factionComparison ->
                            if (factionComparison == 0) a.hullClass
                                .compareTo(b.hullClass)
                                .let { hullClassComparison ->
                                    if (hullClassComparison == 0) a.hullName
                                        .compareTo(b.hullName)
                                    else
                                        hullClassComparison
                                }
                            else
                                factionComparison
                        }
                    else
                        hullTypeComparison
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

    private fun String.toFaction() = when (this) {
        "Sakura Empire" -> Faction
            .SAKURA_EMPIRE
        "Eagle Union" -> Faction
            .EAGLE_UNION
        "Royal Navy" -> Faction
            .ROYAL_NAVY
        "Iron Blood" -> Faction
            .IRON_BLOOD
        "Dragon Empery" -> Faction
            .DRAGON_EMPERY
        "Northern Parliament" -> Faction
            .NORTHERN_PARLIAMENT
        "Iris Libre" -> Faction
            .IRIS_LIBRE
        "Vichya Dominion" -> Faction
            .VICHYA__DOMINION
        "Sardegna Empire" -> Faction
            .SARDEGNA_EMPIRE
        else -> Faction
            .OTHER
    }
}