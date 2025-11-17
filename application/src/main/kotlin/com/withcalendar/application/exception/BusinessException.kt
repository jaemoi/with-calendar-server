package com.withcalendar.application.exception

import java.lang.RuntimeException

class BusinessException(
    val errorCode: ErrorCode,
    val customMessage: String? = null,
    val detail: Any? = null
) : RuntimeException(
    customMessage ?: errorCode.messageKey
)