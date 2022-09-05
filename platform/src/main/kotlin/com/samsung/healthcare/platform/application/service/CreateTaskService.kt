package com.samsung.healthcare.platform.application.service

import com.samsung.healthcare.platform.application.port.input.CreateTaskResponse
import com.samsung.healthcare.platform.application.port.input.CreateTaskUseCase
import com.samsung.healthcare.platform.application.port.output.TaskOutputPort
import com.samsung.healthcare.platform.domain.project.task.Task
import org.springframework.stereotype.Service

@Service
class CreateTaskService(
    private val taskOutputPort: TaskOutputPort
) : CreateTaskUseCase {
    override suspend fun createTask(): CreateTaskResponse =
        taskOutputPort.create(
            Task.newTask()
        ).let {
            CreateTaskResponse(
                it.revisionId?.value,
                it.id
            )
        }
}
