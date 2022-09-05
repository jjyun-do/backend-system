package com.samsung.healthcare.account.application.service

import com.samsung.healthcare.account.application.accesscontrol.Authorize
import com.samsung.healthcare.account.application.port.input.AccountServicePort
import com.samsung.healthcare.account.application.port.output.AuthServicePort
import com.samsung.healthcare.account.domain.Account
import com.samsung.healthcare.account.domain.Email
import com.samsung.healthcare.account.domain.Role
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class AccountService(
    private val authServicePort: AuthServicePort,
    private val mailService: MailService
) : AccountServicePort {

    @Authorize
    override fun inviteUser(email: Email, roles: Collection<Role>): Mono<Void> =
        // TODO chagge method that generate random password
        authServicePort.registerNewUser(email, UUID.randomUUID().toString())
            .flatMap { account ->
                Mono.zip(
                    assignRolesForNewUser(account, roles).then(Mono.just("emit-event")),
                    authServicePort.generateResetToken(account.id)
                ).map { it.t2 }
            }.flatMap { resetToken ->
                mailService.sendMail(email, resetToken)
            }

    private fun assignRolesForNewUser(account: Account, roles: Collection<Role>): Mono<Void> =
        if (roles.isEmpty()) Mono.empty()
        else assignRoles(account.id, roles)

    @Authorize
    override fun assignRoles(accountId: String, roles: Collection<Role>): Mono<Void> =
        if (roles.isEmpty()) Mono.error(IllegalArgumentException())
        else authServicePort.assignRoles(accountId, roles)

    @Authorize
    override fun removeRolesFromAccount(accountId: String, roles: Collection<Role>): Mono<Void> =
        if (roles.isEmpty()) Mono.error(IllegalArgumentException())
        else authServicePort.removeRolesFromAccount(accountId, roles)
}
