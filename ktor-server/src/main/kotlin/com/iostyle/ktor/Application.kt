package com.iostyle.ktor

import com.iostyle.ktor.routing.configureRouting
import com.iostyle.ktor.routing.snippetRouting
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureRouting()
    snippetRouting()
}
