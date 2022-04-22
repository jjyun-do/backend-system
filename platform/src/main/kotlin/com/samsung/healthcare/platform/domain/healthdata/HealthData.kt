package com.samsung.healthcare.platform.domain.healthdata

import com.samsung.healthcare.platform.domain.User
import java.time.LocalDateTime

abstract class HealthData(
    open val id: HealthDataId?,
    open val userId: User.UserId,
    open val time: LocalDateTime,
) {
    companion object {
        val StringToHealthData: Map<String, HealthDataType> = mapOf(
            "heart-rate" to HealthDataType.HeartRate,
        )
    }

    data class HealthDataId private constructor(val value: Int) {
        companion object {
            fun from(value: Int?): HealthDataId {
                requireNotNull(value)
                require(0 < value)
                return HealthDataId(value)
            }
        }

        override fun toString(): String =
            value.toString()
    }

    abstract fun type(): HealthDataType

    enum class HealthDataType {
        HeartRate,
    }
}
