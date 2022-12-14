package com.samsung.healthcare.account.adapter.web.handler

import com.samsung.healthcare.account.application.port.input.VerifyEmailUseCase
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono

@Component
class VerifyEmailHandler(
    private val verifyEmailUseCase: VerifyEmailUseCase,
) {
    fun verifyEmail(req: ServerRequest): Mono<ServerResponse> =
        req.bodyToMono<VerifyEmailRequest>()
            .flatMap {
                verifyEmailUseCase.verifyEmail(it.token)
            }
            .then(ServerResponse.ok().build())

    data class VerifyEmailRequest(val token: String)
}
