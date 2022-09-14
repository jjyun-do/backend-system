package com.samsung.healthcare.platform.adapter.persistence.entity.project.healthdata

import java.time.LocalDateTime

abstract class SampleDataEntity(
    id: Int? = null,
    userId: String,
    open val time: LocalDateTime,
) : HealthDataEntity(id, userId)
