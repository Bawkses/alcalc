@file:OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)

package com.github.bawkses.alcalc.ui.component.tabs

import dev.fritz2.components.SingleSelectionStore
import dev.fritz2.components.box
import dev.fritz2.components.lineUp
import dev.fritz2.dom.DomListener
import dev.fritz2.dom.html.RenderContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import org.w3c.dom.events.MouseEvent

fun <T> RenderContext.tabs(
    items: List<T>,
    initialItem: T? = null,
    renderTab: RenderContext.(item: T, index: Int, isSelected: Boolean) -> DomListener<MouseEvent, *>,
    renderContent: RenderContext.(value: T) -> Unit
) {
    val internalStore = SingleSelectionStore()
        .apply {
            toggle.invoke(
                if (initialItem != null) items
                    .indexOf(initialItem)
                else
                    0
            )
        }

    lineUp {
        spacing { none }
        items {
            for ((index, item) in items.withIndex()) internalStore.data
                .filterNotNull()
                .map { index == it }
                .render { isSelected ->
                    renderTab(this, item, index, isSelected)
                        .events.map { index } handledBy internalStore.toggle
                }
        }
    }

    box({
        minWidth { full }
    }) {
        internalStore.data
            .filterNotNull()
            .map { items[it] }
            .render { renderContent(it) }
    }
}
