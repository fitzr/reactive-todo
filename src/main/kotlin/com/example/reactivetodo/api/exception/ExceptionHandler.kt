package com.example.reactivetodo.api.exception

import com.example.reactivetodo.application.TodoNotFoundException
import com.example.reactivetodo.application.TodoUnauthorizedException
import java.util.Collections
import org.slf4j.LoggerFactory
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.http.HttpStatus
import org.springframework.http.codec.HttpMessageWriter
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.result.view.ViewResolver
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class ExceptionHandler(serverCodecConfigurer: ServerCodecConfigurer) : ErrorWebExceptionHandler {

    private val context = ResponseContext(serverCodecConfigurer.writers)
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> =
            handle(ex).flatMap { it.writeTo(exchange, context) }

    private fun handle(ex: Throwable): Mono<ServerResponse> =
        when (ex) {
            is IllegalArgumentException -> HttpStatus.BAD_REQUEST
            is TodoNotFoundException -> HttpStatus.NOT_FOUND
            is TodoUnauthorizedException -> HttpStatus.UNAUTHORIZED
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }.let {
            if (it.is5xxServerError) {
                logger.error("SERVER ERROR", ex)
            } else {
                logger.debug("CLIENT ERROR", ex)
            }
            ServerResponse.status(it.value()).bodyValue(ErrorResponse(it.reasonPhrase))
        }

    private class ResponseContext(val writers: MutableList<HttpMessageWriter<*>>) : ServerResponse.Context {
        override fun viewResolvers(): MutableList<ViewResolver> = Collections.emptyList()
        override fun messageWriters() = writers
    }
}
