package com.samsung.healthcare.account.application.port.input

data class PasswordResetCommand(
    val resetToken: String,
    val newPassword: String
)
