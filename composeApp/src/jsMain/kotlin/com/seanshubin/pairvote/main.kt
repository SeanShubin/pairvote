package com.seanshubin.pairvote

import com.seanshubin.pairvote.ui.App
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable(rootElementId = "root") {
        App()
    }
}
