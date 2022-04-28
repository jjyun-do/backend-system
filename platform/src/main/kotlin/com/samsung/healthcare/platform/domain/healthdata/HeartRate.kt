package com.samsung.healthcare.platform.domain.healthdata

import com.samsung.healthcare.platform.domain.User
import java.time.LocalDateTime

data class HeartRate(
    override val id: HealthDataId?,
    override val userId: User.UserId,
    override val time: LocalDateTime,
    val bpm: Long,
) : HealthData(id, userId, time, HealthDataType.HEART_RATE) {
    companion object {
        fun newHeartRate(userId: User.UserId, bpm: Long): HeartRate =
            HeartRate(null, userId, LocalDateTime.now(), bpm)
    }
}
