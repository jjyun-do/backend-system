package com.samsung.healthcare.platform.application.port.input

import com.samsung.healthcare.platform.domain.User.UserId
import com.samsung.healthcare.platform.domain.healthdata.HealthData.HealthDataType
import kotlinx.coroutines.flow.Flow

data class CreateHealthDataCommand(
    val type: HealthDataType,
    val userId: UserId,
    val data: Flow<Map<String, Any>>
)
