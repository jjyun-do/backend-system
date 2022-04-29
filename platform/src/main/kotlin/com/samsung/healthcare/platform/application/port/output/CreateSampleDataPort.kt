package com.samsung.healthcare.platform.application.port.output

import com.samsung.healthcare.platform.domain.healthdata.HealthData.HealthDataId
import com.samsung.healthcare.platform.domain.healthdata.SampleData
import kotlinx.coroutines.flow.Flow

interface CreateSampleDataPort {
    suspend fun create(data: Flow<SampleData>): Flow<HealthDataId>
}
