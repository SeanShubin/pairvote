package com.seanshubin.pairvote.storage

import kotlinx.browser.localStorage

object BrowserStorageProvider : StorageProvider {
    override fun getItem(key: String): String? =
        localStorage.getItem(key)

    override fun setItem(key: String, value: String) {
        localStorage.setItem(key, value)
    }

    override fun removeItem(key: String) {
        localStorage.removeItem(key)
    }
}
