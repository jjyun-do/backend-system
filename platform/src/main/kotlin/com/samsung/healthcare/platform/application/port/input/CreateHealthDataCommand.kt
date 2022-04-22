package com.samsung.healthcare.platform.application.port.input

import com.samsung.healthcare.platform.domain.User
import com.samsung.healthcare.platform.domain.healthdata.HealthData.HealthDataType

data class CreateHealthDataCommand(
    val type: HealthDataType,
    val userId: User.UserId,
    val data: Map<String, Any>,
)
