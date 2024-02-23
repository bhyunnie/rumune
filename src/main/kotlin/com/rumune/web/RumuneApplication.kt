package com.rumune.web

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableJpaAuditing(dateTimeProviderRef = "offSetDateTimeProvider")
@ConfigurationPropertiesScan
@EnableScheduling
class RumuneApplication

fun main(args: Array<String>) {
    runApplication<RumuneApplication>(*args)
}
