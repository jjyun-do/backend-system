package com.samsung.healthcare.platform.adapter.web

import com.samsung.healthcare.platform.adapter.web.context.ContextHolder.getFirebaseToken
import com.samsung.healthcare.platform.application.exception.BadRequestException
import com.samsung.healthcare.platform.application.port.input.UserProfileInputPort
import com.samsung.healthcare.platform.domain.project.UserProfile
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.buildAndAwait

@Component
class UserProfileHandler(
    private val inputPort: UserProfileInputPort
) {
    suspend fun registerUser(req: ServerRequest): ServerResponse {
        val firebaseToken = getFirebaseToken()

        val userProfile = req.awaitBody<UserProfile>()
        if (firebaseToken.uid != userProfile.userId) {
            throw BadRequestException()
        }

        inputPort.registerUser(userProfile)

        return ServerResponse.ok().buildAndAwait()
    }
}
