package com.withcalendar.application.exception.business

import com.withcalendar.application.exception.ApplicationException
import com.withcalendar.application.exception.ErrorCode

abstract class BusinessException(
    val errorCode: ErrorCode,
    val detail: Any? = null,
    val customMessage: String? = null,
    message: String = customMessage ?: errorCode.messageKey
) : ApplicationException(message)

