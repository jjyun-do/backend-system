package com.samsung.healthcare.account.application.service

import com.samsung.healthcare.account.application.port.input.VerifyEmailUseCase
import com.samsung.healthcare.account.application.port.output.AuthServicePort
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class VerifyEmailService(
    private val authServicePort: AuthServicePort,
) : VerifyEmailUseCase {
    override fun verifyEmail(token: String): Mono<Void> =
        authServicePort.verifyEmail(token)
}
