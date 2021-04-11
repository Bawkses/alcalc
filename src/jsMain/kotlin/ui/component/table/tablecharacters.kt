package ui.component.table

import data.entity.Character
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
                th { +"luck" }
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
                        td { +"${character.luck}" }
                    }
                }
        }
    }
}
