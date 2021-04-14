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
                th { +"luck" }
                th { +"acc" }
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
                        td { +"${character.luck}" }
                        td { +"${character.accuracy}" }
                        td { +"${character.slots.canHold(EquipType.Gun::class)}" }
                        td { +"${character.slots.canHold(EquipType.Torpedo::class)}" }
                        td { +"${character.slots.canHold(EquipType.Plane::class)}" }
                        td { +"${character.slots.canHold(EquipType.AntiAir::class)}" }
                    }
                }
        }
    }
}
