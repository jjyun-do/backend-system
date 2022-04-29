package com.samsung.healthcare.platform.application.port.output

import com.samsung.healthcare.platform.domain.healthdata.HealthData.HealthDataType
import java.time.LocalDateTime

data class LoadIntervalDataCommand(
    val type: HealthDataType,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
)
