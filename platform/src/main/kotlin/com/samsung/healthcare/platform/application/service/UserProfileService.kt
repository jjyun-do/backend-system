package com.samsung.healthcare.platform.application.service

import com.samsung.healthcare.platform.application.port.input.UserProfileInputPort
import com.samsung.healthcare.platform.application.port.output.UserProfileOutputPort
import com.samsung.healthcare.platform.domain.project.UserProfile
import org.springframework.stereotype.Service

@Service
class UserProfileService(
    private val userProfileOutputPort: UserProfileOutputPort
) : UserProfileInputPort {
    override suspend fun registerUser(userProfile: UserProfile) {
        userProfileOutputPort.create(userProfile)
    }
}
