package com.seanshubin.pairvote.app

import com.seanshubin.pairvote.platform.BrowserDocumentBodyProvider
import com.seanshubin.pairvote.platform.BrowserWindowProvider
import com.seanshubin.pairvote.platform.DocumentBodyProvider
import com.seanshubin.pairvote.platform.WindowProvider
import org.jetbrains.compose.web.renderComposable

fun main() {
    startApp(BrowserWindowProvider, BrowserDocumentBodyProvider)
}

fun startApp(
    windowProvider: WindowProvider = BrowserWindowProvider,
    documentBodyProvider: DocumentBodyProvider = BrowserDocumentBodyProvider
) {
    windowProvider.onerror = { message, source, lineno, colno, error ->
        renderErrorPage(message, error, documentBodyProvider)
        false
    }
    renderComposable(rootElementId = "root") {
        App()
    }
}

private fun renderErrorPage(
    message: Any?,
    error: Any?,
    documentBodyProvider: DocumentBodyProvider
) {
    documentBodyProvider.innerHTML = """
          <div style="padding: 20px; background: #ffebee; font-family: monospace;">
              <h1>Application Error</h1>
              <pre>$message

  ${error?.asDynamic()?.stack ?: ""}</pre>
          </div>
      """.trimIndent()
}