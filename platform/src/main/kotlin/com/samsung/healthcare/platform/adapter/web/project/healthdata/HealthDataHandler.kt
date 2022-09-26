package com.samsung.healthcare.platform.adapter.web.project.healthdata

import com.samsung.healthcare.platform.adapter.web.context.ContextHolder.getFirebaseToken
import com.samsung.healthcare.platform.application.port.input.project.UpdateUserProfileLastSyncedTimeUseCase
import com.samsung.healthcare.platform.application.port.input.project.healthdata.SaveHealthDataUseCase
import com.samsung.healthcare.platform.domain.project.UserProfile.UserId
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.buildAndAwait

@Component
class HealthDataHandler(
    private val createHealthDataUseCase: SaveHealthDataUseCase,
    private val updateUserProfileLastSyncedTimeUseCase: UpdateUserProfileLastSyncedTimeUseCase,
) {
    suspend fun createHealthData(req: ServerRequest): ServerResponse {
        val userId = UserId.from(getFirebaseToken().uid)
        updateUserProfileLastSyncedTimeUseCase.updateLastSyncedTime(userId)
        // TODO run async : fire and forget
        createHealthDataUseCase.saveHealthData(
            userId,
            req.awaitBody()
        )

        return ServerResponse.accepted().buildAndAwait()
    }
}
