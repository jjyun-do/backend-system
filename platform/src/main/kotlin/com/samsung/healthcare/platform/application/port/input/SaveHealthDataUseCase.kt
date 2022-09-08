package com.samsung.healthcare.platform.application.port.input

import com.samsung.healthcare.platform.domain.project.UserProfile.UserId

interface SaveHealthDataUseCase {
    suspend fun saveHealthData(userId: UserId, command: SaveHealthDataCommand)
}
