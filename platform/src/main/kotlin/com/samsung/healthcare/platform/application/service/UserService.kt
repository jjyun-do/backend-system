package com.samsung.healthcare.platform.application.service

import com.samsung.healthcare.platform.application.port.input.UserInputPort
import com.samsung.healthcare.platform.application.port.output.UserOutputPort
import com.samsung.healthcare.platform.domain.User
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userOutputPort: UserOutputPort,
) : UserInputPort {
    override suspend fun getUser(): User {
        TODO("Not yet implemented")
    }

    override fun getUsers(): Flow<User> {
        return userOutputPort.findAll()
    }

    override suspend fun getUserById(id: String): User? {
        return userOutputPort.findById(id)
    }

    override suspend fun registerUser(): User {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser() {
        TODO("Not yet implemented")
    }
}
