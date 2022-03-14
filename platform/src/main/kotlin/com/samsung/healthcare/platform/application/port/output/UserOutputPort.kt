package com.samsung.healthcare.platform.application.port.output

import com.samsung.healthcare.platform.adapter.persistence.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserOutputPort {
    suspend fun save(): Unit
    suspend fun findAll(): Flow<UserEntity>
}
