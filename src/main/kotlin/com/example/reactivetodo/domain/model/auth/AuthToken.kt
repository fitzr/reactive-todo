package com.example.reactivetodo.domain.model.auth

data class AuthToken(val value: String) {
    companion object {
        fun createOrNull(str: String?) = if (str.isNullOrBlank()) null else AuthToken(str)
    }
}
