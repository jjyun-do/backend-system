package com.samsung.healthcare.platform.application.port.input

import com.samsung.healthcare.platform.domain.healthdata.HealthData
import kotlinx.coroutines.flow.Flow

interface GetHealthDataQuery {
    suspend fun findByTimeBetween(command: GetHealthDataCommand): Flow<HealthData>
}
