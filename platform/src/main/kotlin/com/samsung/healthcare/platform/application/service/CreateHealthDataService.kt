package com.samsung.healthcare.platform.application.service

import com.samsung.healthcare.platform.application.port.input.CreateHealthDataCommand
import com.samsung.healthcare.platform.application.port.input.CreateHealthDataUseCase
import com.samsung.healthcare.platform.application.port.output.CreateHealthDataPort
import com.samsung.healthcare.platform.domain.healthdata.HealthData.HealthDataId
import org.springframework.stereotype.Service

@Service
class CreateHealthDataService(
    private val createHealthDataPort: CreateHealthDataPort,
) : CreateHealthDataUseCase {
    override suspend fun registerHealthData(command: CreateHealthDataCommand): HealthDataId {
        TODO("Not yet implemented")
    }
}
