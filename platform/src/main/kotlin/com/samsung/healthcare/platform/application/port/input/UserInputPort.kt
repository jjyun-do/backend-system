package com.samsung.healthcare.platform.application.port.input

import com.samsung.healthcare.platform.domain.Email
import com.samsung.healthcare.platform.domain.User
import com.samsung.healthcare.platform.domain.User.UserId
import kotlinx.coroutines.flow.Flow

interface UserInputPort {
    suspend fun getUser(): User
    fun getUsers(): Flow<User>
    suspend fun getUserById(id: UserId): User?
    suspend fun registerUser(command: RegisterUserCommand): UserId
    suspend fun deleteUser()
    suspend fun existsByEmail(email: Email): Boolean
}
