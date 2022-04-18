package com.samsung.healthcare.platform.application.port.input

import com.samsung.healthcare.platform.domain.datatype.HeartRate

interface CreateHeartRateUseCase {
    suspend fun registerHeartRate(command: CreateHeartRateCommand): HeartRate.HeartRateId
}
