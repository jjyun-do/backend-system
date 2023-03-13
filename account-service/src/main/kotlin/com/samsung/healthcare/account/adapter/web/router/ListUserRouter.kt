package com.samsung.healthcare.account.adapter.web.router

import com.samsung.healthcare.account.adapter.web.filter.JwtAuthenticationFilterFunction
import com.samsung.healthcare.account.adapter.web.handler.ListUserHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class ListUserRouter {

    @Bean
    fun routeListUsers(
        handler: ListUserHandler,
        jwtAuthenticationFilterFunction: JwtAuthenticationFilterFunction
    ): RouterFunction<ServerResponse> =
        RouterFunctions.route()
            .GET(LIST_PROJECT_USER_PATH, handler::listProjectUsers)
            .filter(jwtAuthenticationFilterFunction)
            .build()

    @Bean
    fun routeInternalListUsers(
        handler: ListUserHandler
    ): RouterFunction<ServerResponse> =
        RouterFunctions.route()
            .GET(LIST_ALL_USER_PATH, handler::listAllUsers)
            .build()
}
