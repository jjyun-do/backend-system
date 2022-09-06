package com.samsung.healthcare.platform.adapter.persistence.repository.project.task

import com.samsung.healthcare.platform.adapter.persistence.entity.project.task.toEntity
import com.samsung.healthcare.platform.application.port.output.TaskOutputPort
import com.samsung.healthcare.platform.domain.project.task.Task
import org.springframework.stereotype.Component

@Component
class TaskDatabaseAdapter(
    private val taskRepository: TaskRepository
) : TaskOutputPort {
    override suspend fun create(task: Task): Task =
        taskRepository.save(task.toEntity()).toDomain()

    override suspend fun update(task: Task): Task {
        requireNotNull(task.revisionId)
        return taskRepository.findById(task.revisionId.value)?.copy(
            properties = task.properties,
            status = task.status.name,
        ).let {
            requireNotNull(it)
            taskRepository.save(it).toDomain()
        }
    }
}
