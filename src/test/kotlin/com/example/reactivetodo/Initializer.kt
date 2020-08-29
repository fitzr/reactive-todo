package com.example.reactivetodo

import com.example.reactivetodo.infrastructure.mocks.MockTodoRepository
import com.example.reactivetodo.infrastructure.mocks.MockUserRepository
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext

class AppInitializer : ApplicationContextInitializer<GenericApplicationContext> {

    override fun initialize(context: GenericApplicationContext) {
        beans().initialize(context)
    }
}

class MockInitializer : ApplicationContextInitializer<GenericApplicationContext> {

    override fun initialize(context: GenericApplicationContext) {
        org.springframework.context.support.beans {
            bean<MockUserRepository>(isPrimary = true)
            bean<MockTodoRepository>(isPrimary = true)
        }.initialize(context)
    }
}
