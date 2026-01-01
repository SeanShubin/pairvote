package com.seanshubin.pairvote.platform

interface DocumentProvider {
    val activeElement: Any?
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
    fun selectNodeContents(element: Any)
}

interface DocumentBodyProvider {
    var innerHTML: String?
}
