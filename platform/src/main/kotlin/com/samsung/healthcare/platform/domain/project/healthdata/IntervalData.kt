package com.samsung.healthcare.platform.domain.project.healthdata

import java.time.Instant

abstract class IntervalData(
    id: HealthDataId?,
    open val startTime: Instant,
    open val endTime: Instant,
    type: HealthDataType,
) : HealthData(id, type)
