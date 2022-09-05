package com.samsung.healthcare.account.application.port.input

import com.samsung.healthcare.account.domain.Email
import reactor.core.publisher.Mono

interface SignInUseCase {
    fun signIn(email: Email, password: String): Mono<SignInResponse>
}
