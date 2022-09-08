package com.samsung.healthcare.account.adapter.web.handler

import com.samsung.healthcare.account.application.port.input.ListUserUseCase
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class ListUserHandler(
    private val listUserService: ListUserUseCase
) {
    fun listUsers(req: ServerRequest): Mono<ServerResponse> =
        listUserService.listAllUsers()
            .flatMap { users ->
                ServerResponse.ok()
                    .bodyValue(
                        users.map { account ->
                            UserResponse(account.id, account.email.value, account.roles.map { it.roleName })
                        }
                    )
            }

    data class UserResponse(val id: String, val email: String, val roles: List<String>)
}
