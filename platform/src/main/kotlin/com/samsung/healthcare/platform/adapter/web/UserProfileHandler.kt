package com.samsung.healthcare.platform.adapter.web

import com.samsung.healthcare.platform.application.port.input.CreateUserCommand
import com.samsung.healthcare.platform.application.port.input.UserProfileInputPort
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
        val createUserCommand = req.awaitBody<CreateUserCommand>()
        inputPort.registerUser(createUserCommand)

        return ServerResponse.ok().buildAndAwait()
    }
}
