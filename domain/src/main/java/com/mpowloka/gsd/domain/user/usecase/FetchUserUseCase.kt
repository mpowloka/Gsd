package com.mpowloka.gsd.domain.user.usecase

import com.mpowloka.gsd.domain.user.UsersRepository

class FetchUserUseCase(
    private val usersRepository: UsersRepository
){

    fun fetch(username: String) {
        usersRepository.fetchUser(username)
    }

}