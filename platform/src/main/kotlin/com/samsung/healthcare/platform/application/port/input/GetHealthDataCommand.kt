package com.samsung.healthcare.platform.application.port.input

import com.samsung.healthcare.platform.domain.healthdata.HealthData.HealthDataType
import java.time.LocalDateTime

data class GetHealthDataCommand(
    val type: HealthDataType,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
)
