package com.samsung.healthcare.platform.application.port.input

import com.samsung.healthcare.platform.domain.healthdata.HealthData.HealthDataId

interface CreateHealthDataUseCase {
    suspend fun registerHealthData(command: CreateHealthDataCommand): HealthDataId
}
