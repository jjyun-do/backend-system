package com.samsung.healthcare.platform.adapter.persistence

import com.samsung.healthcare.platform.adapter.persistence.entity.UserEntity
import com.samsung.healthcare.platform.application.port.output.UserOutputPort
import com.samsung.healthcare.platform.domain.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Component

@Component
class UserDatabaseAdapter(
    private val repository: UserRepository,
) : UserOutputPort {
    override suspend fun insert(user: User): User =
        repository.save(UserEntity.fromDomain(user).also { it.setNew() }).toDomain()

    override suspend fun update(user: User): User =
        repository.save(UserEntity.fromDomain(user)).toDomain()

    override fun findAll(): Flow<User> =
        repository.findAll().map { it.toDomain() }

    override suspend fun findById(id: String): User? =
        repository.findById(id)?.toDomain()
}
