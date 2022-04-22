package com.samsung.healthcare.platform.adapter.persistence

import com.samsung.healthcare.platform.adapter.persistence.entity.healthdata.HeartRateEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.time.LocalDateTime

interface HeartRateRepository : CoroutineCrudRepository<HeartRateEntity, Int> {
    suspend fun findByTimeBetween(startDate: LocalDateTime, endDate: LocalDateTime): Flow<HeartRateEntity>
}
