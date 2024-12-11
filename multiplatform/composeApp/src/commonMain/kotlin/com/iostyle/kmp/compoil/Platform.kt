package com.iostyle.kmp.compoil

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform