package com.samsung.healthcare.platform.application.service.project

import com.samsung.healthcare.platform.adapter.web.context.ContextHolder.getFirebaseToken
import com.samsung.healthcare.platform.application.exception.ForbiddenException
import com.samsung.healthcare.platform.application.port.input.CreateUserCommand
import com.samsung.healthcare.platform.application.port.input.project.UpdateUserProfileLastSyncedTimeUseCase
import com.samsung.healthcare.platform.application.port.input.project.UserProfileInputPort
import com.samsung.healthcare.platform.application.port.output.project.UserProfileOutputPort
import com.samsung.healthcare.platform.domain.project.UserProfile
import org.springframework.stereotype.Service

@Service
class UserProfileService(
    private val userProfileOutputPort: UserProfileOutputPort
) : UserProfileInputPort, UpdateUserProfileLastSyncedTimeUseCase {
    override suspend fun registerUser(command: CreateUserCommand) {
        if (command.userId != getFirebaseToken().uid)
            throw ForbiddenException("This operation can only be done by owner of token.")

        userProfileOutputPort.create(UserProfile.newUserProfile(command.userId, command.profile))
    }

    override suspend fun updateLastSyncedTime(userId: UserProfile.UserId) {
        userProfileOutputPort.updateLastSyncedAt(userId)
    }
}
