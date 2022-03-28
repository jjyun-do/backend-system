package com.samsung.healthcare.platform.application.port.output

import com.samsung.healthcare.platform.domain.Email
import com.samsung.healthcare.platform.domain.User
import com.samsung.healthcare.platform.domain.User.UserId
import kotlinx.coroutines.flow.Flow

interface UserOutputPort {
    suspend fun insert(user: User): UserId
    suspend fun update(user: User): User
    fun findAll(): Flow<User>
    suspend fun findById(id: UserId): User?
    suspend fun existsByEmail(email: Email): Boolean
    suspend fun delete(id: UserId): Boolean
}
