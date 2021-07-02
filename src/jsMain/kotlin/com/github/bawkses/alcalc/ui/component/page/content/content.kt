@file:OptIn(ExperimentalCoroutinesApi::class)

package com.github.bawkses.alcalc.ui.component.page.content

import com.github.bawkses.alcalc.data.entity.Character
import com.github.bawkses.alcalc.ui.component.table.tableCharacters
import com.github.bawkses.alcalc.ui.component.tabs.tabs
import dev.fritz2.components.box
import dev.fritz2.components.clickButton
import dev.fritz2.components.stackUp
import dev.fritz2.dom.html.RenderContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

private enum class TabItem {
    CHARACTERS,
    ITEMS,
    TEAM
}

fun RenderContext.pageContent(
    charaFlow: Flow<List<Character>>
) {
    val tabItems = TabItem
        .values()
        .toList()

    stackUp({
        flex { grow { "1" } }
        alignItems { center }
        paddings { horizontal { normal } }
    }) {
        spacing { none }
        items {
            tabs(
                items = tabItems,
                renderTab = { item, index, isSelected ->
                    clickButton({
                        radii {
                            topLeft {
                                when (index) {
                                    0 -> normal
                                    else -> none
                                }
                            }
                            topRight {
                                when (index) {
                                    tabItems.lastIndex -> normal
                                    else -> none
                                }
                            }
                            bottom { none }
                        }
                    }) {
                        text(
                            when (item) {
                                TabItem.CHARACTERS ->
                                    "Characters"
                                TabItem.ITEMS ->
                                    "Items"
                                TabItem.TEAM ->
                                    "Team"
                            }
                        )
                        variant {
                            if (isSelected)
                                solid
                            else
                                outline
                        }
                        type {
                            if (isSelected)
                                primary
                            else
                                secondary
                        }
                    }
                },
                renderContent = { tab ->
                    box({
                        paddings {
                            vertical { smaller }
                            horizontal { normal }
                        }
                        border {
                            width { thin }
                            color { primary.main }
                        }
                        background { color { gray100 } }
                        radius { normal }
                    }) {
                        when (tab) {
                            TabItem.CHARACTERS ->
                                tableCharacters(charaFlow)
                            TabItem.ITEMS,
                            TabItem.TEAM ->
                                p { +"TODO" } // TODO
                        }
                    }
                }
            )
        }
    }
}