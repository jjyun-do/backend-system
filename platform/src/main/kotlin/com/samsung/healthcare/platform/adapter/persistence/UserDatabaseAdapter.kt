package com.samsung.healthcare.platform.adapter.persistence

import com.samsung.healthcare.platform.application.port.output.UserOutputPort
import com.samsung.healthcare.platform.domain.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Component

@Component
class UserDatabaseAdapter(
    private val repository: UserRepository,
) : UserOutputPort {
    override suspend fun save() {
        TODO("Not yet implemented")
    }

    override fun findAll(): Flow<User> {
        return repository.findAll().map { it.toDomain() }
    }
}
