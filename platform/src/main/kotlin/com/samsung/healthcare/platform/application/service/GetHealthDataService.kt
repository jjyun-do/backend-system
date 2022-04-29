package com.samsung.healthcare.platform.application.service

import com.samsung.healthcare.platform.application.port.input.GetHealthDataCommand
import com.samsung.healthcare.platform.application.port.input.GetHealthDataQuery
import com.samsung.healthcare.platform.application.port.output.LoadIntervalDataPort
import com.samsung.healthcare.platform.application.port.output.LoadSampleDataPort
import com.samsung.healthcare.platform.domain.healthdata.HealthData
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class GetHealthDataService(
    private val loadSampleDataPort: LoadSampleDataPort,
    private val loadIntervalDataPort: LoadIntervalDataPort,
) : GetHealthDataQuery {
    override suspend fun findByPeriod(command: GetHealthDataCommand): Flow<HealthData> {
        TODO("Not yet implemented")
    }
}
