package com.samsung.healthcare.platform.application.service.project.task

import com.samsung.healthcare.platform.application.port.input.project.task.UpdateTaskCommand
import com.samsung.healthcare.platform.application.port.input.project.task.UpdateTaskUseCase
import com.samsung.healthcare.platform.application.port.output.project.task.ItemOutputPort
import com.samsung.healthcare.platform.application.port.output.project.task.TaskOutputPort
import com.samsung.healthcare.platform.domain.project.task.Item
import com.samsung.healthcare.platform.domain.project.task.RevisionId
import com.samsung.healthcare.platform.domain.project.task.Task
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateTaskService(
    private val taskOutputPort: TaskOutputPort,
    private val itemOutputPort: ItemOutputPort
) : UpdateTaskUseCase {
    @Transactional
    override suspend fun updateTask(
        id: String,
        revisionId: RevisionId,
        command: UpdateTaskCommand
    ) {
        taskOutputPort.update(
            Task(revisionId, id, command.properties, command.status)
        ).let { task ->
            requireNotNull(task.revisionId)
            itemOutputPort.update(
                task.revisionId.value,
                command.items.map {
                    Item.newItem(task, it.contents, it.type, it.sequence)
                }
            )
        }
    }
}
