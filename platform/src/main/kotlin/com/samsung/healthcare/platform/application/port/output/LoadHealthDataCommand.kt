package com.samsung.healthcare.platform.application.port.output

import com.samsung.healthcare.platform.domain.healthdata.HealthData.HealthDataId
import com.samsung.healthcare.platform.domain.healthdata.HealthData.HealthDataType
import java.time.LocalDateTime

data class LoadHealthDataCommand(
    val type: HealthDataType,
    val id: HealthDataId,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
)
