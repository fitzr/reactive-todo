package com.example.reactivetodo.application

class TodoNotFoundException(msg: String) : RuntimeException(msg)

class TodoUnauthorizedException(msg: String) : RuntimeException(msg)
