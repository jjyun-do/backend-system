package com.samsung.healthcare.platform.domain.healthdata

import com.samsung.healthcare.platform.domain.User.UserId

abstract class HealthData(
    open val id: HealthDataId?,
    open val userId: UserId,
    val type: HealthDataType,
) {
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

    enum class HealthDataType(val type: String) {
        HEART_RATE("heart-rate"),
        STEPS("steps");

        companion object {
            private val stringToType = HealthDataType.values().associateBy(HealthDataType::type)
            fun fromString(type: String) = stringToType[type]
        }
    }
}
