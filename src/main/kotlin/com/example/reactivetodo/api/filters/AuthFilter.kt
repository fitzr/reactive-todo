package com.example.reactivetodo.api.filters

import com.example.reactivetodo.application.TodoUnauthorizedException
import com.example.reactivetodo.application.auth.AuthService
import com.example.reactivetodo.domain.model.auth.AuthToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactor.mono
import org.springframework.web.reactive.function.server.HandlerFilterFunction
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

class AuthFilter(private val service: AuthService) : HandlerFilterFunction<ServerResponse, ServerResponse> {

    override fun filter(request: ServerRequest, next: HandlerFunction<ServerResponse>): Mono<ServerResponse> {
        val token = tokenOf(request)
        return mono(Dispatchers.Unconfined) {
            val user = service.authenticate(token)
            request.attributes()["AUTHENTICATED_USER"] = user
            request
        }.flatMap(next::handle)
    }

    private fun tokenOf(request: ServerRequest): AuthToken =
            AuthToken.createOrNull(request.headers().firstHeader("Authorization"))
                    ?: throw TodoUnauthorizedException("Auth header is required.")
}
