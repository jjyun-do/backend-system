package com.samsung.healthcare.dataqueryservice.application.exception

import org.springframework.http.HttpStatus
import java.sql.SQLException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SQLException::class)
    fun handleSQLException(e: SQLException) = ErrorResponse(
        code = "SQL Exception",
        message = e.message,
    )

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception) = ErrorResponse(
        code = "Exception",
        message = e.message,
    )
}
