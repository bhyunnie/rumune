package com.rumune.web.global.security.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig: WebMvcConfigurer {
    @Override
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000", "https://discord.com", "https://kapi.com")
            .allowedMethods("GET","POST","PUT","DELETE")
            .allowedHeaders("Authorization", "Content-Type")
            .exposedHeaders("Custom-Header")
            .allowCredentials(true)
            .maxAge(3600)
    }
}