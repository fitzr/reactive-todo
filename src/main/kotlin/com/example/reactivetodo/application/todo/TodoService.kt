package com.example.reactivetodo.application.todo

import com.example.reactivetodo.domain.model.todo.TodoContent
import com.example.reactivetodo.domain.model.todo.TodoId
import com.example.reactivetodo.domain.model.todo.TodoRepository
import com.example.reactivetodo.domain.model.todo.TodoTitle
import com.example.reactivetodo.domain.model.user.User

class TodoService(private val repo: TodoRepository) {
    suspend fun list(user: User): List<TodoData> =
            repo.fetchAll(user.id).map { TodoData(it) }

    suspend fun find(user: User, id: TodoId): TodoData =
            repo.find(user.id, id)?.let { TodoData(it) } ?: throw IllegalStateException("NOT FOUND")

    suspend fun create(user: User, title: TodoTitle, content: TodoContent): TodoData =
            TodoData(repo.insert(user.id, title, content))

    suspend fun update(user: User, title: TodoTitle?, content: TodoContent?, done: Boolean?): TodoData =
            repo.update(user.id, title, content, done)?.let { TodoData(it) } ?: throw IllegalStateException("NOT FOUND")

    suspend fun delete(user: User, id: TodoId) {
        repo.delete(user.id, id) || throw IllegalStateException("NOT FOUND")
    }
}
