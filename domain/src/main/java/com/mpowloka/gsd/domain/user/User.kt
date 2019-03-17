package com.mpowloka.gsd.domain.user

data class User(
    val userId: Long,
    val login: String,
    val organization: String,
    val avatarUrl: String
)