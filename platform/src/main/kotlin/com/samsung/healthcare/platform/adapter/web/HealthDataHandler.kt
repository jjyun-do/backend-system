package com.samsung.healthcare.platform.adapter.web

import com.samsung.healthcare.platform.adapter.web.context.ContextHolder.getFirebaseToken
import com.samsung.healthcare.platform.application.port.input.SaveHealthDataUseCase
import com.samsung.healthcare.platform.domain.project.UserProfile.UserId
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.buildAndAwait

@Component
class HealthDataHandler(
    private val createHealthDataUseCase: SaveHealthDataUseCase,
) {
    suspend fun createHealthData(req: ServerRequest): ServerResponse {
        // TODO run async : fire and forget
        createHealthDataUseCase.saveHealthData(
            UserId.from(getFirebaseToken().uid),
            req.awaitBody()
        )

        return ServerResponse.accepted().buildAndAwait()
    }
}
