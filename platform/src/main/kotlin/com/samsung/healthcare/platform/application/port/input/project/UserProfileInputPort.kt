package com.samsung.healthcare.platform.application.port.input.project

import com.samsung.healthcare.platform.application.port.input.CreateUserCommand
import com.samsung.healthcare.platform.application.port.input.UpdateUserCommand

interface UserProfileInputPort {
    suspend fun registerUser(command: CreateUserCommand)

    suspend fun updateUser(requestedUserId: String, command: UpdateUserCommand)
}
