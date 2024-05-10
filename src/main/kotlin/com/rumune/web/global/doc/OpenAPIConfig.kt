package com.rumune.web.global.doc

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenAPIConfig {
    @Bean
    fun openAPI(): OpenAPI {
        val info =
            Info()
                .version("v1.0.0")
                .title("RUMUNE API DOCUMENTATION")
                .description("RUMUNE API 문서입니다.")
        val components =
            Components().addSecuritySchemes(
                "bearer-token",
                SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"),
            )
        return OpenAPI().info(info).components(components)
    }
}
