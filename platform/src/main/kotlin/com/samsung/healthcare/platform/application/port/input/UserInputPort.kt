package com.samsung.healthcare.platform.application.port.input

import com.samsung.healthcare.platform.domain.User
import kotlinx.coroutines.flow.Flow

interface UserInputPort {
    suspend fun getUser(): User
    fun getUsers(): Flow<User>
    suspend fun registerUser(): User
    suspend fun deleteUser(): Unit
}
