package com.trustprice.web.global.security.config

import com.trustprice.web.domain.user.application.CustomOAuth2UserService
import com.trustprice.web.domain.user.application.CustomOidcUserService
import com.trustprice.web.global.security.filter.JwtAuthenticationFilter
import com.trustprice.web.global.security.handler.AuthenticationFailedHandler
import com.trustprice.web.global.security.handler.OAuth2FailedHandler
import com.trustprice.web.global.security.handler.OAuth2SuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.time.OffsetDateTime
import java.util.Optional

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val customOAuth2UserService: CustomOAuth2UserService,
    private val customOidcUserService: CustomOidcUserService,
) {
    @Bean
    fun filterChain(
        http: HttpSecurity,
        jwtAuthenticationFilter: JwtAuthenticationFilter,
        oAuth2SuccessHandler: OAuth2SuccessHandler,
        authenticationFailedHandler: AuthenticationFailedHandler,
        oAuth2FailedHandler: OAuth2FailedHandler,
    ): SecurityFilterChain {
        http
            .httpBasic { hb ->
                hb.disable()
            }
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
            .authorizeHttpRequests { request ->
                request
                    .requestMatchers(
                        "/",
                        "/api/v1/signin",
                        "/api/v1/category",
                        "/api/v1/post/product/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                    ).permitAll()
                    .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
            }
            .exceptionHandling { exception ->
                exception.authenticationEntryPoint(authenticationFailedHandler)
            }
            .oauth2Login { oauth2Login ->
                oauth2Login.userInfoEndpoint { userInfoEndpoint ->
                    userInfoEndpoint.userService(customOAuth2UserService)
                        .oidcUserService(customOidcUserService)
                }
                    .successHandler(oAuth2SuccessHandler)
                    .failureHandler(oAuth2FailedHandler)
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean("offSetDateTimeProvider")
    fun dateTimeProvider(): DateTimeProvider {
        return DateTimeProvider { Optional.of(OffsetDateTime.now()) }
    }
}
