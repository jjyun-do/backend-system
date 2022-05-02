package com.samsung.healthcare.platform.domain.healthdata

import com.samsung.healthcare.platform.domain.User
import java.time.LocalDateTime

data class Steps(
    override val id: HealthDataId?,
    override val userId: User.UserId,
    override val startTime: LocalDateTime,
    override val endTime: LocalDateTime,
    val count: Long,
) : IntervalData(id, userId, startTime, endTime, HealthDataType.STEPS) {
    companion object {
        fun newSteps(
            userId: User.UserId,
            startTime: LocalDateTime,
            endTime: LocalDateTime,
            steps: Long
        ): Steps = Steps(null, userId, startTime, endTime, steps)
    }
}
