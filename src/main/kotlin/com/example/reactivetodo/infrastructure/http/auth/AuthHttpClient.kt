package com.example.reactivetodo.infrastructure.http.auth

import com.example.reactivetodo.domain.model.auth.AuthToken
import com.example.reactivetodo.domain.model.auth.User
import com.example.reactivetodo.domain.model.auth.UserId
import com.example.reactivetodo.domain.model.auth.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange

class AuthHttpClient(
    @Value("\${todo.auth.baseUrl}") private val baseUrl: String,
    @Value("\${todo.auth.path}") private val path: String
) : UserRepository {

    private val client = WebClient.builder().baseUrl(baseUrl).build()

    override suspend fun find(token: AuthToken): User? = client
            .post()
            .uri(path)
            .body(BodyInserters.fromFormData("token", token.value))
            .awaitExchange()
            .let { response -> when (response.statusCode()) {
                HttpStatus.OK -> response.awaitBody<AuthResponseBody>().let { User(UserId(it.id), it.name) }
                HttpStatus.UNAUTHORIZED -> null
                else -> throw IllegalStateException("AuthHttpClient received an unexpected response. $response")
            } }

    private data class AuthResponseBody(val id: String, val name: String)
}
