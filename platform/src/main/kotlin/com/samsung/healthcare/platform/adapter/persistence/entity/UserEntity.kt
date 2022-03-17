package com.samsung.healthcare.platform.adapter.persistence.entity

import com.samsung.healthcare.platform.domain.User
import java.time.LocalDateTime
import org.springframework.data.relational.core.mapping.Table

@Table("users")
data class UserEntity(
    private val id: String,
    private val createdAt: LocalDateTime,
    private val deletedAt: LocalDateTime?,
) : BaseIdEntity<String>(id) {
    companion object {
        fun fromDomain(user: User): UserEntity =
            UserEntity(user.id, user.createdAt, user.deletedAt)
    }

    fun toDomain(): User =
        User(this.id, this.createdAt, this.deletedAt)
}
