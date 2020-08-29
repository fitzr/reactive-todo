package com.example.reactivetodo.domain.model.todo

import com.example.reactivetodo.domain.model.user.UserId

interface TodoRepository {
    suspend fun fetchAll(userId: UserId): List<Todo>
    suspend fun find(userId: UserId, todoId: TodoId): Todo?
    suspend fun insert(userId: UserId, title: TodoTitle, content: TodoContent): Todo
    suspend fun update(userId: UserId, title: TodoTitle?, content: TodoContent?, done: Boolean?): Todo?
    suspend fun delete(userId: UserId, todoId: TodoId): Boolean
}
