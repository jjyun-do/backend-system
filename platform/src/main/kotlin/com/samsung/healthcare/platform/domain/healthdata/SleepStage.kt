package com.samsung.healthcare.platform.domain.healthdata

import com.samsung.healthcare.platform.domain.User
import java.time.LocalDateTime

data class SleepStage(
    override val id: HealthDataId?,
    override val userId: User.UserId,
    override val startTime: LocalDateTime,
    override val endTime: LocalDateTime,
    val stage: String,
) : IntervalData(id, userId, startTime, endTime, HealthDataType.SLEEP_STAGE) {
    companion object {
        fun newSleepStage(
            userId: User.UserId,
            startTime: LocalDateTime,
            endTime: LocalDateTime,
            stage: String,
        ): SleepStage = SleepStage(null, userId, startTime, endTime, stage)
    }
}
