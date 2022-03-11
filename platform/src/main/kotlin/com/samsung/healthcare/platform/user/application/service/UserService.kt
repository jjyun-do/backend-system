package com.samsung.healthcare.platform.user.application.service

import com.samsung.healthcare.platform.user.application.port.input.UserUseCase
import com.samsung.healthcare.platform.user.application.port.output.UserPort
import com.samsung.healthcare.platform.user.domain.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userPort: UserPort,
) : UserUseCase {
    override suspend fun getUser(): User {
        TODO("Not yet implemented")
    }

    override suspend fun getUsers(): Flow<User> {
        return userPort.findAll().map { it.toDomain() }
    }

    override suspend fun registerUser(): User {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser() {
        TODO("Not yet implemented")
    }
}
