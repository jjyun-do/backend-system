package com.samsung.healthcare.platform.adapter.persistence

import com.samsung.healthcare.platform.adapter.persistence.entity.healthdata.HealthDataEntity
import com.samsung.healthcare.platform.application.exception.NotFoundException
import com.samsung.healthcare.platform.application.port.output.CreateSampleDataPort
import com.samsung.healthcare.platform.application.port.output.LoadSampleDataCommand
import com.samsung.healthcare.platform.application.port.output.LoadSampleDataPort
import com.samsung.healthcare.platform.domain.healthdata.HealthData
import com.samsung.healthcare.platform.domain.healthdata.HealthData.HealthDataId
import com.samsung.healthcare.platform.domain.healthdata.HealthData.HealthDataType
import com.samsung.healthcare.platform.domain.healthdata.SampleData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Component

@Component
class SampleDataStorageAdapter(
    heartRateRepository: HeartRateRepository,
) : CreateSampleDataPort, LoadSampleDataPort {
    private val typeToRepository: Map<HealthDataType, SampleDataRepository<HealthDataEntity>> =
        mapOf(
            HealthDataType.HEART_RATE to (heartRateRepository as SampleDataRepository<HealthDataEntity>),
        )

    override suspend fun create(data: List<SampleData>): Flow<HealthDataId> {
        TODO("Not yet implemented")
    }

    override suspend fun findByPeriod(command: LoadSampleDataCommand): Flow<HealthData> {
        val repo = typeToRepository[command.type]
            ?: throw NotFoundException("The healthData type ${command.type} does not exist.")

        return repo.findByTimeBetween(command.startDate, command.endDate).map { it.toDomain() }
    }
}
