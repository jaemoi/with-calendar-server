package com.withcalendar.api.security

import com.withcalendar.application.common.ApiResponse
import com.withcalendar.application.exception.ErrorCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: org.springframework.security.core.AuthenticationException
    ) {

        val body = ApiResponse.fail(
            code = ErrorCode.UNAUTHORIZED.code,         // AUTH_001 같은 코드
            messageKey = ErrorCode.UNAUTHORIZED.messageKey,
            detail = "Authorization header missing or invalid"
        )

        response.status = ErrorCode.UNAUTHORIZED.status
        response.contentType = "application/json;charset=UTF-8"
        response.writer.write(body.toJson())
    }
}

// ApiResponse를 JSON 문자열로 변환하는 확장 함수
fun ApiResponse<*>.toJson(): String {
    return com.fasterxml.jackson.module.kotlin.jacksonObjectMapper()
        .writeValueAsString(this)
}
