package com.rumune.web.global.config

import com.rumune.web.domain.user.application.OAuth2UserInfoEndpoint
import com.rumune.web.global.auth.FailedAuthenticationEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun filterChain(
        http: HttpSecurity,
        oAuth2UserInfoEndpoint: OAuth2UserInfoEndpoint
    ): SecurityFilterChain {
        http
            .csrf { csrf ->
                csrf.disable()
            }
            .headers { header ->
                header.frameOptions { frameOption ->
                    frameOption.sameOrigin()
                }
            }
            .authorizeHttpRequests { request ->
                request.requestMatchers("/**", "/swagger-ui/**", "/api/**").permitAll()
                // .requestMatchers("").hasRole()
                // .anyRequest().authenticated() // 제외 전부 권한 체크
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .exceptionHandling { handling ->
                handling.authenticationEntryPoint(FailedAuthenticationEntryPoint())
            }

        http.oauth2Login { oauth2 ->
            oauth2.loginPage("/login/oauth2")
                .authorizationEndpoint { authorization ->
                    authorization.baseUri("/api/oauth2/login/**")
                }
                .userInfoEndpoint { userInfo ->
                    userInfo.userService(oAuth2UserInfoEndpoint)
                }
                .redirectionEndpoint { redirectionEndpoint ->
                    redirectionEndpoint.baseUri("http://localhost:8080/api/auth/oauth2/login/callback/**")
                }
        }
        return http.build()
    }
}