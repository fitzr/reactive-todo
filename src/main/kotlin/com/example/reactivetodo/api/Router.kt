package com.example.reactivetodo.api

import com.example.reactivetodo.api.handlers.TodoHandler
import org.springframework.web.reactive.function.server.coRouter

class Router(private val todoHandle: TodoHandler) {
    fun router() = coRouter {
        GET("/todo", todoHandle::list)
        GET("/todo/{id}", todoHandle::find)
        POST("/todo", todoHandle::create)
        PATCH("/todo/{id}", todoHandle::update)
        DELETE("/todo/{id}", todoHandle::delete)
    }
}