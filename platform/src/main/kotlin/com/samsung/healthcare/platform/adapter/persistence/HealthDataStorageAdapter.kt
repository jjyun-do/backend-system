package com.samsung.healthcare.platform.adapter.persistence

import com.samsung.healthcare.platform.adapter.persistence.entity.healthdata.HealthDataEntity
import com.samsung.healthcare.platform.adapter.persistence.entity.healthdata.toEntity
import com.samsung.healthcare.platform.application.exception.NotFoundException
import com.samsung.healthcare.platform.application.port.output.CreateHealthDataPort
import com.samsung.healthcare.platform.application.port.output.LoadHealthDataCommand
import com.samsung.healthcare.platform.application.port.output.LoadHealthDataPort
import com.samsung.healthcare.platform.domain.healthdata.HealthData
import com.samsung.healthcare.platform.domain.healthdata.HealthData.HealthDataId
import com.samsung.healthcare.platform.domain.healthdata.HealthData.HealthDataType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Component

@Component
class HealthDataStorageAdapter(
    heartRateRepository: HeartRateRepository,
) : CreateHealthDataPort, LoadHealthDataPort {
    private val typeToRepository: Map<HealthDataType, HealthDataRepository<HealthDataEntity>> =
        mapOf(
            HealthDataType.HEART_RATE to (heartRateRepository as HealthDataRepository<HealthDataEntity>),
        )

    override suspend fun create(healthData: HealthData): HealthDataId {
        return HealthDataId.from(
            typeToRepository[healthData.type]?.save(healthData.toEntity())?.id
        )
    }

    override suspend fun findByTimeBetween(command: LoadHealthDataCommand): Flow<HealthData> {
        val repo = typeToRepository[command.type]
            ?: throw NotFoundException("The healthData type ${command.type} does not exist.")

        return repo.findByTimeBetween(command.startDate, command.endDate).map { it.toDomain() }
    }
}
