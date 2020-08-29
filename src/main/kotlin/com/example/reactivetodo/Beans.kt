package com.example.reactivetodo

import com.example.reactivetodo.api.Router
import com.example.reactivetodo.api.handlers.TodoHandler
import com.example.reactivetodo.application.todo.TodoService
import org.springframework.context.support.beans

fun beans() = beans {
    bean<Router>()
    bean { ref<Router>().router() }
    bean<TodoHandler>()
    bean<TodoService>()
}
