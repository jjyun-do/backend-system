package com.samsung.healthcare.platform.application.port.input

interface CreateTaskUseCase {
    suspend fun createTask(): CreateTaskResponse
}
