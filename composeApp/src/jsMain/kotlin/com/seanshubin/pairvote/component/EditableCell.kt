package com.seanshubin.pairvote.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.dom.Td
import org.w3c.dom.HTMLTableCellElement

@Composable
fun EditableCell(
    value: String,
    errorMessage: String?,
    onValueChange: (String) -> Unit
) {
    Td(attrs = {
        contentEditable(true)
        if (errorMessage != null) {
            classes("error-cell")  // Add a CSS class
            title(errorMessage)
        }
    }) {
        DisposableEffect(value) {
            val element = scopeElement as HTMLTableCellElement
            if (document.activeElement != element) {
                element.textContent = value
            }
            val cleanup = setupEditableCellListeners(element, onValueChange)
            onDispose { cleanup() }
        }
    }
}

private fun selectAllContent(element: HTMLTableCellElement) {
    window.setTimeout({
        val selection = document.asDynamic().getSelection()
        val range = document.createRange()
        range.selectNodeContents(element)
        selection?.removeAllRanges()
        selection?.addRange(range)
    }, 0)
}

private fun setupEditableCellListeners(
    element: HTMLTableCellElement,
    onValueChange: (String) -> Unit
): () -> Unit {
    val inputListener: (dynamic) -> Unit = {
        val newValue = element.textContent ?: ""
        onValueChange(newValue)
    }
    val focusListener: (dynamic) -> Unit = { selectAllContent(element) }
    val mouseUpListener: (dynamic) -> Unit = { selectAllContent(element) }
    val clickListener: (dynamic) -> Unit = { selectAllContent(element) }

    element.addEventListener("input", inputListener)
    element.addEventListener("focus", focusListener)
    element.addEventListener("mouseup", mouseUpListener)
    element.addEventListener("click", clickListener)

    // Return cleanup function
    return {
        element.removeEventListener("input", inputListener)
        element.removeEventListener("focus", focusListener)
        element.removeEventListener("mouseup", mouseUpListener)
        element.removeEventListener("click", clickListener)
    }
}
