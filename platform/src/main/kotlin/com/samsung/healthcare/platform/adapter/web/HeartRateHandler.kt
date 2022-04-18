package com.samsung.healthcare.platform.adapter.web

import com.samsung.healthcare.platform.application.port.input.CreateHeartRateUseCase
import com.samsung.healthcare.platform.application.port.input.GetHeartRateQuery
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

@Component
class HeartRateHandler(
    private val createHeartRateUseCase: CreateHeartRateUseCase,
    private val getHeartRateQuery: GetHeartRateQuery,
) {
    suspend fun createHeartRate(req: ServerRequest): ServerResponse {
        TODO("Not yet implemented")
    }

    suspend fun findHeartRateById(req: ServerRequest): ServerResponse {
        TODO("Not yet implemented")
    }
}
