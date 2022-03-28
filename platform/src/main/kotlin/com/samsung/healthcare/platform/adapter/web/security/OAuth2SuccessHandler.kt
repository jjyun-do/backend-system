package com.samsung.healthcare.platform.adapter.web.security

import com.samsung.healthcare.platform.application.port.input.RegisterUserCommand
import com.samsung.healthcare.platform.application.port.input.UserInputPort
import com.samsung.healthcare.platform.domain.Email
import kotlinx.coroutines.reactor.mono
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class OAuth2SuccessHandler(private val userInputPort: UserInputPort) : ServerAuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        webFilterExchange: WebFilterExchange,
        authentication: Authentication,
    ): Mono<Void> {
        val attributes: Map<String, Any> = (authentication.principal as OAuth2User).attributes
        return mono {
            registerIfNotExists(attributes)
        }.then()
    }

    suspend fun registerIfNotExists(attributes: Map<String, Any>) {
        // TODO: save the hash value of email
        val email = Email(attributes[EMAIL_KEY] as String)
        if (userInputPort.existsByEmail(email)) return

        userInputPort.registerUser(
            RegisterUserCommand(
                email,
                attributes[SUBJECT_KEY] as String,
                attributes[REGISTRATION_ID_KEY] as String
            )
        )
    }
}
