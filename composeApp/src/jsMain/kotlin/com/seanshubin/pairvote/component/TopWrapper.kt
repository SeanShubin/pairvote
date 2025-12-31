package com.seanshubin.pairvote.component

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div

@Composable
fun TopWrapper(content: @Composable () -> Unit) {
    Div(attrs = {
        style {
            property("padding", "16px")
        }
    }) {
        content()
    }
}
