package com.samsung.healthcare.account.adapter.auth.supertoken

import com.samsung.healthcare.account.application.exception.SignInException
import com.samsung.healthcare.account.application.port.output.AuthServicePort
import com.samsung.healthcare.account.domain.Account
import com.samsung.healthcare.account.domain.Email
import com.samsung.healthcare.account.domain.Role
import feign.FeignException.NotFound
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class SuperTokenAdapter(
    private val apiClient: SuperTokenApi
) : AuthServicePort {
    override fun registerNewUser(email: Email, password: String): Mono<Account> {
        TODO("Not yet implemented")
    }

    override fun generateResetToken(accountId: String): Mono<String> {
        TODO("Not yet implemented")
    }

    override fun resetPassword(resetToken: String, newPassword: String): Mono<Void> {
        TODO("Not yet implemented")
    }

    override fun assignRoles(accountId: String, collection: Collection<Role>): Mono<Void> {
        TODO("Not yet implemented")
    }

    override fun removeRolesFromAccount(accountId: String, collection: Collection<Role>): Mono<Void> {
        TODO("Not yet implemented")
    }

    override fun createRoles(collection: Collection<Role>): Mono<Void> {
        TODO("Not yet implemented")
    }

    override fun signIn(email: Email, password: String): Mono<Account> =
        apiClient.signIn(SignInRequest(email.value, password))
            .map { signInResponse ->
                Account(
                    signInResponse.user.id,
                    Email(signInResponse.user.email),
                    emptyList()
                )
            }.onErrorMap(NotFound::class.java) { SignInException() }
            // TODO handle other exceptions
            .onErrorMap { SignInException() }

    override fun listUserRoles(id: String): Mono<List<Role>> {
        TODO("Not yet implemented")
    }
}
