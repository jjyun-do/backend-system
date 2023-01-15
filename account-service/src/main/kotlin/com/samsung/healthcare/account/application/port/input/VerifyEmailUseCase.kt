package com.samsung.healthcare.account.application.port.input

import reactor.core.publisher.Mono

interface VerifyEmailUseCase {
    fun verifyEmail(token: String): Mono<VerifyEmailResponse>
}
