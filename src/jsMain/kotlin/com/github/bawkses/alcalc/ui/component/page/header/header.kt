@file:OptIn(ExperimentalCoroutinesApi::class)

package com.github.bawkses.alcalc.ui.component.page.header

import dev.fritz2.dom.html.RenderContext
import dev.fritz2.dom.html.TextElement
import dev.fritz2.styling.StyleClass
import dev.fritz2.styling.params.FlexParams
import dev.fritz2.styling.params.styled
import kotlinx.coroutines.ExperimentalCoroutinesApi

fun RenderContext.pageHeader() {
    headerStyled({
        paddings {
            vertical { smaller }
            horizontal { normal }
        }
        width { full }
        background { color { primary.base } }
        color { primary.baseContrast }
    }) {
        h1 { +"Azur Lane Calculator" }
    }
}

private fun RenderContext.headerStyled(
    styling: FlexParams.() -> Unit = {},
    baseClass: StyleClass = StyleClass.None,
    id: String? = null,
    prefix: String = "header",
    init: TextElement.() -> Unit
) {
    ::header.styled(styling, baseClass, id, prefix) {
    }(init)
}
