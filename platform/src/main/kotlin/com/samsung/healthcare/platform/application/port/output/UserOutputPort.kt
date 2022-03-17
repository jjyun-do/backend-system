package com.samsung.healthcare.platform.application.port.output

import com.samsung.healthcare.platform.domain.User
import kotlinx.coroutines.flow.Flow

interface UserOutputPort {
    suspend fun insert(user: User): User
    suspend fun update(user: User): User
    fun findAll(): Flow<User>
    suspend fun findById(id: String): User?
}
