package com.rumune.web

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing(dateTimeProviderRef = "offSetDateTimeProvider")
@ConfigurationPropertiesScan
class RumuneApplication

fun main(args: Array<String>) {
    runApplication<RumuneApplication>(*args)
}
