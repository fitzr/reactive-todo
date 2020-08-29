package com.example.reactivetodo

import com.example.reactivetodo.api.Router
import com.example.reactivetodo.api.handlers.TodoHandler
import org.springframework.context.support.beans

fun beans() = beans {
    bean<Router>()
    bean { ref<Router>().router() }
    bean<TodoHandler>()
}
