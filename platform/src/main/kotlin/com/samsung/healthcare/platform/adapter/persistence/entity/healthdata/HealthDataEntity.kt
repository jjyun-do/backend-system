package com.samsung.healthcare.platform.adapter.persistence.entity.healthdata

import com.samsung.healthcare.platform.domain.healthdata.HealthData

abstract class HealthDataEntity {
    abstract fun toDomain(): HealthData
}
