package com.mpowloka.gsd.domain.user.usecase

import com.mpowloka.gsd.domain.user.UsersFetcher

class FetchUserUseCase(
    private val usersFetcher: UsersFetcher
) {

    fun fetch(username: String) {
        usersFetcher.fetchUser(username)
    }

}