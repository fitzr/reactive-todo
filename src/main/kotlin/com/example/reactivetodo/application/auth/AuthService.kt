package com.example.reactivetodo.application.auth

import com.example.reactivetodo.application.TodoUnauthorizedException
import com.example.reactivetodo.domain.model.auth.AuthToken
import com.example.reactivetodo.domain.model.auth.User
import com.example.reactivetodo.domain.model.auth.UserRepository

class AuthService(private val uerRepository: UserRepository) {
    suspend fun authenticate(token: AuthToken): User =
            uerRepository.find(token) ?: throw TodoUnauthorizedException("The token was not valid.")
}
