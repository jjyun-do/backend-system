package com.samsung.healthcare.platform.application.port.input

import com.samsung.healthcare.platform.domain.healthdata.HealthData.HealthDataId
import kotlinx.coroutines.flow.Flow

interface CreateHealthDataUseCase {
    suspend fun registerHealthData(command: CreateHealthDataCommand): Flow<HealthDataId>
}
