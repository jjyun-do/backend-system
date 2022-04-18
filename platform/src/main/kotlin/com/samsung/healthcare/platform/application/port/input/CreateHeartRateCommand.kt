package com.samsung.healthcare.platform.application.port.input

import com.samsung.healthcare.platform.domain.User
import java.time.LocalDateTime

data class CreateHeartRateCommand(
    val userId: User.UserId,
    val time: LocalDateTime,
    val bpm: Long,
)
