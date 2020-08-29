package com.example.reactivetodo

import com.example.reactivetodo.api.ExceptionHandler
import com.example.reactivetodo.api.Router
import com.example.reactivetodo.api.filters.AuthFilter
import com.example.reactivetodo.api.handlers.TodoHandler
import com.example.reactivetodo.application.auth.AuthService
import com.example.reactivetodo.application.todo.TodoService
import com.example.reactivetodo.infrastructure.db.todo.PostgreSQLTodoRepository
import com.example.reactivetodo.infrastructure.http.auth.AuthHttpClient
import org.springframework.context.support.beans

fun beans() = beans {
    bean<Router>()
    bean { ref<Router>().router() }
    bean<TodoHandler>()
    bean<AuthFilter>()
    bean<ExceptionHandler>()
    bean<AuthService>()
    bean<TodoService>()
    bean<AuthHttpClient>()
    bean<PostgreSQLTodoRepository>()
}
