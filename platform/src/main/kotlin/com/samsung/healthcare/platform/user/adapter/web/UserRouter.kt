package com.samsung.healthcare.platform.user.adapter.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class UserRouter(
    private val handler: UserHandler,
) {
    @Bean
    fun router() = coRouter {
        "/api/users".nest {
            GET("", handler::getUsers)
        }
    }
}
