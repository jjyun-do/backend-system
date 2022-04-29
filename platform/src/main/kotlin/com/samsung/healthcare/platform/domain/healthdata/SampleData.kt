package com.samsung.healthcare.platform.domain.healthdata

import com.samsung.healthcare.platform.domain.User.UserId
import java.time.LocalDateTime

abstract class SampleData(
    id: HealthDataId?,
    userId: UserId,
    open val time: LocalDateTime,
    type: HealthDataType,
) : HealthData(id, userId, type)
