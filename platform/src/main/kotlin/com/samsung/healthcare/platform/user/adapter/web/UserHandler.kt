package com.samsung.healthcare.platform.user.adapter.web

import com.samsung.healthcare.platform.user.application.port.input.UserUseCase
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyAndAwait

@Component
@Suppress("UnusedPrivateMember")
class UserHandler(
    private val useCase: UserUseCase,
) {
    suspend fun getUsers(req: ServerRequest): ServerResponse {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyAndAwait(useCase.getUsers())
    }
}
