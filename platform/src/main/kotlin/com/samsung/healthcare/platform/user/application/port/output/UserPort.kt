package com.samsung.healthcare.platform.user.application.port.output

import com.samsung.healthcare.platform.user.adapter.persistence.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserPort {
    suspend fun save(): Unit
    suspend fun findAll(): Flow<UserEntity>
}
