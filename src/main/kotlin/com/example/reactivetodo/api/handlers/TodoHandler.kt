package com.example.reactivetodo.api.handlers

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

class TodoHandler {
    suspend fun list(request: ServerRequest): ServerResponse {
        val list = emptyList<Any>()
        return ServerResponse.ok().bodyValueAndAwait(list)
    }

    suspend fun find(request: ServerRequest): ServerResponse {
        val list = emptyList<Any>()
        return ServerResponse.ok().bodyValueAndAwait(list)
    }

    suspend fun create(request: ServerRequest): ServerResponse {
        val list = emptyList<Any>()
        return ServerResponse.ok().bodyValueAndAwait(list)
    }

    suspend fun update(request: ServerRequest): ServerResponse {
        val list = emptyList<Any>()
        return ServerResponse.ok().bodyValueAndAwait(list)
    }

    suspend fun delete(request: ServerRequest): ServerResponse {
        val list = emptyList<Any>()
        return ServerResponse.ok().bodyValueAndAwait(list)
    }
}
