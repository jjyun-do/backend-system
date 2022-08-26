package com.samsung.healthcare.dataqueryservice.application.exception

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.sql.SQLDataException
import java.sql.SQLInvalidAuthorizationSpecException
import java.sql.SQLNonTransientException
import java.sql.SQLSyntaxErrorException

@RestControllerAdvice
class GlobalExceptionHandler(
    @Value("#{environment.getProperty('debug') != null && environment.getProperty('debug') != 'false'}")
    private val isDebug: Boolean,
) {
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(SQLInvalidAuthorizationSpecException::class)
    fun handleSQLInvalidAuthorizationSpecException(e: SQLInvalidAuthorizationSpecException) = ErrorResponse(
        message = "Permission denied.",
        code = e.sqlState,
        reason = if (isDebug) e.message else null
    )

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SQLDataException::class)
    fun handleSQLDataException(e: SQLDataException) = ErrorResponse(
        message = "Invalid data access attempted.",
        code = e.sqlState,
        reason = if (isDebug) e.message else null
    )

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SQLSyntaxErrorException::class)
    fun handleSQLSyntaxErrorException(e: SQLSyntaxErrorException) = ErrorResponse(
        message = "Invalid SQL syntax.",
        code = e.sqlState,
        reason = if (isDebug) e.message else null
    )

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SQLNonTransientException::class)
    fun handleSQLNonTransientException(e: SQLNonTransientException) = ErrorResponse(
        message = "SQL Non-Transient Exception occurred.",
        code = e.sqlState,
        reason = if (isDebug) e.message else null
    )

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException::class)
    fun handleException(e: RuntimeException) = ErrorResponse(
        message = "Internal server error occurred. Please try again later.",
        reason = if (isDebug) e.message else null
    )
}
