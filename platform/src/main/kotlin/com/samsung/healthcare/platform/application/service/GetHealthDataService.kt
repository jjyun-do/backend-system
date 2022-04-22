package com.samsung.healthcare.platform.application.service

import com.samsung.healthcare.platform.application.port.input.GetHealthDataCommand
import com.samsung.healthcare.platform.application.port.input.GetHealthDataQuery
import com.samsung.healthcare.platform.application.port.output.LoadHealthDataPort
import com.samsung.healthcare.platform.domain.healthdata.HealthData
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class GetHealthDataService(
    private val loadHealthDataPort: LoadHealthDataPort,
) : GetHealthDataQuery {
    override suspend fun findByTimeBetween(command: GetHealthDataCommand): Flow<HealthData> {
        TODO("Not yet implemented")
    }
}
