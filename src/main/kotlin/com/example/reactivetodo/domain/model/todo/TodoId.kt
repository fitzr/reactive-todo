package com.example.reactivetodo.domain.model.todo

data class TodoId(val value: Int) {
    constructor(str: String?) : this(
            requireNotNull(str?.toIntOrNull()) { "TodoId($str) was invalid." }
    )
}
