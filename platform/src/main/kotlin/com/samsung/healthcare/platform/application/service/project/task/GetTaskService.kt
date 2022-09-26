package com.samsung.healthcare.platform.application.service.project.task

import com.samsung.healthcare.platform.application.exception.BadRequestException
import com.samsung.healthcare.platform.application.port.input.project.task.GetTaskCommand
import com.samsung.healthcare.platform.application.port.input.project.task.GetTaskUseCase
import com.samsung.healthcare.platform.application.port.output.project.task.ItemOutputPort
import com.samsung.healthcare.platform.application.port.output.project.task.TaskOutputPort
import com.samsung.healthcare.platform.domain.project.task.Task
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
    override suspend fun findByPeriod(command: GetTaskCommand): Flow<Map<String, Any?>> =
        if (command.status != null && !TaskStatus.values().any { it.name == command.status })
            throw BadRequestException("Invalid TaskStatus type: ${command.status}")
        else if (null != command.startTime)
            byCreatedAt(command)
        else if (null != command.lastSyncTime)
            byPublishedAt(command)
        else
            throw BadRequestException("You must provide one of `start_time` / `last_sync_time`")

    private suspend fun byCreatedAt(command: GetTaskCommand): Flow<Map<String, Any?>> = convert(
        taskOutputPort.findByPeriod(
            command.startTime ?: LocalDateTime.parse("1900-01-01T00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            command.endTime ?: LocalDateTime.parse("9999-12-31T23:59", DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            command.status,
        )
    )

    private suspend fun byPublishedAt(command: GetTaskCommand): Flow<Map<String, Any?>> =
        if (command.lastSyncTime == null || command.endTime == null)
            throw BadRequestException("You must provide end time.")
        else
            convert(taskOutputPort.findByPublishedAt(command.lastSyncTime, command.endTime))

    private suspend fun convert(input: Flow<Task>): Flow<Map<String, Any?>> =
        input.map { it.unrollTask() }
            .flatMapConcat {
                it["items"] = itemOutputPort.findByRevisionIdAndTaskId(it["revisionId"] as Int, it["id"].toString())
                    .map { item -> item.unrollItem() }.asFlux().collectList().awaitSingle()
                flowOf(it)
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
