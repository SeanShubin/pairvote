package com.seanshubin.pairvote

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform