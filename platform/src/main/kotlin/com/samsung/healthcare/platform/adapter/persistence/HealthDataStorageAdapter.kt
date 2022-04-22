package com.samsung.healthcare.platform.adapter.persistence

import com.samsung.healthcare.platform.application.port.output.CreateHealthDataPort
import com.samsung.healthcare.platform.application.port.output.LoadHealthDataCommand
import com.samsung.healthcare.platform.application.port.output.LoadHealthDataPort
import com.samsung.healthcare.platform.domain.healthdata.HealthData
import com.samsung.healthcare.platform.domain.healthdata.HealthData.HealthDataId
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Component

@Component
class HealthDataStorageAdapter(
    private val heartRateRepository: HeartRateRepository,
) : CreateHealthDataPort, LoadHealthDataPort {
    override suspend fun create(healthData: HealthData): HealthDataId {
        TODO("Not yet implemented")
    }

    override suspend fun findByTimeBetween(command: LoadHealthDataCommand): Flow<HealthData> {
        TODO("Not yet implemented")
    }
}
