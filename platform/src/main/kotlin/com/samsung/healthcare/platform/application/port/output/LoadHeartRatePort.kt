package com.samsung.healthcare.platform.application.port.output

import com.samsung.healthcare.platform.domain.datatype.HeartRate
import com.samsung.healthcare.platform.domain.datatype.HeartRate.HeartRateId

interface LoadHeartRatePort {
    suspend fun findById(id: HeartRateId): HeartRate?
}
