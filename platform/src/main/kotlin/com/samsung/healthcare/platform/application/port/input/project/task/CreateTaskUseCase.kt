package com.samsung.healthcare.platform.application.port.input.project.task

interface CreateTaskUseCase {
    suspend fun createTask(projectId: String, command: CreateTaskCommand): CreateTaskResponse
}
