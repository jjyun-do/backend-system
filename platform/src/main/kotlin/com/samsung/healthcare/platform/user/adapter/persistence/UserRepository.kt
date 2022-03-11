package com.samsung.healthcare.platform.user.adapter.persistence

import com.samsung.healthcare.platform.user.adapter.persistence.entity.UserEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface UserRepository : CoroutineCrudRepository<UserEntity, String>
