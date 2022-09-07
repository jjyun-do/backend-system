package com.samsung.healthcare.platform.application.port.input

import java.time.LocalDateTime

data class GetTaskCommand(
    val startTime: LocalDateTime?,
    val endTime: LocalDateTime?,
    val status: String?,
)
