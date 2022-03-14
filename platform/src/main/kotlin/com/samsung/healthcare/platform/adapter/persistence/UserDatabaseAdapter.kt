package com.samsung.healthcare.platform.adapter.persistence

import com.samsung.healthcare.platform.adapter.persistence.entity.UserEntity
import com.samsung.healthcare.platform.application.port.output.UserOutputPort
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Component

@Component
class UserDatabaseAdapter(
    private val repository: UserRepository,
) : UserOutputPort {
    override suspend fun save() {
        TODO("Not yet implemented")
    }

    override suspend fun findAll(): Flow<UserEntity> {
        return repository.findAll()
    }
}
