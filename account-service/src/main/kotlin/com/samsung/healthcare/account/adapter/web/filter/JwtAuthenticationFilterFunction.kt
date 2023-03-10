package com.samsung.healthcare.account.adapter.web.filter

import com.samsung.healthcare.account.application.context.ContextHolder
import com.samsung.healthcare.account.application.exception.UnauthorizedException
import com.samsung.healthcare.account.application.port.input.GetAccountUseCase
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.oauth2.jwt.JwtException
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.HandlerFilterFunction
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class JwtAuthenticationFilterFunction(
    private val getAccount: GetAccountUseCase
) : HandlerFilterFunction<ServerResponse, ServerResponse> {

    private val bearerPrefix = "Bearer "

    override fun filter(request: ServerRequest, next: HandlerFunction<ServerResponse>): Mono<ServerResponse> {

        val bearerString = request.headers().firstHeader(AUTHORIZATION) ?: throw UnauthorizedException()
        if (!bearerString.startsWith(bearerPrefix)) throw UnauthorizedException()

        return getAccount.getAccountFromToken(bearerString.substring(bearerPrefix.length))
            .flatMap { account ->
                ContextHolder.setAccount(next.handle(request), account)
            }.onErrorMap(JwtException::class.java) { throw UnauthorizedException() }
    }
}
