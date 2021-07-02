@file:OptIn(ExperimentalCoroutinesApi::class)

package com.github.bawkses.alcalc

import com.github.bawkses.alcalc.data.repository.CharacterRepository
import com.github.bawkses.alcalc.data.source.azurapi.AzurApiSourceImpl
import com.github.bawkses.alcalc.ui.component.page.content.pageContent
import com.github.bawkses.alcalc.ui.component.page.footer.pageFooter
import com.github.bawkses.alcalc.ui.component.page.header.pageHeader
import dev.fritz2.components.stackUp
import dev.fritz2.dom.html.render
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow

fun main() {
    val characterRepository = CharacterRepository(
        azurApiSource = AzurApiSourceImpl()
    )

    val charaFlow = flow {
        characterRepository
            .retrieveCharacters()
            .sortedBy { it.faction }
            .sortedWith(compareBy(
                { it.hullType },
                { it.faction },
                { it.hullClass },
                { it.hullName }
            ))
            .also { emit(it) }
    }

    render {
        stackUp({
            alignItems { center }
            minHeight { "100vh" }
        }) {
            spacing { normal }
            items {
                pageHeader()
                pageContent(charaFlow)
                pageFooter()
            }
        }
    }
}
