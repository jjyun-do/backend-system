package com.samsung.healthcare.platform.domain.project

class UserProfile(
    val userId: UserId,
    val profile: Map<String, Any> = emptyMap(),
) {
    companion object {
        fun newUserProfile(userId: String, profile: Map<String, Any>): UserProfile =
            UserProfile(UserId.from(userId), profile)
    }

    data class UserId private constructor(val value: String) {
        companion object {
            fun from(value: String?): UserId {
                requireNotNull(value)
                return UserId(value)
            }
        }
    }
}
