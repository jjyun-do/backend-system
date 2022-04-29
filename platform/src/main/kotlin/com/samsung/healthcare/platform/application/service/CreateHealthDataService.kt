package com.samsung.healthcare.platform.application.service

import com.samsung.healthcare.platform.application.port.input.CreateHealthDataCommand
import com.samsung.healthcare.platform.application.port.input.CreateHealthDataUseCase
import com.samsung.healthcare.platform.application.port.output.CreateIntervalDataPort
import com.samsung.healthcare.platform.application.port.output.CreateSampleDataPort
import com.samsung.healthcare.platform.domain.healthdata.HealthData.HealthDataId
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class CreateHealthDataService(
    private val createHealthDataPort: CreateSampleDataPort,
    private val createIntervalDataPort: CreateIntervalDataPort,
) : CreateHealthDataUseCase {
    override suspend fun registerHealthData(command: CreateHealthDataCommand): Flow<HealthDataId> {
        TODO("Not yet implemented")
    }
}
