package com.samsung.healthcare.platform.adapter.persistence.entity.healthdata

import com.samsung.healthcare.platform.domain.healthdata.HealthData
import com.samsung.healthcare.platform.domain.healthdata.HealthData.HealthDataType
import com.samsung.healthcare.platform.domain.healthdata.HeartRate
import org.springframework.data.annotation.Id
import java.time.LocalDateTime

abstract class HealthDataEntity(
    @Id
    open val id: Int? = null,
    open val userId: String,
    open val time: LocalDateTime,
) {
    abstract fun toDomain(): HealthData
}

fun HealthData.toEntity(): HealthDataEntity =
    when (this.type) {
        HealthDataType.HEART_RATE -> (this as HeartRate).toEntity()
    }
