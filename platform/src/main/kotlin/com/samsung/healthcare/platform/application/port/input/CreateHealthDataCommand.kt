package com.samsung.healthcare.platform.application.port.input

import com.samsung.healthcare.platform.domain.User.UserId
import com.samsung.healthcare.platform.domain.healthdata.HealthData.HealthDataType

data class CreateHealthDataCommand(
    val type: HealthDataType,
    val userId: UserId,
    val data: List<Map<String, Any>>
)
