package com.samsung.healthcare.account.adapter.web.router

import com.samsung.healthcare.account.adapter.web.handler.ResetPasswordHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class ResetPasswordRouter {

    @Bean
    fun routeResetPassword(
        handler: ResetPasswordHandler
    ): RouterFunction<ServerResponse> =
        RouterFunctions.route()
            .POST(
                RESET_PASSWORD_PATH,
                RequestPredicates.contentType(MediaType.APPLICATION_JSON),
                handler::resetPassword
            )
            .build()
}
