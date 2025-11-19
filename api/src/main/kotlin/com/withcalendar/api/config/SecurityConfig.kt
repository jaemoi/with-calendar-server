package com.withcalendar.api.config

import com.withcalendar.api.security.CustomAccessDeniedHandler
import com.withcalendar.api.security.CustomAuthenticationEntryPoint
import com.withcalendar.api.security.JwtAuthenticationFilter
import com.withcalendar.api.security.TokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val tokenProvider: TokenProvider,
    private val accessDeniedHandler: CustomAccessDeniedHandler,
    private val authenticationEntryPoint: CustomAuthenticationEntryPoint
) {

    companion object {
        private val PUBLIC_ENDPOINTS = listOf(
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/api/v1/auth/**",       // 로그인/회원가입/토큰 재발급
            "/error",                // 스프링 내부 오류 페이지
        )
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        http
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }

            .cors {
                it.configurationSource {
                    CorsConfiguration().apply {
                        allowedOriginPatterns = listOf("*")
                        allowedMethods = listOf("*")
                        allowedHeaders = listOf("*")
                        allowCredentials = true
                    }
                }
            }

            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }

            .exceptionHandling {
                it.authenticationEntryPoint(authenticationEntryPoint)
                it.accessDeniedHandler(accessDeniedHandler)
            }

            .authorizeHttpRequests {
                it
                    .requestMatchers(*PUBLIC_ENDPOINTS.toTypedArray())
                    .permitAll()
                    .anyRequest().authenticated()
            }

            .addFilterBefore(
                JwtAuthenticationFilter(tokenProvider),
                UsernamePasswordAuthenticationFilter::class.java
            )

        return http.build()
    }
}
