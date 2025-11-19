package com.withcalendar.api.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

@Component
class LoggingFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        try {
            // 1) traceId 생성
            val traceId = UUID.randomUUID().toString()

            // 2) MDC에 넣기 → 모든 로그에 자동 포함됨
            MDC.put("traceId", traceId)

            // 3) 요청 URL 로깅 (옵션)
            logger.info("Incoming request: ${request.method} ${request.requestURI}")

            filterChain.doFilter(request, response)

        } finally {
            // 요청 끝나면 꼭 지워줘야 Memory Leak 안남
            MDC.clear()
        }
    }
}