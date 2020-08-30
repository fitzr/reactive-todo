package com.example.reactivetodo.domain.model.auth

interface UserRepository {
    suspend fun find(token: AuthToken): User?
}
