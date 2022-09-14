package com.samsung.healthcare.platform.adapter.persistence.project.task

import com.samsung.healthcare.platform.adapter.persistence.entity.project.task.TaskEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.time.LocalDateTime

interface TaskRepository : CoroutineCrudRepository<TaskEntity, Int> {
    suspend fun findByCreatedAtGreaterThanEqualAndCreatedAtLessThanEqual(
        startTime: LocalDateTime,
        endTime: LocalDateTime,
    ): Flow<TaskEntity>

    suspend fun findByCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualAndStatus(
        startTime: LocalDateTime,
        endTime: LocalDateTime,
        status: String,
    ): Flow<TaskEntity>

    suspend fun findByIdIn(id: List<String>): Flow<TaskEntity>
}
