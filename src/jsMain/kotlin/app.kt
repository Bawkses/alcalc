import data.repository.CharacterRepository
import data.source.azurapi.AzurApiSourceImpl
import dev.fritz2.dom.html.render
import kotlinx.coroutines.flow.flow
import ui.component.table.tableCharacters

fun main() {
    val characterRepository = CharacterRepository(
        azurApiSource = AzurApiSourceImpl()
    )

    render {
        tableCharacters(
            flow {
                emit(
                    characterRepository
                        .retrieveCharacters()
                )
            }
        )
    }
}
