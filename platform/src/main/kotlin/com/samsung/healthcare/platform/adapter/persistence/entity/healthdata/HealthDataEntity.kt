package com.samsung.healthcare.platform.adapter.persistence.entity.healthdata

import com.samsung.healthcare.platform.domain.healthdata.HealthData
import com.samsung.healthcare.platform.domain.healthdata.HealthData.HealthDataType
import com.samsung.healthcare.platform.domain.healthdata.HeartRate
import com.samsung.healthcare.platform.domain.healthdata.SleepStage
import com.samsung.healthcare.platform.domain.healthdata.Steps
import org.springframework.data.annotation.Id

abstract class HealthDataEntity(
    @Id
    open val id: Int? = null,
    open val userId: String,
) {
    abstract fun toDomain(): HealthData
}

fun HealthData.toEntity(): HealthDataEntity =
    when (this.type) {
        HealthDataType.HEART_RATE -> (this as HeartRate).toEntity()
        HealthDataType.SLEEP_STAGE -> (this as SleepStage).toEntity()
        HealthDataType.STEPS -> (this as Steps).toEntity()
    }
