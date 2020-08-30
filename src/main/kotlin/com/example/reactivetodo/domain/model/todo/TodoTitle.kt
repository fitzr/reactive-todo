package com.example.reactivetodo.domain.model.todo

data class TodoTitle(val value: String) {
    init {
        require(value.isNotBlank()) { "A blank title is not allowed." }
    }
}
