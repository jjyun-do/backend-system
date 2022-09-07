package com.samsung.healthcare.account.application.service

import com.samsung.healthcare.account.application.port.input.ListUserUseCase
import com.samsung.healthcare.account.application.port.output.AuthServicePort
import com.samsung.healthcare.account.domain.Account
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ListUserService(
    private val authService: AuthServicePort
) : ListUserUseCase {
    override fun listAllUsers(): Mono<List<Account>> =
        authService.listUsers()
}
