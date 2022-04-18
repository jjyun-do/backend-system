package com.samsung.healthcare.platform.application.service

import com.samsung.healthcare.platform.application.port.input.GetHeartRateQuery
import com.samsung.healthcare.platform.application.port.output.CreateHeartRatePort
import com.samsung.healthcare.platform.domain.datatype.HeartRate
import com.samsung.healthcare.platform.domain.datatype.HeartRate.HeartRateId
import org.springframework.stereotype.Service

@Service
class GetHeartRateService(
    private val createHeartRatePort: CreateHeartRatePort,
) : GetHeartRateQuery {
    override suspend fun findHeartRateById(id: HeartRateId): HeartRate {
        TODO("Not yet implemented")
    }
}
