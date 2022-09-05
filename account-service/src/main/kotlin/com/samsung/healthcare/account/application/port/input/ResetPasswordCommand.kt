package com.samsung.healthcare.account.application.port.input

data class ResetPasswordCommand(
    val resetToken: String,
    val newPassword: String
) {
    init {
        require(resetToken.isNotBlank())
        require(newPassword.isNotBlank())
    }
}
