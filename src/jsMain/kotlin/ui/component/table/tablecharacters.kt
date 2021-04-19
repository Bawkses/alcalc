package ui.component.table

import data.entity.Character
import data.entity.EquipType
import data.util.effectiveHealth
import data.util.statMultiplierFor
import data.util.reloadMultiplier
import dev.fritz2.dom.html.RenderContext
import kotlinx.coroutines.flow.Flow
import ui.assets.img.faction.icon
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
                if (showBasicStats) th { +"acc" }
                if (showBasicStats) th { +"rel" }
                th { +"xRel" }
                if (showBasicStats) th { +"fire" }
                th { +"xDD" }
                th { +"xCL" }
                th { +"xCA" }
                th { +"xBB" }
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
                        td {
                            val icon = character.faction.icon
                            if (icon != null) img {
                                src(icon)
                                alt(character.faction.nameCapitalized)
                                inlineStyle("height: 1em") // todo
                            } else
                                +"-"
                        }
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
                        if (showBasicStats) td { +"${character.accuracy}" }
                        if (showBasicStats) td { +"${character.reload}" }
                        td {
                            +character.reloadMultiplier()
                                .formatStat()
                        }
                        if (showBasicStats) td { +"${character.firepower}" }
                        td {
                            +maxOf(
                                character.statMultiplierFor(EquipType.Weapon.Gun.Destroyer),
                                character.statMultiplierFor(EquipType.Weapon.Gun.Submarine)
                            ).formatStat()
                        }
                        td {
                            +character.statMultiplierFor(EquipType.Weapon.Gun.LightCruiser)
                                .formatStat()
                        }
                        td {
                            +maxOf(
                                character.statMultiplierFor(EquipType.Weapon.Gun.HeavyCruiser),
                                character.statMultiplierFor(EquipType.Weapon.Gun.LargeCruiser)
                            ).formatStat()
                        }
                        td {
                            +character.statMultiplierFor(EquipType.Weapon.Gun.Battleship)
                                .formatStat()
                        }
                        if (showBasicStats) td { +"${character.torpedo}" }
                        td {
                            +maxOf(
                                character.statMultiplierFor(EquipType.Weapon.Torpedo.Default),
                                character.statMultiplierFor(EquipType.Weapon.Torpedo.Submarine)
                            ).formatStat()
                        }
                        if (showBasicStats) td { +"${character.aviation}" }
                        td {
                            +character.statMultiplierFor(EquipType.Weapon.Plane.Fighter)
                                .formatStat()
                        }
                        td {
                            +maxOf(
                                character.statMultiplierFor(EquipType.Weapon.Plane.DiveBomber),
                                character.statMultiplierFor(EquipType.Weapon.Plane.Seaplane)
                            ).formatStat()
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
    "x%.3f".format(this)
else
    "-"
