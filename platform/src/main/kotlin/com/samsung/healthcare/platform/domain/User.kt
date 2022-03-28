package com.samsung.healthcare.platform.domain

import java.time.LocalDateTime

data class User(
    val id: UserId?,
    val email: Email,
    val sub: String,
    val provider: String,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun newUser(email: Email, sub: String, provider: String): User =
            User(null, email, sub, provider, LocalDateTime.now())

        fun getHash(email: String): String {
            // TODO: Implement user email hashing logic
            return email
        }
    }

    override fun toString(): String {
        return "User[$email]"
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is User) return false

        return email == other.email
    }

    override fun hashCode(): Int {
        return email.hashCode()
    }

    data class UserId private constructor(val value: Int) {
        companion object {
            fun from(value: Int?): UserId {
                requireNotNull(value)
                require(0 < value)
                return UserId(value)
            }
        }
    }
}
