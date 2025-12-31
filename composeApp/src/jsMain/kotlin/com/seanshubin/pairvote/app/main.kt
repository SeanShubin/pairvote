package com.seanshubin.pairvote.app

import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.renderComposable

fun main() {
    window.onerror = { message, source, lineno, colno, error ->
        document.body?.innerHTML = """
              <div style="padding: 20px; background: #ffebee; font-family: monospace;">
                  <h1>Application Error</h1>
                  <pre>$message

  ${error?.asDynamic()?.stack ?: ""}</pre>
              </div>
          """.trimIndent()
        false
    }

    renderComposable(rootElementId = "root") {
        App()
    }
}
