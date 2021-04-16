package data.repository

import data.entity.*
import data.source.azurapi.AzurApiSource
import data.source.azurapi.io.output.CharacterOutput
import org.lighthousegames.logging.logging

class CharacterRepository(
    private val azurApiSource: AzurApiSource
) {
    companion object {
        private val log = logging()
    }

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

                val charaSlots = output.slots
                    .toCharaSlots(isRetrofit = false)

                when {
                    hullType == null -> log
                        .w { "Unable to parse retrofit hullType ${output.hullType} for id ${output.wikiId}" }
                    charaSlots == null -> log
                        .w { "Unable to parse retrofit slots ${output.slots} for id ${output.wikiId}" }
                    else -> {
                        // Yield non-retrofitted character if it has no retrofit or its retrofit has a different hull type
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
                                slots = charaSlots,
                                health = output.stats.values.health,
                                evasion = output.stats.values.evasion,
                                accuracy = output.stats.values.accuracy,
                                firepower = output.stats.values.firepower,
                                torpedo = output.stats.values.torpedo,
                                aviation = output.stats.values.aviation,
                                antiAir = output.stats.values.antiAir,
                                antiSub = output.stats.values.antiSub,
                                reload = output.stats.values.reload,
                                luck = output.stats.values.luck,
                                speed = output.stats.values.speed
                            )
                        )

                        // Yield retrofitted character
                        if (output.hasRetrofit) {
                            val charaSlotsRetrofit = output.slots
                                .toCharaSlots(isRetrofit = true)

                            when {
                                hullTypeRetrofit == null -> log
                                    .w { "Unable to parse hullType ${output.hullType} for id ${output.wikiId}" }
                                charaSlotsRetrofit == null -> log
                                    .w { "Unable to parse slots ${output.slots} for id ${output.wikiId}" }
                                else -> yield(
                                    Character(
                                        wikiId = output.wikiRetrofitId!!,
                                        wikiUrl = output.wikiUrl,
                                        hullName = output.name.value,
                                        hullClass = output.hullClass,
                                        hullType = hullTypeRetrofit,
                                        faction = output.faction.toFaction(),
                                        isRetrofitted = true,
                                        slots = charaSlotsRetrofit,
                                        health = output.stats.valuesAfterRetrofit!!.health,
                                        evasion = output.stats.valuesAfterRetrofit.evasion,
                                        accuracy = output.stats.valuesAfterRetrofit.accuracy,
                                        firepower = output.stats.valuesAfterRetrofit.firepower,
                                        torpedo = output.stats.valuesAfterRetrofit.torpedo,
                                        aviation = output.stats.valuesAfterRetrofit.aviation,
                                        antiAir = output.stats.valuesAfterRetrofit.antiAir,
                                        antiSub = output.stats.valuesAfterRetrofit.antiSub,
                                        reload = output.stats.valuesAfterRetrofit.reload,
                                        luck = output.stats.valuesAfterRetrofit.luck,
                                        speed = output.stats.valuesAfterRetrofit.speed
                                    )
                                )
                            }
                        }
                    }
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

    private fun List<CharacterOutput.Slot>.toCharaSlots(
        isRetrofit: Boolean = false
    ): CharaSlots? = run {
        // Read outputSlots into array unless it cant be parsed.
        val arr = Array<CharaSlots.Value?>(5) { null }
        for (i in 0..4) {
            val outputSlot = getOrNull(i)
            if (outputSlot != null) {
                val cahHold = outputSlot.type
                    .toSlotTypes(isRetrofit = isRetrofit)

                if (cahHold == null)
                    return@run null
                else arr[i] = CharaSlots.Value(
                    canHold = cahHold,
                    efficiency = when {
                        !isRetrofit ->
                            outputSlot.efficiency
                        else ->
                            outputSlot.efficiencyAfterRetrofit!!
                    },
                    quantity = outputSlot.quantity
                )
            }
        }
        return@run arr
    }?.let { valueArr ->
        // Fill null entries with Aux slot, aux slots are not included.
        val lastNonNullIndex = valueArr.indexOfLast { it != null }
        if (lastNonNullIndex < valueArr.lastIndex) valueArr
            .fill(
                fromIndex = lastNonNullIndex + 1,
                element = CharaSlots.Value(
                    canHold = setOf(EquipType.Auxiliary),
                    efficiency = 0,
                    quantity = 1
                )
            )

        @Suppress("UNCHECKED_CAST")
        valueArr as Array<CharaSlots.Value>
    }?.let {
        CharaSlots(it)
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun String.toSlotTypes(
        isRetrofit: Boolean = false
    ): Set<EquipType>? =
        if (contains(" on retrofit)")) {
            val split = split("(", " on retrofit)", limit = 3)
            if (split.size == 3) {
                if (isRetrofit) split[1]
                    .toSlotTypes()
                else split[0]
                    .toSlotTypes()
            } else
                null
        } else buildSet {
            if (this@toSlotTypes.contains("DD")) add(
                EquipType.Weapon.Gun.Destroyer
            )

            if (this@toSlotTypes.contains("CL")) add(
                EquipType.Weapon.Gun.LightCruiser
            )

            if (this@toSlotTypes.contains("CA")) add(
                EquipType.Weapon.Gun.HeavyCruiser
            )

            if (this@toSlotTypes.contains("CB")) add(
                EquipType.Weapon.Gun.LargeCruiser
            )

            if (this@toSlotTypes.contains("BB")) add(
                EquipType.Weapon.Gun.Battleship
            )

            if (this@toSlotTypes.contains("Submarine-mounted 203mm Gun")) add(
                EquipType.Weapon.Gun.Submarine
            )

            if (this@toSlotTypes.contains("Submarine Torpedoes")) add(
                EquipType.Weapon.Torpedo.Submarine
            ) else if (this@toSlotTypes.contains("Torpedoes")) add(
                EquipType.Weapon.Torpedo.Default
            )

            if (this@toSlotTypes.contains("Fighters")) add(
                EquipType.Weapon.Plane.Fighter
            )

            if (this@toSlotTypes.contains("Dive Bombers")) add(
                EquipType.Weapon.Plane.DiveBomber
            )

            if (this@toSlotTypes.contains("Torpedo Bombers")) add(
                EquipType.Weapon.Plane.TorpedoBomber
            )

            if (this@toSlotTypes.contains("Seaplanes")) add(
                EquipType.Weapon.Plane.Seaplane
            )

            if (this@toSlotTypes.contains("Anti-Air Guns")) add(
                EquipType.Weapon.AntiAir
            )

            if (this@toSlotTypes.contains("Cargo")) add(
                EquipType.Cargo
            )

            if (this@toSlotTypes.contains("Auxiliaries")) add(
                EquipType.Auxiliary
            )
        }.ifEmpty { null }
}