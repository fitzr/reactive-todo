package com.example.reactivetodo.infrastructure.mocks

import com.example.reactivetodo.domain.model.auth.UserId
import com.example.reactivetodo.domain.model.todo.Todo
import com.example.reactivetodo.domain.model.todo.TodoContent
import com.example.reactivetodo.domain.model.todo.TodoId
import com.example.reactivetodo.domain.model.todo.TodoRepository
import com.example.reactivetodo.domain.model.todo.TodoTitle

class MockTodoRepository : TodoRepository {
    private val data = listOf(
            Todo(id = 10, userId = "BuzzId", title = "shopping", content = "buy detergent", done = true),
            Todo(id = 11, userId = "BuzzId", title = "payment", content = "pay rent", done = false)
    )

    override suspend fun fetchAll(userId: UserId): List<Todo> =
            data.filter { it.userId == userId }

    override suspend fun find(userId: UserId, todoId: TodoId): Todo? =
            data.find { it.userId == userId && it.id == todoId }

    override suspend fun insert(userId: UserId, title: TodoTitle, content: TodoContent): Todo =
            Todo(TodoId(12), userId, title, content, false)

    override suspend fun update(userId: UserId, todoId: TodoId, title: TodoTitle?, content: TodoContent?, done: Boolean?): Todo? =
        data.find { it.userId == userId && it.id == todoId }
                ?.let { Todo(it.id, it.userId, title ?: it.title, content ?: it.content, done ?: it.done) }

    override suspend fun delete(userId: UserId, todoId: TodoId): Boolean =
            data.any { it.userId == userId && it.id == todoId }
}
