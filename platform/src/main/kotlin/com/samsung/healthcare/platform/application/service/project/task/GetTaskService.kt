package com.samsung.healthcare.platform.application.service.project.task

import com.samsung.healthcare.platform.application.exception.BadRequestException
import com.samsung.healthcare.platform.application.port.input.project.task.GetTaskCommand
import com.samsung.healthcare.platform.application.port.input.project.task.GetTaskUseCase
import com.samsung.healthcare.platform.application.port.output.project.task.ItemOutputPort
import com.samsung.healthcare.platform.application.port.output.project.task.TaskOutputPort
import com.samsung.healthcare.platform.enums.TaskStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactor.asFlux
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class GetTaskService(
    private val taskOutputPort: TaskOutputPort,
    private val itemOutputPort: ItemOutputPort
) : GetTaskUseCase {
    override suspend fun findByPeriod(command: GetTaskCommand): Flow<Map<String, Any?>> {
        if (command.status != null && !TaskStatus.values().any { it.name == command.status }) {
            throw BadRequestException("Invalid TaskStatus type: ${command.status}")
        }

        return taskOutputPort.findByPeriod(
            command.startTime ?: LocalDateTime.parse("1900-01-01T00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            command.endTime ?: LocalDateTime.parse("9999-12-31T23:59", DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            command.status,
        )
            .map { it.unrollTask() }
            .flatMapConcat {
                it["items"] = itemOutputPort.findByRevisionIdAndTaskId(it["revisionId"] as Int, it["id"].toString())
                    .map { item -> item.unrollItem() }.asFlux().collectList().awaitSingle()
                flowOf(it)
            }
    }

    override suspend fun findById(id: String): Flow<Map<String, Any?>> {
        return taskOutputPort.findById(id)
            .map { it.unrollTask() }
            .flatMapConcat {
                it["items"] = itemOutputPort.findByRevisionIdAndTaskId(it["revisionId"] as Int, it["id"].toString())
                    .map { item -> item.unrollItem() }.asFlux().collectList().awaitSingle()
                flowOf(it)
            }
    }
}
