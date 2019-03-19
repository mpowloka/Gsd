package com.mpowloka.gsd.domain.user.usecase

import com.mpowloka.gsd.domain.user.UsersFetcher

class FetchUsersUseCase(
    private val usersFetcher: UsersFetcher
) {

    fun fetch() {
        usersFetcher.fetchUsers()
    }

}