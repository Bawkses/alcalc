package ui.component.table

import data.entity.Character
import dev.fritz2.dom.html.RenderContext
import kotlinx.coroutines.flow.Flow

fun RenderContext.tableCharacters(
    characters: Flow<List<Character>>
) {
    table {
        thead {
            tr {
                th { +"id" }
                th { +"type" }
                th { +"name" }
                th { +"hp" }
                th { +"ev" }
                th { +"effHp" }
            }
        }
        tbody {
            characters
                .renderEach { character ->
                    tr {
                        td {
                            a {
                                href(character.wikiUrl)
                                +character.wikiId
                            }
                        }
                        td { +character.hullType.symbol }
                        td {
                            +character.hullName
                            br {}
                            small { +"(${character.hullClass})" }
                        }
                        td { +"${character.health}" }
                        td { +"${character.evasion}" }
                        td { +"${character.effectiveHealth}" }
                    }
                }
        }
    }
}
