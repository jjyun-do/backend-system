package com.samsung.healthcare.platform.domain.healthdata

import com.samsung.healthcare.platform.domain.User.UserId
import java.time.LocalDateTime

abstract class IntervalData(
    id: HealthDataId?,
    userId: UserId,
    open val startTime: LocalDateTime,
    open val endTime: LocalDateTime,
    type: HealthDataType,
) : HealthData(id, userId, type)
