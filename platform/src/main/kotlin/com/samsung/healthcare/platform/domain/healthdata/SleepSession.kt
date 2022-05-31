package com.samsung.healthcare.platform.domain.healthdata

import com.samsung.healthcare.platform.domain.User
import java.time.LocalDateTime

data class SleepSession(
    override val id: HealthDataId?,
    override val userId: User.UserId,
    override val startTime: LocalDateTime,
    override val endTime: LocalDateTime,
    val title: String? = null,
    val notes: String? = null,
) : IntervalData(id, userId, startTime, endTime, HealthDataType.SLEEP_SESSION) {
    companion object {
        fun newSleepSession(
            userId: User.UserId,
            startTime: LocalDateTime,
            endTime: LocalDateTime,
            title: String? = null,
            notes: String? = null,
        ): SleepSession = SleepSession(null, userId, startTime, endTime, title, notes)
    }
}
