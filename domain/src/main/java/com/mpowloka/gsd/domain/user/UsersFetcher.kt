package com.mpowloka.gsd.domain.user

interface UsersFetcher {

    fun fetchUsers()

    fun fetchUser(username: String)

}