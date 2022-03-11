package com.samsung.healthcare.platform.user.application.port.input

import com.samsung.healthcare.platform.user.domain.User
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    suspend fun getUser(): User
    suspend fun getUsers(): Flow<User>
    suspend fun registerUser(): User
    suspend fun deleteUser(): Unit
}
