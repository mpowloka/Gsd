package com.mpowloka.gsd.domain.user

data class User(
    val userId: Long,
    val login: String,
    val organization: String,
    val avatarUrl: String
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (userId != other.userId) return false

        return true
    }

    override fun hashCode(): Int {
        return userId.hashCode()
    }

}