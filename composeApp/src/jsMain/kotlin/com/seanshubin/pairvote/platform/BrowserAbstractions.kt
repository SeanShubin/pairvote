package com.seanshubin.pairvote.platform

import org.w3c.dom.Element

interface DocumentProvider {
    val activeElement: Element?
    fun createRange(): RangeProvider
    fun getSelection(): SelectionProvider?
}

interface WindowProvider {
    fun setTimeout(callback: () -> Unit, delayMs: Int)
    var onerror: ((message: Any?, source: String?, lineno: Int?, colno: Int?, error: Any?) -> Boolean)?
}

interface SelectionProvider {
    fun removeAllRanges()
    fun addRange(range: RangeProvider)
}

interface RangeProvider {
    fun selectNodeContents(element: Element)
}

interface DocumentBodyProvider {
    var innerHTML: String?
}

object BrowserDocumentProvider : DocumentProvider {
    override val activeElement: Element?
        get() = kotlinx.browser.document.activeElement

    override fun createRange(): RangeProvider =
        BrowserRangeProvider(kotlinx.browser.document.createRange())

    override fun getSelection(): SelectionProvider? {
        val sel = kotlinx.browser.document.asDynamic().getSelection()
        return if (sel != null) BrowserSelectionProvider(sel) else null
    }
}

object BrowserWindowProvider : WindowProvider {
    override fun setTimeout(callback: () -> Unit, delayMs: Int) {
        kotlinx.browser.window.setTimeout(callback, delayMs)
    }

    override var onerror: ((Any?, String?, Int?, Int?, Any?) -> Boolean)?
        get() = kotlinx.browser.window.onerror?.let { handler ->
            { msg, src, line, col, err ->
                handler(msg, src ?: "", line ?: 0, col ?: 0, err) as? Boolean ?: false
            }
        }
        set(value) {
            kotlinx.browser.window.onerror = value?.let { handler ->
                { msg, src, line, col, err ->
                    handler(msg, src, line, col, err)
                }
            }
        }
}

private class BrowserSelectionProvider(private val selection: dynamic) : SelectionProvider {
    override fun removeAllRanges() {
        selection.removeAllRanges()
    }

    override fun addRange(range: RangeProvider) {
        selection.addRange((range as BrowserRangeProvider).range)
    }
}

private class BrowserRangeProvider(val range: org.w3c.dom.Range) : RangeProvider {
    override fun selectNodeContents(element: Element) {
        range.selectNodeContents(element)
    }
}

object BrowserDocumentBodyProvider : DocumentBodyProvider {
    override var innerHTML: String?
        get() = kotlinx.browser.document.body?.innerHTML
        set(value) {
            if (value != null) {
                kotlinx.browser.document.body?.innerHTML = value
            }
        }
}
