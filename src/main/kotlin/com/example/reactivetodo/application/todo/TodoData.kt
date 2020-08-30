package com.example.reactivetodo.application.todo

import com.example.reactivetodo.domain.model.todo.Todo

data class TodoData(
    val id: Int,
    val title: String,
    val content: String,
    val done: Boolean
) {
    constructor(todo: Todo) : this(todo.id.value, todo.title.value, todo.content.value, todo.done)
}
