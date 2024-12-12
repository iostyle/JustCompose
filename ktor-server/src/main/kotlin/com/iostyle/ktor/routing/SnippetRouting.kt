package com.iostyle.ktor.routing

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.request.receiveNullable
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.serialization.Serializable

private val snippets by lazy { mutableListOf("snippet1", "snippet2") }

@Serializable
data class PostSnippet(val snippet: Text) {
    @Serializable
    data class Text(val text: String)
}


fun Application.snippetRouting() {
    routing {
        route("/snippets") {
            get {
                call.respond(mapOf("snippets" to synchronized(snippets) { snippets.toList() }))
            }
            post {
                val json = call.receiveNullable<PostSnippet>()
                System.out.println(json)
                json?.snippet?.text?.let { snippets.add(it) }
                call.respondText("Success", status = HttpStatusCode.OK)
            }
        }
    }
}

