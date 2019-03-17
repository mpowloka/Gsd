package com.mpowloka.gsd.data.user

import com.mpowloka.gsd.domain.user.User

data class UserModel(
    val id: Long,
    val login: String,
    val avatar_url: String,
    val organizations_url: String
) {

    fun toUser() = User(id, login, organizations_url, avatar_url)

}