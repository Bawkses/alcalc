@file:OptIn(ExperimentalCoroutinesApi::class)

package com.github.bawkses.alcalc.ui.component.page.footer

import dev.fritz2.dom.html.RenderContext
import dev.fritz2.dom.html.TextElement
import dev.fritz2.styling.StyleClass
import dev.fritz2.styling.params.FlexParams
import dev.fritz2.styling.params.styled
import kotlinx.coroutines.ExperimentalCoroutinesApi

fun RenderContext.pageFooter() {
    footerStyled({
        paddings {
            vertical { smaller }
            horizontal { normal }
        }
        width { full }
        background { color { primary.baseContrast } }
        color { primary.base }
    }) {
        p { +"credits and stuff" } // todo credits
    }
}

private fun RenderContext.footerStyled(
    styling: FlexParams.() -> Unit = {},
    baseClass: StyleClass = StyleClass.None,
    id: String? = null,
    prefix: String = "footer",
    init: TextElement.() -> Unit
) {
    ::footer.styled(styling, baseClass, id, prefix) {
    }(init)
}
