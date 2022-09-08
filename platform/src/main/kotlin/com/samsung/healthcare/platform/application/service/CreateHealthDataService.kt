package com.samsung.healthcare.platform.application.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.samsung.healthcare.platform.application.port.input.SaveHealthDataCommand
import com.samsung.healthcare.platform.application.port.input.SaveHealthDataUseCase
import com.samsung.healthcare.platform.application.port.output.SaveHealthDataPort
import com.samsung.healthcare.platform.domain.project.UserProfile.UserId
import org.springframework.stereotype.Service

@Service
class CreateHealthDataService(
    private val saveHealthDataPort: SaveHealthDataPort,
    private val objectMapper: ObjectMapper,
) : SaveHealthDataUseCase {
    @Suppress("UNCHECKED_CAST")
    override suspend fun saveHealthData(userId: UserId, command: SaveHealthDataCommand) {
        saveHealthDataPort.save(
            userId,
            command.type,
            command.data.map {
                objectMapper.convertValue(it, command.type.dataClass)
            }
        )
    }
}
