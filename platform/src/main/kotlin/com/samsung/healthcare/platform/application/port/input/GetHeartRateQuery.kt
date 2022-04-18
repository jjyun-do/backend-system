package com.samsung.healthcare.platform.application.port.input

import com.samsung.healthcare.platform.domain.datatype.HeartRate

interface GetHeartRateQuery {
    suspend fun findHeartRateById(id: HeartRate.HeartRateId): HeartRate
}
