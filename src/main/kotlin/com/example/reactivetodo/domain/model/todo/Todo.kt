package com.example.reactivetodo.domain.model.todo

import com.example.reactivetodo.domain.model.auth.UserId

data class Todo(
    val id: TodoId,
    val userId: UserId,
    val title: TodoTitle,
    val content: TodoContent,
    val done: Boolean
) {
    constructor(id: Int, userId: String, title: String, content: String, done: Boolean) :
            this(TodoId(id), UserId(userId), TodoTitle(title), TodoContent(content), done)
}
