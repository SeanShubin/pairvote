package com.seanshubin.pairvote.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.seanshubin.pairvote.platform.BrowserDocumentProvider
import com.seanshubin.pairvote.platform.BrowserWindowProvider
import com.seanshubin.pairvote.platform.DocumentProvider
import com.seanshubin.pairvote.platform.WindowProvider
import org.jetbrains.compose.web.dom.Td
import org.w3c.dom.HTMLTableCellElement

@Composable
fun EditableCell(
    value: String,
    errorMessage: String?,
    onValueChange: (String) -> Unit,
    documentProvider: DocumentProvider = BrowserDocumentProvider,
    windowProvider: WindowProvider = BrowserWindowProvider
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
            if (documentProvider.activeElement != element) {
                element.textContent = value
            }
            val cleanup = setupEditableCellListeners(element, onValueChange, documentProvider, windowProvider)
            onDispose { cleanup() }
        }
    }
}

private fun selectAllContent(
    element: HTMLTableCellElement,
    documentProvider: DocumentProvider,
    windowProvider: WindowProvider
) {
    windowProvider.setTimeout({
        val selection = documentProvider.getSelection()
        val range = documentProvider.createRange()
        range.selectNodeContents(element)
        selection?.removeAllRanges()
        selection?.addRange(range)
    }, 0)
}

private fun setupEditableCellListeners(
    element: HTMLTableCellElement,
    onValueChange: (String) -> Unit,
    documentProvider: DocumentProvider,
    windowProvider: WindowProvider
): () -> Unit {
    val inputListener: (dynamic) -> Unit = {
        val newValue = element.textContent ?: ""
        onValueChange(newValue)
    }
    val focusListener: (dynamic) -> Unit = { selectAllContent(element, documentProvider, windowProvider) }
    val mouseUpListener: (dynamic) -> Unit = { selectAllContent(element, documentProvider, windowProvider) }
    val clickListener: (dynamic) -> Unit = { selectAllContent(element, documentProvider, windowProvider) }

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
