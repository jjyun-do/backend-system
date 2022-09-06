package com.samsung.healthcare.platform.adapter.web

import com.samsung.healthcare.platform.adapter.web.common.getRevisionId
import com.samsung.healthcare.platform.adapter.web.common.getTaskId
import com.samsung.healthcare.platform.application.port.input.CreateTaskUseCase
import com.samsung.healthcare.platform.application.port.input.UpdateTaskUseCase
import com.samsung.healthcare.platform.domain.project.task.RevisionId
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.buildAndAwait

@Component
class TaskHandler(
    private val createTaskUseCase: CreateTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
) {
    suspend fun createTask(req: ServerRequest): ServerResponse =
        ServerResponse
            .ok()
            .bodyValue(createTaskUseCase.createTask())
            .awaitSingle()

    suspend fun updateTask(req: ServerRequest): ServerResponse {
        updateTaskUseCase.updateTask(
            req.getTaskId(),
            RevisionId.from(req.getRevisionId()),
            req.awaitBody()
        )
        return ServerResponse.noContent().buildAndAwait()
    }
}
