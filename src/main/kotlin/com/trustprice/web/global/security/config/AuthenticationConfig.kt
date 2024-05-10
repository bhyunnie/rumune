package com.trustprice.web.global.security.config

import com.trustprice.web.domain.user.application.UserService
import com.trustprice.web.domain.user.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class AuthenticationConfig(
    private val userService: UserService,
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder = NoOpPasswordEncoder.getInstance()

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager = config.authenticationManager

    @Bean
    fun userDetailsService(userRepository: UserRepository): UserDetailsService = userService

    @Bean
    fun authenticationProvider(userRepository: UserRepository): AuthenticationProvider {
        return DaoAuthenticationProvider()
            .also {
                it.setUserDetailsService(userDetailsService(userRepository))
                it.setPasswordEncoder(passwordEncoder())
            }
    }

    @Bean
    fun roleHierarchy(): RoleHierarchy { // 권한 높낮이 설정
        val roleHierarchy = RoleHierarchyImpl()
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER")
        return roleHierarchy
    }
}
