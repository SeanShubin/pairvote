package com.seanshubin.pairvote.component

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.*

@Composable
fun DeleteCell(
    isRemovable: Boolean,
    onRemove: () -> Unit
) {
    Td(attrs = { classes("delete-cell") }) {
        if (isRemovable) {
            Button(attrs = {
                onClick { onRemove() }
                classes("delete-button")
            }) {
                Text("×")
            }
        }
    }
}