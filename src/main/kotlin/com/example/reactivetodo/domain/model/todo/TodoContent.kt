package com.example.reactivetodo.domain.model.todo

data class TodoContent(val value: String) {
    init {
        require(value.isNotBlank()) { "A blank content is not allowed." }
    }
}
