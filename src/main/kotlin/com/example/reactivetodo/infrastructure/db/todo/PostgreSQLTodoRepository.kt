package com.example.reactivetodo.infrastructure.db.todo

import com.example.reactivetodo.domain.model.auth.UserId
import com.example.reactivetodo.domain.model.todo.Todo
import com.example.reactivetodo.domain.model.todo.TodoContent
import com.example.reactivetodo.domain.model.todo.TodoId
import com.example.reactivetodo.domain.model.todo.TodoRepository
import com.example.reactivetodo.domain.model.todo.TodoTitle
import io.r2dbc.spi.Row
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.awaitFirst
import org.springframework.data.r2dbc.core.awaitFirstOrNull

class PostgreSQLTodoRepository(private val client: DatabaseClient) : TodoRepository {

    override suspend fun fetchAll(userId: UserId): List<Todo> = client
            .execute("SELECT * FROM todos WHERE user_id = :userId")
            .bind("userId", userId.value)
            .map(::readRow)
            .all()
            .collectList()
            .awaitSingle()

    override suspend fun find(userId: UserId, todoId: TodoId): Todo? = client
            .execute("SELECT * FROM todos WHERE id = :todoId AND user_id = :userId")
            .bind("userId", userId.value)
            .bind("todoId", todoId.value)
            .map(::readRow)
            .awaitFirstOrNull()

    override suspend fun insert(userId: UserId, title: TodoTitle, content: TodoContent): Todo = client
            .execute("INSERT INTO todos (user_id, title, content) VALUES(:userId, :title, :content) RETURNING *")
            .bind("userId", userId.value)
            .bind("title", title.value)
            .bind("content", content.value)
            .map(::readRow)
            .awaitFirst()

    override suspend fun update(userId: UserId, todoId: TodoId, title: TodoTitle?, content: TodoContent?, done: Boolean?): Todo? {
        val params = mapOf("title" to title?.value, "content" to content?.value, "done" to done)
                .filter { it.value != null }
        require(params.isNotEmpty())
        val set = params.map { "${it.key} = :${it.key}" }.joinToString(",")
        return client.execute("UPDATE todos SET $set WHERE id = :todoId AND user_id = :userId RETURNING *")
                .bind("userId", userId.value)
                .bind("todoId", todoId.value)
                .let {
                    params.toList().fold(it) { spec, param -> spec.bind(param.first, param.second!!) }
                }
                .map(::readRow)
                .awaitFirstOrNull()
    }

    override suspend fun delete(userId: UserId, todoId: TodoId): Boolean = client
            .execute("DELETE FROM todos WHERE id = :todoId AND user_id = :userId RETURNING *")
            .bind("userId", userId.value)
            .bind("todoId", todoId.value)
            .map(::readRow)
            .awaitFirstOrNull()
            .let { it != null }

    private fun readRow(row: Row): Todo =
            Todo(
                    row.get("id")!!.toString().toInt(),
                    row.get("user_id", String::class.java)!!,
                    row.get("title", String::class.java)!!,
                    row.get("content", String::class.java)!!,
                    row.get("done")!!.toString().toBoolean()
            )
}
