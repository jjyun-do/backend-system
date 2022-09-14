package com.samsung.healthcare.platform.adapter.persistence.project

import com.samsung.healthcare.platform.adapter.persistence.entity.project.toEntity
import com.samsung.healthcare.platform.application.port.output.project.UserProfileOutputPort
import com.samsung.healthcare.platform.domain.project.UserProfile
import org.springframework.stereotype.Component

@Component
class UserProfileDatabaseAdapter(
    private val repository: UserProfileRepository
) : UserProfileOutputPort {
    override suspend fun create(userProfile: UserProfile) {
        repository.save(userProfile.toEntity().also { it.setNew() })
    }
}
