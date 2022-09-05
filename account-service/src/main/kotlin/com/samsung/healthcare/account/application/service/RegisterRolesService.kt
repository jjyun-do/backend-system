package com.samsung.healthcare.account.application.service

import com.samsung.healthcare.account.application.port.input.RegisterRolesUseCase
import com.samsung.healthcare.account.application.port.output.AuthServicePort
import com.samsung.healthcare.account.domain.Role
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class RegisterRolesService(
    private val authServicePort: AuthServicePort
) : RegisterRolesUseCase {
    override fun registerRoles(roles: Collection<Role>): Mono<Void> =
        if (roles.isEmpty()) Mono.error(IllegalArgumentException())
        else authServicePort.createRoles(roles)
}
