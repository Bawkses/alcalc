@file:OptIn(ExperimentalCoroutinesApi::class)

package com.github.bawkses.alcalc.ui.component.page.footer

import dev.fritz2.dom.html.RenderContext
import dev.fritz2.styling.footer
import kotlinx.coroutines.ExperimentalCoroutinesApi

fun RenderContext.pageFooter() {
    footer({
        paddings {
            vertical { smaller }
            horizontal { normal }
        }
        width { full }
        background { color { primary.mainContrast } }
        color { primary.main }
    }, prefix = "footer") {
        p { +"credits and stuff" } // todo credits
    }
}
