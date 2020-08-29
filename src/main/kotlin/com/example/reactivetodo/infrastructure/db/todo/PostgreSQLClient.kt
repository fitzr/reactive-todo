package com.example.reactivetodo.infrastructure.db.todo

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.data.r2dbc.core.DatabaseClient

class PostgreSQLClient(
    @Value("\${todo.db.host}") private val host: String,
    @Value("\${todo.db.port}") private val port: Int,
    @Value("\${todo.db.user}") private val user: String,
    @Value("\${todo.db.pass}") private val pass: String,
    @Value("\${todo.db.database}") private val database: String
) {
    @Bean
    fun client(): DatabaseClient {
        return DatabaseClient.create(PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration
                        .builder()
                        .host(host)
                        .port(port)
                        .database(database)
                        .username(user)
                        .password(pass)
                        .build()
        ))
    }
}
