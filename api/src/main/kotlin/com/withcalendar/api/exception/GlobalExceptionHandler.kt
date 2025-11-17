package com.withcalendar.api.exception

import com.withcalendar.api.common.ApiResponse
import com.withcalendar.application.exception.BusinessException
import com.withcalendar.application.exception.ErrorCode
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.*

@RestControllerAdvice
class GlobalExceptionHandler {
    private val logger = KotlinLogging.logger {}

    @ExceptionHandler
    fun handleBusiness(e: BusinessException): ResponseEntity<ApiResponse<Unit>> {
        val code = e.errorCode.code
        val messageKey = e.customMessage ?: e.errorCode.messageKey
        val detail = e.detail

        val body = ApiResponse.fail(code, messageKey, detail)
        return ResponseEntity.status(e.errorCode.status).body(body)
    }

    /**
     * 2) Validation 예외 (@Valid 검증 실패)
     * MethodArgumentNotValidException → @RequestBody DTO 검증 실패
     * BindException → @ModelAttribute 검증 실패 (ex: 쿼리파라미터)
     */
    @ExceptionHandler
    fun handleMethodArgumentNotValid(e: MethodArgumentNotValidException): ResponseEntity<ApiResponse<Unit>> {

        val fieldError = e.bindingResult.fieldErrors.firstOrNull()

        val detail = fieldError?.let {
            mapOf(
                "field" to it.field,
                "reason" to (it.defaultMessage ?: "Invalid value")
            )
        }

        val body = ApiResponse.fail(
            code = ErrorCode.INVALID_INPUT.code,
            messageKey = ErrorCode.INVALID_INPUT.messageKey,
            detail = detail
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body)
    }

    @ExceptionHandler
    fun handleBindException(e: BindException): ResponseEntity<ApiResponse<Unit>> {

        val fieldError = e.bindingResult.fieldErrors.firstOrNull()

        val detail = fieldError?.let {
            mapOf(
                "field" to it.field,
                "reason" to (it.defaultMessage ?: "Invalid value")
            )
        }

        val body = ApiResponse.fail(
            code = ErrorCode.INVALID_INPUT.code,
            messageKey = ErrorCode.INVALID_INPUT.messageKey,
            detail = detail
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body)
    }


    /**
     * 3) JSON 파싱 예외
     * JSON 문법 오류 / Enum 값 에러 / 숫자 타입 오류 등
     */
    @ExceptionHandler
    fun handleJsonParse(e: HttpMessageNotReadableException): ResponseEntity<ApiResponse<Unit>> {

        logger.warn { "JSON Parse Error: $e" }

        val body = ApiResponse.fail(
            code = ErrorCode.INVALID_INPUT.code,
            messageKey = "error.invalid_json",  // 필요하면 ErrorCode에 추가
            detail = mapOf("reason" to "Invalid JSON format")
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponse<Unit>> {

        val errorId = UUID.randomUUID().toString()

        logger.error("[ERROR-ID=$errorId] Unexpected exception", e)

        val body = ApiResponse.fail(
            code = ErrorCode.INTERNAL_ERROR.code,
            messageKey = ErrorCode.INTERNAL_ERROR.messageKey,
            detail = mapOf("errorId" to errorId)
        )

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(body)
    }

}