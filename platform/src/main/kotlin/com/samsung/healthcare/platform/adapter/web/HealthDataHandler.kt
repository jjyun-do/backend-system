package com.samsung.healthcare.platform.adapter.web

import com.samsung.healthcare.platform.application.port.input.CreateHealthDataUseCase
import com.samsung.healthcare.platform.application.port.input.GetHealthDataQuery
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

@Component
class HealthDataHandler(
    private val createHealthDataUseCase: CreateHealthDataUseCase,
    private val getHealthDataQuery: GetHealthDataQuery,
) {
    suspend fun createHealthData(req: ServerRequest): ServerResponse {
        TODO("Not yet implemented")
    }

    suspend fun findByPeriod(req: ServerRequest): ServerResponse {
        TODO("Not yet implemented")
    }
}
