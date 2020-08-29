package com.example.reactivetodo.infrastructure.db.todo

import com.example.reactivetodo.domain.model.auth.UserId
import com.example.reactivetodo.domain.model.todo.Todo
import com.example.reactivetodo.domain.model.todo.TodoContent
import com.example.reactivetodo.domain.model.todo.TodoId
import com.example.reactivetodo.domain.model.todo.TodoRepository
import com.example.reactivetodo.domain.model.todo.TodoTitle

class PostgreSQLTodoRepository : TodoRepository {
    override suspend fun fetchAll(userId: UserId): List<Todo> {
        TODO("Not yet implemented")
    }

    override suspend fun find(userId: UserId, todoId: TodoId): Todo? {
        TODO("Not yet implemented")
    }

    override suspend fun insert(userId: UserId, title: TodoTitle, content: TodoContent): Todo {
        TODO("Not yet implemented")
    }

    override suspend fun update(userId: UserId, title: TodoTitle?, content: TodoContent?, done: Boolean?): Todo? {
        TODO("Not yet implemented")
    }

    override suspend fun delete(userId: UserId, todoId: TodoId): Boolean {
        TODO("Not yet implemented")
    }
}
