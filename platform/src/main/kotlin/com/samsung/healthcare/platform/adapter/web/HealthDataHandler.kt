package com.samsung.healthcare.platform.adapter.web

import com.samsung.healthcare.platform.application.port.input.CreateHealthDataUseCase
import com.samsung.healthcare.platform.application.port.input.GetHealthDataCommand
import com.samsung.healthcare.platform.application.port.input.GetHealthDataQuery
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyAndAwait

@Component
class HealthDataHandler(
    private val createHealthDataUseCase: CreateHealthDataUseCase,
    private val getHealthDataQuery: GetHealthDataQuery,
) {
    suspend fun createHealthData(req: ServerRequest): ServerResponse {
        TODO("Not yet implemented")
    }

    suspend fun findByPeriod(req: ServerRequest): ServerResponse {

        val healthData = getHealthDataQuery.findByPeriod(
            GetHealthDataCommand(
                req.getTypes(),
                req.getUsers(),
                req.getStartDate(),
                req.getEndDate()
            )
        )

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyAndAwait(healthData)
    }
}
