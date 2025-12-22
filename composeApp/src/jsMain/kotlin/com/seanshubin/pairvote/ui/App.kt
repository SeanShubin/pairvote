package com.seanshubin.pairvote.ui

import androidx.compose.runtime.*
import com.seanshubin.pairvote.Greeting
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

@Composable
fun App() {
    Div(attrs = {
        style {
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
            alignItems(AlignItems.Center)
            width(100.percent)
            padding(20.px)
            backgroundColor(Color("#E6D5F5"))  // primaryContainer equivalent
            minHeight(100.vh)
        }
    }) {
        val greeting = remember { Greeting().greet() }
        P(attrs = {
            style {
                fontSize(18.px)
                fontWeight("500")
                color(Color("#1C1B1F"))
                marginBottom(8.px)
            }
        }) {
            Text(greeting)
        }
    }
}
