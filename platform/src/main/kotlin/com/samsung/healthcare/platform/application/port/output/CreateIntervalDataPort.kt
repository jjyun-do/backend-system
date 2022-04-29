package com.samsung.healthcare.platform.application.port.output

import com.samsung.healthcare.platform.domain.healthdata.HealthData.HealthDataId
import com.samsung.healthcare.platform.domain.healthdata.IntervalData
import kotlinx.coroutines.flow.Flow

interface CreateIntervalDataPort {
    suspend fun create(data: Flow<IntervalData>): Flow<HealthDataId>
}
