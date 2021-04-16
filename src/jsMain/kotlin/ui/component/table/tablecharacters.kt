package ui.component.table

import data.entity.Character
import data.entity.EquipType
import data.util.effectiveHealth
import data.util.statMultiplierFor
import dev.fritz2.dom.html.RenderContext
import kotlinx.coroutines.flow.Flow
import util.string.format

// todo use config
const val showBasicStats = false

fun RenderContext.tableCharacters(
    characters: Flow<List<Character>>
) {
    table {
        thead {
            tr {
                th { +"type" }
                th { +"faction" }
                th { +"class" }
                th { +"name" }
                th { +"eHP " }
                if (showBasicStats) th { +"hp" }
                if (showBasicStats) th { +"ev" }
                th { +"acc" }
                th { +"rel" }
                if (showBasicStats) th { +"fire" }
                th { +"xDD" }
                th { +"xCL" }
                th { +"xCA" }
                if (showBasicStats) th { +"torp" }
                th { +"xTorp" }
                if (showBasicStats) th { +"avi" }
                th { +"xFight" }
                th { +"xDivBo" }
                th { +"xTorBo" }
                if (showBasicStats) th { +"aa" }
                th { +"xAA" }
                if (showBasicStats) th { +"as" }
                th { +"luck" }
                th { +"spd" }
            }
        }
        tbody {
            characters
                .renderEach { character ->
                    tr {
                        td { +character.hullType.symbol }
                        td { +character.faction.name }
                        td { +character.hullClass }
                        td {
                            a {
                                href(character.wikiUrl)
                                +character.hullName
                            }
                            if (character.isRetrofitted)
                                small { +" (Retrofit)" }
                        }
                        td { +"${character.effectiveHealth()}" }
                        if (showBasicStats) td { +"${character.health}" }
                        if (showBasicStats) td { +"${character.evasion}" }
                        td { +"${character.accuracy}" }
                        td { +"${character.reload}" }
                        if (showBasicStats) td { +"${character.firepower}" }
                        td {
                            +character.statMultiplierFor(EquipType.Weapon.Gun.Destroyer)
                                .formatStat()
                        }
                        td {
                            +character.statMultiplierFor(EquipType.Weapon.Gun.LightCruiser)
                                .formatStat()
                        }
                        td {
                            +character.statMultiplierFor(EquipType.Weapon.Gun.HeavyCruiser)
                                .formatStat()
                        }
                        if (showBasicStats) td { +"${character.torpedo}" }
                        td {
                            val defTorpedo = character.statMultiplierFor(EquipType.Weapon.Torpedo.Default)
                            val subTorpedo = character.statMultiplierFor(EquipType.Weapon.Torpedo.Submarine)
                            +(defTorpedo + subTorpedo)
                                .formatStat()
                        }
                        if (showBasicStats) td { +"${character.aviation}" }
                        td {
                            +character.statMultiplierFor(EquipType.Weapon.Plane.Fighter)
                                .formatStat()
                        }
                        td {
                            +character.statMultiplierFor(EquipType.Weapon.Plane.DiveBomber)
                                .formatStat()
                        }
                        td {
                            +character.statMultiplierFor(EquipType.Weapon.Plane.TorpedoBomber)
                                .formatStat()
                        }
                        if (showBasicStats) td { +"${character.antiAir}" }
                        td {
                            +character.statMultiplierFor(EquipType.Weapon.AntiAir)
                                .formatStat()
                        }
                        if (showBasicStats) td { +"${character.antiSub}" }
                        td { +"${character.luck}" }
                        td { +"${character.speed}" }
                    }
                }
        }
    }
}

private fun Float.formatStat() = if (this > 0)
    "x%.2f".format(this)
else
    "-"
