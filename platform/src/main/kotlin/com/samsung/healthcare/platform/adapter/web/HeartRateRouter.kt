package com.samsung.healthcare.platform.adapter.web

import org.apache.logging.log4j.util.Strings
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class HeartRateRouter(
    private val handler: HeartRateHandler,
) {
    @Bean
    fun routeHeartRate(): RouterFunction<ServerResponse> = coRouter {
        "/api/healthdata/heartrate".nest {
            POST(Strings.EMPTY, contentType(MediaType.APPLICATION_JSON), handler::createHeartRate)
            GET("{id}", handler::findHeartRateById)
        }
    }
}
