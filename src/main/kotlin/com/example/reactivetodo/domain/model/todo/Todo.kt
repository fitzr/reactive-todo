package com.example.reactivetodo.domain.model.todo

import com.example.reactivetodo.domain.model.user.UserId

data class Todo(
    val id: TodoId,
    val userId: UserId,
    val title: TodoTitle,
    val content: TodoContent,
    val done: Boolean
)
