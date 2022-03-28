package com.samsung.healthcare.platform.application.port.input

import com.samsung.healthcare.platform.domain.Email

data class RegisterUserCommand(
    val email: Email,
    val sub: String,
    val provider: String,
)
// TODO validate
