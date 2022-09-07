package com.samsung.healthcare.platform.adapter.persistence.converter.mapper

import com.samsung.healthcare.platform.adapter.persistence.entity.project.UserProfileEntity
import com.samsung.healthcare.platform.domain.project.UserProfile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class UserProfileMapperTest {
    @Test
    fun `should convert domain to entity`() {
        val userProfile = UserProfile(
            "test-user-id",
            emptyMap()
        )

        val userProfileEntity = UserProfileMapper.INSTANCE.toEntity(userProfile)

        assertThat(userProfileEntity.userId).isEqualTo(userProfile.userId)
    }

    @Test
    fun `should convert entity to domain`() {
        val userProfileEntity = UserProfileEntity(
            "test-user-id",
            emptyMap()
        )

        val userProfile = UserProfileMapper.INSTANCE.toDomain(userProfileEntity)

        assertThat(userProfile.userId).isEqualTo(userProfileEntity.userId)
    }
}
