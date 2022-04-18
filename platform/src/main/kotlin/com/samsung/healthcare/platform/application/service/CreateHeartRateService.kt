package com.samsung.healthcare.platform.application.service

import com.samsung.healthcare.platform.application.port.input.CreateHeartRateCommand
import com.samsung.healthcare.platform.application.port.input.CreateHeartRateUseCase
import com.samsung.healthcare.platform.application.port.output.CreateHeartRatePort
import com.samsung.healthcare.platform.domain.datatype.HeartRate.HeartRateId
import org.springframework.stereotype.Service

@Service
class CreateHeartRateService(
    private val createHeartRatePort: CreateHeartRatePort,
) : CreateHeartRateUseCase {
    override suspend fun registerHeartRate(command: CreateHeartRateCommand): HeartRateId {
        TODO("Not yet implemented")
    }
}
