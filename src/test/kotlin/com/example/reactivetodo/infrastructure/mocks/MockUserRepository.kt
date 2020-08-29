package com.example.reactivetodo.infrastructure.mocks

import com.example.reactivetodo.domain.model.auth.AuthToken
import com.example.reactivetodo.domain.model.auth.User
import com.example.reactivetodo.domain.model.auth.UserId
import com.example.reactivetodo.domain.model.auth.UserRepository

class MockUserRepository : UserRepository {
    override suspend fun find(token: AuthToken): User? =
            if (token.value == "Buzz") User(UserId("BuzzId"), "Buzz") else null
}
