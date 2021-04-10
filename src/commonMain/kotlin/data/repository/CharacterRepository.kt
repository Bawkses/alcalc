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
        .map {
            val hullType = when (it.hullType) {
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
                // does not exist; is returned as destroyer
                "Munition Ship" -> HullType
                    .FrontLine.MunitionShip
                else -> if (it.name.value == "Kashino") HullType
                    .FrontLine.MunitionShip
                else
                    null
            }

            if (hullType != null) Character(
                wikiId = it.wikiId,
                wikiUrl = it.wikiUrl,
                hullName = it.name.value,
                hullClass = it.hullClass,
                hullType = hullType,
                health = it.stats.values.health,
                evasion = it.stats.values.evasion
            ) else
                null
        }
        .filterNotNull()
        .sortedWith { a, b ->
            // todo hullType ordering
            a.hullType.symbol
                .compareTo(b.hullType.symbol)
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
}