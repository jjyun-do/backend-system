package com.samsung.healthcare.platform.application.port.input

data class CreateTaskResponse(
    val revisionId: Int?,
    val id: String,
) {
    init {
        requireNotNull(revisionId)
    }
}
