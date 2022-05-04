package com.samsung.healthcare.platform.adapter.persistence

import com.samsung.healthcare.platform.adapter.persistence.entity.healthdata.HealthDataEntity
import com.samsung.healthcare.platform.application.port.output.CreateIntervalDataPort
import com.samsung.healthcare.platform.application.port.output.LoadIntervalDataCommand
import com.samsung.healthcare.platform.application.port.output.LoadIntervalDataPort
import com.samsung.healthcare.platform.domain.healthdata.HealthData
import com.samsung.healthcare.platform.domain.healthdata.HealthData.HealthDataId
import com.samsung.healthcare.platform.domain.healthdata.IntervalData
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Component

@Component
class IntervalDataStorageAdapter() : CreateIntervalDataPort, LoadIntervalDataPort {
    private val typeToRepository: Map<HealthData.HealthDataType, IntervalDataRepository<HealthDataEntity>> =
        mapOf()

    override suspend fun create(data: List<IntervalData>): Flow<HealthDataId> {
        TODO("Not yet implemented")
    }

    override suspend fun findByPeriod(command: LoadIntervalDataCommand): Flow<HealthData> {
        TODO("Not yet implemented")
    }
}
