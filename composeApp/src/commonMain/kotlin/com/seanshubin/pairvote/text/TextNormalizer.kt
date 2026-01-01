package com.seanshubin.pairvote.text

object TextNormalizer {
    fun normalize(text: String): String =
        text.trim().replace(Regex("\\s+"), " ")
}
