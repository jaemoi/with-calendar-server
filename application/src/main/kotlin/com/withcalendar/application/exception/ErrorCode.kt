package com.withcalendar.application.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val code: String,
    val messageKey: String,
    val status: HttpStatus
) {

    // Common //
    INVALID_INPUT("COMMON_001", "error.invalid_input", HttpStatus.BAD_REQUEST),
    INVALID_JSON("COMMON_002", "error.invalid_json", HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR(
        "COMMON_999",
        "error.internal_error",
        HttpStatus.INTERNAL_SERVER_ERROR
    ),
    RESOURCE_NOT_FOUND(
        "COMMON_003",
        "error.resource_not_found",
        HttpStatus.NOT_FOUND
    ),


    // User //
    USER_NOT_FOUND("USER_001", "error.user_not_found", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS(
        "USER_002",
        "error.user_already_exists",
        HttpStatus.CONFLICT
    ),
    NICKNAME_DUPLICATED(
        "USER_003",
        "error.nickname_duplicated",
        HttpStatus.CONFLICT
    ),
    INVALID_NICKNAME("USER_004", "error.invalid_nickname", HttpStatus.CONFLICT),


    // Auth //
    UNAUTHORIZED("AUTH_001", "error.unauthorized", HttpStatus.BAD_REQUEST),
    FORBIDDEN("AUTH_002", "error.forbidden", HttpStatus.FORBIDDEN),
    TOKEN_EXPIRED("AUTH_003", "error.token_expired", HttpStatus.BAD_REQUEST),
    TOKEN_INVALID("AUTH_004", "error.token_invalid", HttpStatus.BAD_REQUEST),


    // Appointment //
    APPOINTMENT_NOT_FOUND(
        "APPOINTMENT_001", "error.appointment_not_found",
        HttpStatus.NOT_FOUND
    ),
    INVALID_TIME_RANGE(
        "APPOINTMENT_002", "error.invalid_time_range",
        HttpStatus.BAD_REQUEST
    ),


    // Push //
    PUSH_TOKEN_INVALID(
        "PUSH_001",
        "error.push_token_invalid",
        HttpStatus.BAD_REQUEST
    ),
    PUSH_TOKEN_SAVE_FAILED(
        "PUSH_002", "error.push_token_save_failed",
        HttpStatus.INTERNAL_SERVER_ERROR
    ),

    // TERM //
    REQUIRED_TERM_NOT_AGREED(
        "TERM_001",
        "error.not_agreed_required_term",
        HttpStatus.BAD_REQUEST
    )

}