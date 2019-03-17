package com.mpowloka.gsd.userdetails

import com.mpowloka.gsd.domain.user.User

data class UserDetails(
    val login: String,
    val organization: String,
    val avatarUrl: String
) {

    constructor(user: User): this(user.login, user.organization, user.avatarUrl)

}