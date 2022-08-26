package com.samsung.healthcare.platform.application.port.output

import com.samsung.healthcare.platform.domain.User.UserId
import com.samsung.healthcare.platform.domain.healthdata.HealthData
import com.samsung.healthcare.platform.domain.healthdata.HealthData.HealthDataType

interface SaveHealthDataPort {
    suspend fun save(userId: UserId, type: HealthDataType, data: List<HealthData>)
}
