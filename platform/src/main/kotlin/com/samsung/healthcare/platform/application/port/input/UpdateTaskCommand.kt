package com.samsung.healthcare.platform.application.port.input

import java.time.LocalDateTime

data class UpdateTaskCommand(
    val title: String,
    val description: String,
    val schedule: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val validTime: Int,
    val items: List<Map<String, Any>>,
    val condition: Map<String, Any>? = emptyMap(),
) {
    // TODO: validate
}
