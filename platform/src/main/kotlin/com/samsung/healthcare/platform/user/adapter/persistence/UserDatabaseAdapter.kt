package com.samsung.healthcare.platform.user.adapter.persistence

import com.samsung.healthcare.platform.user.adapter.persistence.entity.UserEntity
import com.samsung.healthcare.platform.user.application.port.output.UserPort
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Component

@Component
class UserDatabaseAdapter(
    private val repository: UserRepository,
) : UserPort {
    override suspend fun save() {
        TODO("Not yet implemented")
    }

    override suspend fun findAll(): Flow<UserEntity> {
        return repository.findAll()
    }
}
