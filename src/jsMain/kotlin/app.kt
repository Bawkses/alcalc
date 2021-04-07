import data.entity.TEST_CHARACTER
import dev.fritz2.dom.html.render
import kotlinx.coroutines.flow.flowOf

fun main() {
    render {
        tableCharactersStats(
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
