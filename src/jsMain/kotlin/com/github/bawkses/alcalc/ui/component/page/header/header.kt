@file:OptIn(ExperimentalCoroutinesApi::class)

package com.github.bawkses.alcalc.ui.component.page.header

import dev.fritz2.dom.html.RenderContext
import dev.fritz2.styling.header
import kotlinx.coroutines.ExperimentalCoroutinesApi

fun RenderContext.pageHeader() {
    header({
        paddings {
            vertical { smaller }
            horizontal { normal }
        }
        width { full }
        background { color { primary.main } }
        color { primary.mainContrast }
    }, prefix = "header") {
        h1 { +"Azur Lane Calculator" }
    }
}
