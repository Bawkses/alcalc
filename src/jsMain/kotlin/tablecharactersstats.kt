import data.entity.AbsCharacter
import dev.fritz2.dom.html.RenderContext
import kotlinx.coroutines.flow.Flow

fun RenderContext.tableCharactersStats(
    characters: Flow<List<AbsCharacter>>
) {
    table {
        thead {
            tr {
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
                        th { +character.name }
                        td { +"${character.health}" }
                        td { +"${character.evasion}" }
                        td { +"${character.effectiveHealth}" }
                    }
                }
        }
    }
}
