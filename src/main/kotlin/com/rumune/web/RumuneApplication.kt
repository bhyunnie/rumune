package com.rumune.web

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing(dateTimeProviderRef = "offSetDateTimeProvider")
class RumuneApplication

fun main(args: Array<String>) {
    runApplication<RumuneApplication>(*args)
}
