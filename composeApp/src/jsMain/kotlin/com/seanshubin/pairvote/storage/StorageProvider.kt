package com.seanshubin.pairvote.storage

interface StorageProvider {
    fun getItem(key: String): String?
    fun setItem(key: String, value: String)
    fun removeItem(key: String)
}
