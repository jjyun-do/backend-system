package com.samsung.healthcare.platform.domain.datatype

import com.samsung.healthcare.platform.domain.User
import java.time.LocalDateTime

data class HeartRate(
    val id: HeartRateId?,
    val userId: User.UserId,
    val time: LocalDateTime,
    val bpm: Long,
) {
    companion object {
        fun newHeartRate(): HeartRate =
            TODO("Not yet implemented")
    }

    data class HeartRateId private constructor(val value: Int) {
        companion object {
            fun from(value: Int?): HeartRateId {
                requireNotNull(value)
                require(0 < value)
                return HeartRateId(value)
            }
        }

        override fun toString(): String =
            value.toString()
    }
}
