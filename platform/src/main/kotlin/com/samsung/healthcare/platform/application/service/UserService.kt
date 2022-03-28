package com.samsung.healthcare.platform.application.service

import com.samsung.healthcare.platform.application.port.input.RegisterUserCommand
import com.samsung.healthcare.platform.application.port.input.UserInputPort
import com.samsung.healthcare.platform.application.port.output.UserOutputPort
import com.samsung.healthcare.platform.domain.Email
import com.samsung.healthcare.platform.domain.User
import com.samsung.healthcare.platform.domain.User.UserId
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

    override suspend fun getUserById(id: UserId): User? {
        return userOutputPort.findById(id)
    }

    override suspend fun registerUser(command: RegisterUserCommand): UserId =
        userOutputPort.insert(
            User.newUser(command.email, command.sub, command.provider)
        )

    override suspend fun deleteUser() {
        TODO("Not yet implemented")
    }

    override suspend fun existsByEmail(email: Email): Boolean =
        userOutputPort.existsByEmail(email)
}
