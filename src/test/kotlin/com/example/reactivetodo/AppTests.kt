package com.example.reactivetodo

import com.example.reactivetodo.api.exception.ErrorResponse
import com.example.reactivetodo.api.exception.ExceptionHandler
import com.example.reactivetodo.application.todo.TodoData
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.web.reactive.function.server.HandlerStrategies
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ContextConfiguration(initializers = [AppInitializer::class, MockInitializer::class])
class AppTests(
    @Autowired private val routerFunction: RouterFunction<ServerResponse>,
    @Autowired private val exceptionHandler: ExceptionHandler
) {
    lateinit var client: WebTestClient

    @BeforeEach
    internal fun setUp() {
        client = WebTestClient
                .bindToRouterFunction(routerFunction)
                .handlerStrategies(HandlerStrategies.builder().exceptionHandler(exceptionHandler).build())
                .build()
    }

    @Test
    fun `list todo`() {
        client.get()
                .uri("/todo")
                .header("Authorization", "Buzz")
                .exchange()
                .expectStatus().isOk
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody<List<TodoData>>()
                .consumeWith {
                    assertEquals(2, it.responseBody?.size)
                }
    }

    @Test
    fun `find todo`() {
        client.get()
                .uri("/todo/11")
                .header("Authorization", "Buzz")
                .exchange()
                .expectStatus().isOk
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody<TodoData>()
                .consumeWith {
                    assertEquals(TodoData(11, "payment", "pay rent", false), it.responseBody)
                }
    }

    @Test
    fun `create todo`() {
        client.post()
                .uri("/todo")
                .header("Authorization", "Buzz")
                .bodyValue(mapOf("title" to "clean", "content" to "clean the garden"))
                .exchange()
                .expectStatus().isCreated
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody<TodoData>()
                .consumeWith {
                    assertEquals(TodoData(12, "clean", "clean the garden", false), it.responseBody)
                }
    }

    @Test
    fun `update todo`() {
        client.patch()
                .uri("/todo/11")
                .header("Authorization", "Buzz")
                .bodyValue(mapOf("done" to true))
                .exchange()
                .expectStatus().isOk
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody<TodoData>()
                .consumeWith {
                    assertEquals(TodoData(11, "payment", "pay rent", true), it.responseBody)
                }
    }

    @Test
    fun `delete todo`() {
        client.delete()
                .uri("/todo/11")
                .header("Authorization", "Buzz")
                .exchange()
                .expectStatus().isNoContent
                .expectBody().isEmpty
    }

    @Test
    fun `should return unauthorized when Authorization header is not valid`() {
        client.get()
                .uri("/todo/11")
                .header("Authorization", "Fizz")
                .exchange()
                .expectStatus().isUnauthorized
                .expectBody<ErrorResponse>()
                .consumeWith {
                    assertEquals("Unauthorized", it.responseBody?.msg)
                }
    }

    @Test
    fun `should return not found when requested todo id is not found`() {
        client.get()
                .uri("/todo/99")
                .header("Authorization", "Buzz")
                .exchange()
                .expectStatus().isNotFound
                .expectBody<ErrorResponse>()
                .consumeWith {
                    assertEquals("Not Found", it.responseBody?.msg)
                }
    }
}
