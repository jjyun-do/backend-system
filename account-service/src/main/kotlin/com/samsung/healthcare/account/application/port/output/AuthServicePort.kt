package com.samsung.healthcare.account.application.port.output

import com.samsung.healthcare.account.domain.Account
import com.samsung.healthcare.account.domain.Email
import com.samsung.healthcare.account.domain.Role
import reactor.core.publisher.Mono

interface AuthServicePort {
    fun registerNewUser(email: Email, password: String): Mono<Account>

    fun generateResetToken(accountId: String): Mono<String>

    fun resetPassword(resetToken: String, newPassword: String): Mono<Void>

    fun assignRoles(accountId: String, collection: Collection<Role>): Mono<Void>

    fun removeRolesFromAccount(accountId: String, collection: Collection<Role>): Mono<Void>

    fun createRoles(collection: Collection<Role>): Mono<Void>

    fun signIn(email: Email, password: String): Mono<Account>

    fun listUserRoles(id: String): Mono<List<Role>>
}
