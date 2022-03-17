package com.samsung.healthcare.platform.adapter.web.security

import com.samsung.healthcare.platform.application.port.output.UserOutputPort
import com.samsung.healthcare.platform.domain.User
import kotlinx.coroutines.reactor.mono
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class OAuth2SuccessHandler(private val userOutputPort: UserOutputPort) : ServerAuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        webFilterExchange: WebFilterExchange,
        authentication: Authentication,
    ): Mono<Void> {
        val oAuth2User: OAuth2User = authentication.principal as OAuth2User
        val attributes: Map<String, Any> = oAuth2User.attributes

        val email: String = attributes["email"] as String

        // TODO: save the hash value of email
        return mono {
            registerUser(email)
        }.then()
    }

    suspend fun registerUser(email: String) {
        userOutputPort.findById(email) ?: userOutputPort.insert(User(email))
    }
}
