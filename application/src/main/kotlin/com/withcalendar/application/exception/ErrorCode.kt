package com.withcalendar.application.exception

enum class ErrorCode(
    val code: String,
    val messageKey: String,
    val status: Int
) {

    // Common //
    INVALID_INPUT("COMMON_001", "error.invalid_input", 400),
    INVALID_JSON("COMMON_002", "error.invalid_json", 400),
    INTERNAL_ERROR("COMMON_999", "error.internal_error", 500),


    // User //
    USER_NOT_FOUND("USER_001", "error.user_not_found", 404),
    USER_ALREADY_EXISTS("USER_002", "error.user_already_exists", 409),
    NICKNAME_DUPLICATED("USER_003", "error.nickname_duplicated", 409),
    INVALID_NICKNAME("USER_004", "error.invalid_nickname", 400),


    // Auth //
    UNAUTHORIZED("AUTH_001", "error.unauthorized", 401),
    FORBIDDEN("AUTH_002", "error.forbidden", 403),
    TOKEN_EXPIRED("AUTH_003", "error.token_expired", 401),
    TOKEN_INVALID("AUTH_004", "error.token_invalid", 401),


    // Appointment //
    APPOINTMENT_NOT_FOUND("APPOINTMENT_001", "error.appointment_not_found", 404),
    INVALID_TIME_RANGE("APPOINTMENT_002", "error.invalid_time_range", 400),


    // Push //
    PUSH_TOKEN_INVALID("PUSH_001", "error.push_token_invalid", 400),
    PUSH_TOKEN_SAVE_FAILED("PUSH_002", "error.push_token_save_failed", 500),

}