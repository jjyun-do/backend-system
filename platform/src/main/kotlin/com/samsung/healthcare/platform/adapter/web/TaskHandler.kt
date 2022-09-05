package com.samsung.healthcare.platform.adapter.web

import com.samsung.healthcare.platform.application.port.input.CreateTaskUseCase
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

@Component
class TaskHandler(
    private val createTaskUseCase: CreateTaskUseCase
) {
    suspend fun createTask(req: ServerRequest): ServerResponse =
        ServerResponse
            .ok()
            .bodyValue(createTaskUseCase.createTask())
            .awaitSingle()
}
