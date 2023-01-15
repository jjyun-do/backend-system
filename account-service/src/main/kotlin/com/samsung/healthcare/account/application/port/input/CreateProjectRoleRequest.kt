package com.samsung.healthcare.account.application.port.input

data class CreateProjectRoleRequest(
    val accountId: String,
    val projectId: String
) {
    init {
        require(accountId.isNotBlank())
        require(projectId.isNotBlank())
    }
}
