package com.example.reactivetodo.api.handlers

import com.example.reactivetodo.application.todo.TodoService
import com.example.reactivetodo.domain.model.auth.User
import com.example.reactivetodo.domain.model.todo.TodoContent
import com.example.reactivetodo.domain.model.todo.TodoId
import com.example.reactivetodo.domain.model.todo.TodoTitle
import java.net.URI
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait

class TodoHandler(private val service: TodoService) {
    suspend fun list(request: ServerRequest): ServerResponse {
        val list = service.list(userOf(request))
        return ServerResponse.ok().bodyValueAndAwait(list)
    }

    suspend fun find(request: ServerRequest): ServerResponse {
        val todo = service.find(userOf(request), todoIdOf(request))
        return ServerResponse.ok().bodyValueAndAwait(todo)
    }

    suspend fun create(request: ServerRequest): ServerResponse {
        val param = request.awaitBody<CreateTodoParam>()
        val todo = service.create(userOf(request), param.todoTitle, param.todoContent)
        return ServerResponse.created(URI("${request.path()}/${todo.id}")).bodyValueAndAwait(todo)
    }

    suspend fun update(request: ServerRequest): ServerResponse {
        val param = request.awaitBody<UpdateTodoParam>()
        val todo = service.update(userOf(request), todoIdOf(request), param.todoTitle, param.todoContent, param.done)
        return ServerResponse.ok().bodyValueAndAwait(todo)
    }

    suspend fun delete(request: ServerRequest): ServerResponse {
        service.delete(userOf(request), todoIdOf(request))
        return ServerResponse.noContent().buildAndAwait()
    }

    private fun userOf(request: ServerRequest): User =
            request.attribute("AUTHENTICATED_USER").orElseThrow() as User

    private fun todoIdOf(request: ServerRequest): TodoId =
            TodoId(request.pathVariable("id"))

    private data class CreateTodoParam(val title: String, val content: String) {
        val todoTitle: TodoTitle
            get() = TodoTitle(title)

        val todoContent: TodoContent
            get() = TodoContent(content)
    }

    private data class UpdateTodoParam(val title: String?, val content: String?, val done: Boolean?) {
        val todoTitle: TodoTitle?
            get() = title?.let { TodoTitle(it) }

        val todoContent: TodoContent?
            get() = content?.let { TodoContent(it) }
    }
}
