package com.samsung.healthcare.account.application.service

import com.samsung.healthcare.account.application.port.input.GetAccountUseCase
import com.samsung.healthcare.account.domain.Account
import com.samsung.healthcare.account.domain.Email
import com.samsung.healthcare.account.domain.Role
import com.samsung.healthcare.account.domain.RoleFactory
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtException
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Instant

@Service
class GetAccountService(
    private val jwtDecoder: ReactiveJwtDecoder
) : GetAccountUseCase {

    override fun getAccountFromToken(token: String): Mono<Account> =
        jwtDecoder.decode(token)
            .map { jwt ->
                validate(jwt)

                kotlin.runCatching {
                    Account(
                        id = jwt.subject,
                        email = jwt.getEmail(),
                        roles = jwt.getRoles()
                    )
                }.getOrElse { throw JwtException("invalid token") }
            }

    private fun validate(jwt: Jwt) {
        jwt.issuedAt ?: throw JwtException("issuedAt is required")
        val expiredAt = jwt.expiresAt ?: throw JwtException("expiredAt is required")
        if (expiredAt.isBefore(Instant.now())) throw JwtException("expired token")
    }

    private fun Jwt.getEmail(): Email =
        Email(this.claims["email"] as String)

    @Suppress("UNCHECKED_CAST")
    private fun Jwt.getRoles(): List<Role> =
        (this.claims["roles"] as List<String>)
            .map { RoleFactory.createRole(it) }
}
