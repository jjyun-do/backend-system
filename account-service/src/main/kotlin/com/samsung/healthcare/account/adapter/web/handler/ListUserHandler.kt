package com.samsung.healthcare.account.adapter.web.handler

import com.samsung.healthcare.account.application.port.input.ListUserUseCase
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono

@Component
class ListUserHandler(
    private val listUserService: ListUserUseCase
) {
    fun listUsers(req: ServerRequest): Mono<ServerResponse> =
        ServerResponse.ok()
            .body(listUserService.listAllUsers())
}
