package com.mpowloka.gsd.domain.user

data class User(
    private val userId: Long,
    private val login: String,
    private val organization: String,
    private val avatarUrl: String
)