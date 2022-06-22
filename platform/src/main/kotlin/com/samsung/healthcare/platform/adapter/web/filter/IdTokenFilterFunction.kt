package com.samsung.healthcare.platform.adapter.web.filter

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.samsung.healthcare.platform.application.exception.UnauthorizedException
import org.springframework.util.StringUtils
import org.springframework.web.reactive.function.server.HandlerFilterFunction
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

class IdTokenFilterFunction : HandlerFilterFunction<ServerResponse, ServerResponse> {
    override fun filter(request: ServerRequest, next: HandlerFunction<ServerResponse>): Mono<ServerResponse> {
        val idToken: String? = request.headers().firstHeader("id_token")
        if (!StringUtils.hasText(idToken)) throw UnauthorizedException("You must provide id_token")

        try {
            FirebaseAuth.getInstance().verifyIdToken(idToken)
        } catch (e: FirebaseAuthException) {
            throw UnauthorizedException("Please use proper authorization: ${e.message}")
        }

        return next.handle(request)
    }
}
