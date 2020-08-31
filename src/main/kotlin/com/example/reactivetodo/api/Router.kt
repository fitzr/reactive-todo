package com.example.reactivetodo.api

import com.example.reactivetodo.api.filters.AuthFilter
import com.example.reactivetodo.api.handlers.TodoHandler
import org.springframework.web.reactive.function.server.coRouter

class Router(private val todoHandler: TodoHandler, private val authFilter: AuthFilter) {
    fun router() = coRouter {
        GET("/todo", todoHandler::list)
        GET("/todo/{id}", todoHandler::find)
        POST("/todo", todoHandler::create)
        PATCH("/todo/{id}", todoHandler::update)
        DELETE("/todo/{id}", todoHandler::delete)
    }.filter(authFilter)
}
