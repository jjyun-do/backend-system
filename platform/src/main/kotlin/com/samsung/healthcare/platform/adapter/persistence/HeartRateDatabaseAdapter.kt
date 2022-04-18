package com.samsung.healthcare.platform.adapter.persistence

import com.samsung.healthcare.platform.application.port.output.CreateHeartRatePort
import com.samsung.healthcare.platform.application.port.output.LoadHeartRatePort
import com.samsung.healthcare.platform.domain.datatype.HeartRate
import com.samsung.healthcare.platform.domain.datatype.HeartRate.HeartRateId
import org.springframework.stereotype.Component

@Component
class HeartRateDatabaseAdapter(
    private val heartRateRepository: HeartRateRepository,
) : CreateHeartRatePort, LoadHeartRatePort {
    override suspend fun create(heartRate: HeartRate): HeartRateId {
        TODO("Not yet implemented")
    }

    override suspend fun findById(id: HeartRate.HeartRateId): HeartRate? {
        TODO("Not yet implemented")
    }
}
