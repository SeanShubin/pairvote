package com.seanshubin.pairvote.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.dom.Td
import org.w3c.dom.HTMLTableCellElement

@Composable
fun EditableCell(
    value: String,
    onValueChange: (String) -> Unit
) {
    Td(attrs = {
        contentEditable(true)
    }) {
        DisposableEffect(value) {
            val element = scopeElement as HTMLTableCellElement
            if (document.activeElement != element) {
                element.textContent = value
            }

            val selectAllContent: () -> Unit = {
                window.setTimeout({
                    val selection = document.asDynamic().getSelection()
                    val range = document.createRange()
                    range.selectNodeContents(element)
                    selection?.removeAllRanges()
                    selection?.addRange(range)
                }, 0)
            }

            val inputListener: (dynamic) -> Unit = { event ->
                val newValue = element.textContent ?: ""
                onValueChange(newValue)
            }
            val focusListener: (dynamic) -> Unit = { event -> selectAllContent() }
            val mouseUpListener: (dynamic) -> Unit = { event -> selectAllContent() }
            val clickListener: (dynamic) -> Unit = { event -> selectAllContent() }

            element.addEventListener("input", inputListener)
            element.addEventListener("focus", focusListener)
            element.addEventListener("mouseup", mouseUpListener)
            element.addEventListener("click", clickListener)

            onDispose {
                element.removeEventListener("input", inputListener)
                element.removeEventListener("focus", focusListener)
                element.removeEventListener("mouseup", mouseUpListener)
                element.removeEventListener("click", clickListener)
            }
        }
    }
}