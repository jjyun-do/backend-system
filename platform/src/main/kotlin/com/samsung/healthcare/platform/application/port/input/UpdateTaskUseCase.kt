package com.samsung.healthcare.platform.application.port.input

interface UpdateTaskUseCase {
    suspend fun updateTask(command: UpdateTaskCommand)
}
