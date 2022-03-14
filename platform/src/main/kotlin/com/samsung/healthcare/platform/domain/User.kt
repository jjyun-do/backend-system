package com.samsung.healthcare.platform.domain

import java.time.LocalDateTime

data class User(
    val id: String,
    val createdAt: LocalDateTime,
    var deletedAt: LocalDateTime?,
) {
    // TODO: Implement validation logic if needed

    constructor(id: String) : this(id, LocalDateTime.now(), null)

    companion object {
        fun getHash(email: String): String {
            // TODO: Implement user email hashing logic
            return email
        }
    }

    fun delete() {
        this.deletedAt = LocalDateTime.now()
    }

    override fun toString(): String {
        return "User[$id]"
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is User) return false

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
