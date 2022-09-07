package com.samsung.healthcare.platform.adapter.persistence.converter.mapper

import com.samsung.healthcare.platform.adapter.persistence.entity.project.UserProfileEntity
import com.samsung.healthcare.platform.domain.project.UserProfile
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface UserProfileMapper {
    companion object {
        val INSTANCE: UserProfileMapper = Mappers.getMapper(UserProfileMapper::class.java)
    }

    fun toEntity(userProfile: UserProfile): UserProfileEntity

    fun toDomain(userProfileEntity: UserProfileEntity): UserProfile
}
