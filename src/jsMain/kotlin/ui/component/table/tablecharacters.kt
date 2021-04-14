package ui.component.table

import data.entity.Character
import data.entity.EquipType
import data.entity.effectiveHealth
import dev.fritz2.dom.html.RenderContext
import kotlinx.coroutines.flow.Flow

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
                th { +"hp" }
                th { +"ev" }
                th { +"acc" }
                th { +"rel" }
                th { +"fire" }
                th { +"torp" }
                th { +"avi" }
                th { +"aa" }
                th { +"ab" }
                th { +"luck" }
                th { +"spd" }
                th { +"guns?" }
                th { +"torps?" }
                th { +"planes?" }
                th { +"antiair?" }
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
                        td { +"${character.health}" }
                        td { +"${character.evasion}" }
                        td { +"${character.accuracy}" }
                        td { +"${character.reload}" }
                        td { +"${character.firepower}" }
                        td { +"${character.torpedo}" }
                        td { +"${character.aviation}" }
                        td { +"${character.antiAir}" }
                        td { +"${character.antiSub}" }
                        td { +"${character.luck}" }
                        td { +"${character.speed}" }
                        td { +"${character.slots.canHold(EquipType.Gun::class)}" }
                        td { +"${character.slots.canHold(EquipType.Torpedo::class)}" }
                        td { +"${character.slots.canHold(EquipType.Plane::class)}" }
                        td { +"${character.slots.canHold(EquipType.AntiAir::class)}" }
                    }
                }
        }
    }
}
