package com.example.reactivetodo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import software.amazon.codeguruprofilerjavaagent.Profiler

@SpringBootApplication
class App

fun main(args: Array<String>) {

    Profiler.builder()
            .profilingGroupName("Test-Reactive-Web")
            .build()
            .start()

    runApplication<App>(*args) {
        addInitializers(beans())
    }
}
