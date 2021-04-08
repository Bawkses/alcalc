import data.entity.TEST_CHARACTER
import dev.fritz2.dom.html.render
import kotlinx.coroutines.flow.flowOf
import ui.component.table.tableCharacters

fun main() {
    console.log(game_data)

    render {
        tableCharacters(
            flowOf(
                listOf(
                    TEST_CHARACTER,
                    TEST_CHARACTER,
                    TEST_CHARACTER,
                    TEST_CHARACTER
                )
            )
        )
    }
}
