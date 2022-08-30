package com.samsung.healthcare.account.application.port.input

import com.samsung.healthcare.account.domain.Email
import com.samsung.healthcare.account.domain.Role
import reactor.core.publisher.Mono

interface AccountServicePort {
    fun inviteUser(email: Email, roles: Collection<Role>): Mono<Void>

    fun resetPassword(passwordResetCommand: PasswordResetCommand): Mono<Void>

    fun signIn(email: Email, password: String): Mono<SignInResponse>

    fun assignRoles(accoundId: String, roles: Collection<Role>): Mono<Void>

    fun removeRolesFromAccount(accoundId: String, roles: Collection<Role>): Mono<Void>

    fun registerRoles(roles: Collection<Role>): Mono<Void>
}
