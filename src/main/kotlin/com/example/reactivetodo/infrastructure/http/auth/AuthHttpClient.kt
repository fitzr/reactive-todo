package com.example.reactivetodo.infrastructure.http.auth

import com.example.reactivetodo.domain.model.auth.AuthToken
import com.example.reactivetodo.domain.model.auth.User
import com.example.reactivetodo.domain.model.auth.UserRepository

class AuthHttpClient : UserRepository {
    override suspend fun find(token: AuthToken): User? {
        TODO("Not yet implemented")
    }
}
