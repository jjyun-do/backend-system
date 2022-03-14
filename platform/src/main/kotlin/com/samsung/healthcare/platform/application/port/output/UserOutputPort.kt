package com.samsung.healthcare.platform.application.port.output

import com.samsung.healthcare.platform.domain.User
import kotlinx.coroutines.flow.Flow

interface UserOutputPort {
    suspend fun save(): Unit
    fun findAll(): Flow<User>
}
