package com.samsung.healthcare.platform.application.port.input

interface UserProfileInputPort {
    suspend fun registerUser(command: CreateUserCommand)
}
