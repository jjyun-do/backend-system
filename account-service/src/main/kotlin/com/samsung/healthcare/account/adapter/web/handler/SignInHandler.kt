package com.samsung.healthcare.account.adapter.web.handler

import com.samsung.healthcare.account.application.port.input.SignInCommand
import com.samsung.healthcare.account.application.port.input.SignInUseCase
import com.samsung.healthcare.account.domain.Email
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono

@Component
class SignInHandler(
    private val signInUseCase: SignInUseCase
) {

    fun signIn(req: ServerRequest): Mono<ServerResponse> =
        req.bodyToMono<SignInRequest>()
            .flatMap {
                signInUseCase.signIn(
                    SignInCommand(Email(it.email), it.password)
                )
            }
            .flatMap { ServerResponse.ok().bodyValue(it) }

    data class SignInRequest(val email: String, val password: String)
}
