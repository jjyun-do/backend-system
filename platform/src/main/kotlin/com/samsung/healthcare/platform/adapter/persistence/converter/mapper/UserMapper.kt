package com.samsung.healthcare.platform.adapter.persistence.converter.mapper

import com.samsung.healthcare.platform.adapter.persistence.entity.UserEntity
import com.samsung.healthcare.platform.domain.User
import com.samsung.healthcare.platform.domain.User.UserId
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
abstract class UserMapper {
    companion object {
        val INSTANCE: UserMapper = Mappers.getMapper(UserMapper::class.java)
    }

    @Mapping(target = "id", source = "id.value")
    abstract fun toEntity(user: User): UserEntity

    @Mapping(target = "id", source = ".")
    abstract fun toDomain(userEntity: UserEntity): User

    fun mapId(userEntity: UserEntity): UserId =
        UserId.from(userEntity.id)
}
