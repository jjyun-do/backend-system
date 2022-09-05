package com.samsung.healthcare.platform.application.port.output

import com.samsung.healthcare.platform.domain.project.task.Task

interface TaskOutputPort {
    suspend fun create(task: Task): Task
}
