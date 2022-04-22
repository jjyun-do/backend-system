package com.samsung.healthcare.platform.application.port.output

import com.samsung.healthcare.platform.domain.healthdata.HealthData
import com.samsung.healthcare.platform.domain.healthdata.HealthData.HealthDataId

interface CreateHealthDataPort {
    suspend fun create(healthData: HealthData): HealthDataId
}
