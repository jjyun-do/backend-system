package com.samsung.healthcare.platform.adapter.persistence

import com.samsung.healthcare.platform.adapter.persistence.entity.healthdata.HealthDataEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.time.LocalDateTime

@NoRepositoryBean
interface HealthDataRepository<T : HealthDataEntity> : CoroutineCrudRepository<T, Int> {
    suspend fun findByTimeBetween(startDate: LocalDateTime, endDate: LocalDateTime): Flow<T>
}
