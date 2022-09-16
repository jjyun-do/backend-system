package com.samsung.healthcare.account.adapter.web.handler

import com.samsung.healthcare.account.application.port.input.ResetPasswordCommand
import com.samsung.healthcare.account.application.port.input.ResetPasswordUseCase
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono

@Component
class ResetPasswordHandler(
    private val resetPasswordUseCase: ResetPasswordUseCase
) {
    fun resetPassword(req: ServerRequest): Mono<ServerResponse> {
        return req.bodyToMono<ResetPasswordCommand>()
            .flatMap { resetPasswordUseCase.resetPassword(it) }
            .flatMap {
                ServerResponse.ok().bodyValue(it)
            }
    }
}
