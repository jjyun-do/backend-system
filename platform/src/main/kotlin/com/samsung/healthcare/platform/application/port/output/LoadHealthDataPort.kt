package com.samsung.healthcare.platform.application.port.output

import com.samsung.healthcare.platform.domain.healthdata.HealthData
import kotlinx.coroutines.flow.Flow

interface LoadHealthDataPort {
    suspend fun findByTimeBetween(command: LoadHealthDataCommand): Flow<HealthData>
}
