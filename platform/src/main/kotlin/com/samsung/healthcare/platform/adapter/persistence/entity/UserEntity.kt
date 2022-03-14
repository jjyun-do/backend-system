package com.samsung.healthcare.platform.adapter.persistence.entity

import com.samsung.healthcare.platform.domain.User
import java.time.LocalDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("users")
data class UserEntity(
    @Id
    val id: String,
    val createdAt: LocalDateTime,
    val deletedAt: LocalDateTime?,
) {
    companion object {
        fun fromDomain(user: User): UserEntity =
            UserEntity(user.id, user.createdAt, user.deletedAt)
    }

    fun toDomain(): User =
        User(this.id, this.createdAt, this.deletedAt)
}
