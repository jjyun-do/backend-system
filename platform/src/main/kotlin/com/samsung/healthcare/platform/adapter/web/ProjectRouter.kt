package com.samsung.healthcare.platform.adapter.web

import org.apache.logging.log4j.util.Strings
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class ProjectRouter(
    private val handler: ProjectHandler,
) {
    @Bean
    fun routeProject(): RouterFunction<ServerResponse> = coRouter {
        "/api/projects".nest {
            POST(Strings.EMPTY, contentType(MediaType.APPLICATION_JSON), handler::createProject)
            GET("{id}", handler::findProjectById)
        }
    }
}
