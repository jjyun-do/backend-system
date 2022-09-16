package com.samsung.healthcare.account.adapter.auth.supertoken

import com.samsung.healthcare.account.adapter.auth.supertoken.SuperTokensApi.CreateRoleRequest
import com.samsung.healthcare.account.adapter.auth.supertoken.SuperTokensApi.GenerateJwtRequest
import com.samsung.healthcare.account.adapter.auth.supertoken.SuperTokensApi.ResetPasswordRequest
import com.samsung.healthcare.account.adapter.auth.supertoken.SuperTokensApi.RoleBinding
import com.samsung.healthcare.account.adapter.auth.supertoken.SuperTokensApi.SignRequest
import com.samsung.healthcare.account.adapter.auth.supertoken.SuperTokensApi.Status.OK
import com.samsung.healthcare.account.adapter.auth.supertoken.SuperTokensApi.StatusResponse
import com.samsung.healthcare.account.adapter.auth.supertoken.SuperTokensApi.User
import com.samsung.healthcare.account.adapter.auth.supertoken.SuperTokensApi.UserId
import com.samsung.healthcare.account.application.exception.AlreadyExistedEmailException
import com.samsung.healthcare.account.application.exception.InvalidResetTokenException
import com.samsung.healthcare.account.application.exception.SignInException
import com.samsung.healthcare.account.application.exception.UnknownAccountIdException
import com.samsung.healthcare.account.application.exception.UnknownEmailException
import com.samsung.healthcare.account.application.port.output.AuthServicePort
import com.samsung.healthcare.account.application.port.output.JwtGenerationCommand
import com.samsung.healthcare.account.application.port.output.TokenSigningPort
import com.samsung.healthcare.account.domain.Account
import com.samsung.healthcare.account.domain.Email
import com.samsung.healthcare.account.domain.Role
import com.samsung.healthcare.account.domain.RoleFactory
import feign.FeignException.NotFound
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class SuperTokenAdapter(
    private val apiClient: SuperTokensApi
) : AuthServicePort, TokenSigningPort {
    override fun registerNewUser(email: Email, password: String): Mono<Account> =
        apiClient.signUp(SignRequest(email.value, password))
            .mapNotNull {
                if (it.status == OK) it.user?.toAccount()
                else throw AlreadyExistedEmailException()
            }

    override fun generateResetToken(accountId: String): Mono<String> =
        apiClient.generateResetToken(UserId(accountId))
            .mapNotNull {
                if (it.status == OK) it.token
                else throw UnknownAccountIdException()
            }

    override fun resetPassword(resetToken: String, newPassword: String): Mono<Void> =
        apiClient.resetPassword(ResetPasswordRequest(resetToken, newPassword))
            .flatMap {
                if (it.status == OK) Mono.empty()
                else Mono.error { InvalidResetTokenException() }
            }

    override fun assignRoles(email: Email, roles: Collection<Role>): Mono<Void> =
        apiClient.getAccountWithEmail(email.value)
            .mapNotNull {
                if (it.status == OK && it.user != null) it.user.toAccount()
                else throw UnknownEmailException()
            }.flatMap { assignRoles(it.id, roles) }

    override fun assignRoles(accountId: String, roles: Collection<Role>): Mono<Void> =
        handleRoleBinding(accountId, roles) { roleBinding ->
            apiClient.assignRoles(roleBinding)
        }

    override fun removeRolesFromAccount(accountId: String, roles: Collection<Role>): Mono<Void> =
        handleRoleBinding(accountId, roles) { roleBinding ->
            apiClient.removeUserRole(roleBinding)
        }

    private fun handleRoleBinding(
        accountId: String,
        roles: Collection<Role>,
        handlerFunction: (RoleBinding) -> Mono<StatusResponse>
    ): Mono<Void> =
        Flux.fromIterable(roles)
            .flatMap { handlerFunction(RoleBinding(accountId, it.roleName)) }
            // TODO handle exceptions
            // TODO haw to handle some fails
            .then()

    override fun createRoles(roles: Collection<Role>): Mono<Void> =
        Flux.fromIterable(roles)
            .flatMap { apiClient.createRoles(CreateRoleRequest(it.roleName)) }
            .then()

    override fun signIn(email: Email, password: String): Mono<Account> =
        apiClient.signIn(SignRequest(email.value, password))
            .onErrorMap(NotFound::class.java) { SignInException() }
            // TODO handle other exceptions
            .onErrorMap { SignInException() }
            .mapNotNull {
                if (it.status == OK && it.user != null) it.user.toAccount()
                else throw SignInException()
            }.flatMap { account ->
                listUserRoles(account.id)
                    .map { account.copy(roles = it) }
            }

    override fun listUserRoles(id: String): Mono<List<Role>> =
        apiClient.listUserRoles(id)
            .map { response ->
                response.roles.map {
                    RoleFactory.createRole(it)
                }
            }

    override fun listUsers(): Mono<List<Account>> =
        apiClient.listUsers()
            .flatMapMany { resp -> Flux.fromIterable(resp.users.map { it.user }) }
            .flatMap { user ->
                listUserRoles(user.id)
                    .map { roles -> Account(user.id, Email(user.email), roles) }
            }.collectList()

    override fun generateSignedJWT(jwtTokenCommand: JwtGenerationCommand): Mono<String> =
        apiClient.generateSignedJwt(
            GenerateJwtRequest(
                payload = mapOf(
                    "sub" to jwtTokenCommand.subject,
                    "email" to jwtTokenCommand.email,
                    "roles" to jwtTokenCommand.roles.map { it.roleName }
                ),
                jwksDomain = jwtTokenCommand.issuer,
                validity = jwtTokenCommand.lifeTime
            )
        ).map { it.jwt }

    private fun User.toAccount(): Account =
        Account(
            id,
            Email(email),
            emptyList()
        )
}
