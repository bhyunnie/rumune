package com.rumune.web.global.security.config

import com.rumune.web.domain.user.application.CustomOAuth2UserService
import com.rumune.web.domain.user.application.CustomOidcUserService
import com.rumune.web.domain.user.application.UserService
import com.rumune.web.domain.user.repository.UserRepository
import com.rumune.web.global.properties.JwtProperties
import com.rumune.web.global.security.filter.JwtAuthenticationFilter
import com.rumune.web.global.security.handler.OAuth2SuccessHandler
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.time.OffsetDateTime
import java.util.Optional


@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(JwtProperties::class)
class SecurityConfig(
    private val customOAuth2UserService: CustomOAuth2UserService,
    private val customOidcUserService: CustomOidcUserService,
) {
    @Bean
    fun filterChain(
        http: HttpSecurity,
        jwtAuthenticationFilter: JwtAuthenticationFilter,
        oAuth2SuccessHandler: OAuth2SuccessHandler,
    ): SecurityFilterChain {
        http
            .formLogin { form ->
                form.disable()
            }
            .csrf { csrf ->
                csrf.disable()
            }
            .headers { header ->
                header.frameOptions { frameOption ->
                    frameOption.disable()
                }
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
//            .authorizeHttpRequests { request ->
//                request
//                    .requestMatchers(
//                        "/",
//                        "/api/authenticate",
//                    ).permitAll()
//                    .requestMatchers("/api/**").authenticated()
//                    .anyRequest().permitAll() // 제외 전부 권한 체크
//            }
            .oauth2Login{ oauth2Login ->
                oauth2Login.userInfoEndpoint {
                    userInfoEndpoint -> userInfoEndpoint.userService(customOAuth2UserService)
                    .oidcUserService(customOidcUserService)
                }
                    .successHandler(oAuth2SuccessHandler)
            }
//            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = NoOpPasswordEncoder.getInstance()

    @Bean
    fun authenticationManager(config:AuthenticationConfiguration): AuthenticationManager = config.authenticationManager

    @Bean
    fun userDetailsService(userRepository: UserRepository): UserDetailsService = UserService(userRepository)

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
        val roleHierarchy = RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER")
        return roleHierarchy
    }

    @Bean("offSetDateTimeProvider")
    fun dateTimeProvider(): DateTimeProvider {
        return DateTimeProvider { Optional.of(OffsetDateTime.now()) }
    }
}