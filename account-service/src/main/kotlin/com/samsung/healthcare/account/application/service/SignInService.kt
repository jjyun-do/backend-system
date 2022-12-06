package com.samsung.healthcare.account.application.service

import com.samsung.healthcare.account.application.port.input.SignInCommand
import com.samsung.healthcare.account.application.port.input.SignInResponse
import com.samsung.healthcare.account.application.port.input.SignInUseCase
import com.samsung.healthcare.account.application.port.output.AuthServicePort
import com.samsung.healthcare.account.application.port.output.TokenStoragePort
import com.samsung.healthcare.account.domain.Account
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class SignInService(
    private val authServicePort: AuthServicePort,
    private val tokenService: TokenService,
    private val refreshTokenPort: TokenStoragePort
) : SignInUseCase {
    override fun signIn(signInCommand: SignInCommand): Mono<SignInResponse> =
        authServicePort.signIn(signInCommand.email, signInCommand.password)
            .flatMap { account -> generateToken(account) }

    private fun generateToken(account: Account): Mono<SignInResponse> =
        tokenService.generateToken(account)
            .flatMap { token ->
                refreshTokenPort.save(token)
                    .thenReturn(SignInResponse(account, token.accessToken, token.refreshToken))
            }
}
