package com.seanshubin.pairvote

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.seanshubin.pairvote.ui.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "pairvote",
    ) {
        App()
    }
}